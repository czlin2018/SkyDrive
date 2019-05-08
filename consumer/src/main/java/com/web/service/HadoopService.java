package com.web.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.comment.api.DateApi;
import com.web.comment.api.HadoopUtil;
import com.web.comment.unit.PageDto;
import com.web.comment.unit.ResultDto;
import com.web.comment.unit.SysExcCode;
import com.web.entity.Resource;
import com.web.entity.ResourcePath;
import com.web.mapper.ResourceMapper;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @描述:
 * @版权: Copyright (c) 2016-2018
 * @公司: lumi
 * @作者: 泽林
 * @创建日期: 2019-05-04
 * @创建时间: 上午8:13
 */
@Service
public class HadoopService {

    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    ResourceService resourceService;


    /**
     * 简单权限
     *
     * @param userId
     * @param path
     * @return
     */
    public String getPath( String userId , String path ){
        if(StringUtils.isEmpty(userId)){
            return path;
        }

        String[] split = path.split("/");
        if(split.length > 1 && split[1].equals(userId)){
            return path;
        }
        return "/" + userId + path;
    }


    /**
     * 创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    public ResultDto mkdir( String path , String userId ) throws Exception{
        path = getPath( userId , path );

        // 文件对象
        FileSystem fs = HadoopUtil.getFileSystem( );
        // 目标路径
        Path newPath = new Path( path );
        // 创建空文件夹
        boolean isOk = fs.mkdirs( newPath );
        fs.close( );

        //插入路径表
        String[] split = path.split("/");
        //说明是新建二级

        if ( split.length == 3 ) {

            ResourcePath resourcePath = new ResourcePath( );
            resourcePath.setNodeId( DateApi.getTimeId( ) );
            resourcePath.setNodeName( userId );
            resourcePath.setNodePath( "/" + userId );
            resourcePath.setIsFrist( 1 );
            resourcePath.setFatherNode( "" );
            resourcePath.setUpdateTime( DateApi.currentDateTime( ) );
            if ( ! resourceService.selectFirst( resourcePath.getNodeName( ) , resourcePath.getNodePath( ) ) ) {
                resourceService.addResourcePath( resourcePath );
            }

        }

        String s = split[split.length - 1];
        ResourcePath resourcePath = new ResourcePath();
        resourcePath.setNodeId(DateApi.getTimeId());
        resourcePath.setNodeName( s );
        resourcePath.setNodePath(path);
        resourcePath.setFatherNode( path.substring( 0 , path.length( ) - 1 - resourcePath.getNodeName( ).length( ) ) );
        resourcePath.setIsFrist( 0 );
        resourcePath.setUpdateTime(DateApi.currentDateTime());
        boolean success = resourceService.addResourcePath(resourcePath);

        if(isOk && success){
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "创建成功!" , userId );
        }
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "创建失败!" );

    }

    /**
     * 上传文件
     *
     * @param path
     * @param file
     * @return
     * @throws Exception
     */

    public ResultDto createFile( String path , String userId , MultipartFile file ) throws Exception{
        if ( StringUtils.isEmpty( path ) || null == file.getBytes( ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "请求参数为空!" );
        }
        path = getPath(userId, path);
        if ( "/".equals( path ) ) {
            path = "";
        }

        String fileName = file.getOriginalFilename( );

        String fullPath = path + fileName;
        //插入资源表,没有"/",插入"/"
        if(path.length() > 2 && ! path.substring(path.length() - 1, path.length()).equals("/")){
            fullPath = path + "/" + fileName;
        }
        Resource resource = new Resource();
        resource.setResourceId(DateApi.getTimeId());
        resource.setResourceName(fileName);
        resource.setPath(path);
        resource.setFullPath( fullPath );
        resource.setUpdateTime(DateApi.currentDateTime());
        boolean success = resourceService.addResource(resource);

        FileSystem fs = HadoopUtil.getFileSystem( );
        // 上传时默认当前目录，后面自动拼接文件的目录
        Path newPath = new Path( path + "/" + fileName );
        // 打开一个输出流
        FSDataOutputStream outputStream = fs.create( newPath );
        outputStream.write( file.getBytes( ) );
        outputStream.close( );
        fs.close( );

        if(success){
            return new ResultDto(SysExcCode.SysCommonExcCode.SYS_SUCCESS, "创建成功!", userId);
        }
        return new ResultDto(SysExcCode.SysCommonExcCode.SYS_ERROR, "创建失败!");

    }

    /**
     * 读取HDFS目录信息 -----DB
     *
     * @param path
     * @param pageDto
     * @param userType
     * @return
     * @throws Exception
     */
    public ResultDto readPathInfoFromDb( String path , String userId , PageDto pageDto , String userType ){
        path = getPath( userId , path );
        Page <Object> objectPage = PageHelper.startPage( pageDto.getPageNo( ) , pageDto.getPageSize( ) );
        List <Map <String, Object>> list = resourceService.readPathInfoFromDb( path , userId , userType );

        pageDto.setTotalCount( objectPage.getTotal( ) );
        pageDto.setPageData( list );
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "读取成功!" , pageDto );
    }
    /**
     * 读取HDFS目录信息
     *
     * @param path
     * @param pageDto
     * @return
     * @throws Exception
     */
    public ResultDto readPathInfo( String path , String userId , PageDto pageDto ) throws Exception{

        path = getPath( userId , path );

        Page <Object> objectPage = PageHelper.startPage( pageDto.getPageNo( ) , pageDto.getPageSize( ) );
        FileSystem fs = HadoopUtil.getFileSystem( );
        Path newPath = new Path( path );
        FileStatus[] statusList = fs.listStatus( newPath );
        List <Map <String, Object>> list = new ArrayList <>( );
        if ( null != statusList && statusList.length > 0 ) {
            for ( FileStatus fileStatus : statusList ) {
                Map <String, Object> map = new HashMap <>( );
                boolean directory = fileStatus.isDirectory( );
                String name = fileStatus.getPath( ).getName( );
                map.put( "name" , name );
                map.put( "directory" , directory );
                //map.put( "filePath" , fileStatus.getPath( ) );
                //map.put( "fileStatus" , fileStatus.toString( ) );
                list.add( map );
            }
            pageDto.setTotalCount( (long) 10 );
            pageDto.setPageData( list );
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "读取成功!" , pageDto );
        } else {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "目录内容为空!" , pageDto );

        }
    }



    /**
     * 读取HDFS文件内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    public ResultDto readFile( String path ) throws Exception{
        FileSystem fs = HadoopUtil.getFileSystem( );
        Path newPath = new Path( path );
        InputStream in = null;
        try {
            in = fs.open( newPath );
            // 复制到标准的输出流
            IOUtils.copyBytes( in , System.out , 4096 );
        } finally {
            IOUtils.closeStream( in );
            fs.close( );
        }
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "读取成功!" );
    }



    /**
     * 读取文件列表
     *
     * @param path
     * @return
     * @throws Exception
     */
    public ResultDto listFile( String path ) throws Exception{

        if ( StringUtils.isEmpty( path ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "请求参数为空!" );
        }
        FileSystem fs = HadoopUtil.getFileSystem( );
        Path newPath = new Path( path );
        // 递归找到所有文件
        RemoteIterator <LocatedFileStatus> filesList = fs.listFiles( newPath , true );
        List <Map <String, String>> returnList = new ArrayList <>( );
        while (filesList.hasNext( )) {
            LocatedFileStatus next = filesList.next( );
            String fileName = next.getPath( ).getName( );
            Path filePath = next.getPath( );
            Map <String, String> map = new HashMap <>( );
            map.put( "fileName" , fileName );
            map.put( "filePath" , filePath.toString( ) );
            returnList.add( map );
        }
        fs.close( );
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "请求参数为空!" , returnList );

    }

    /**
     * 重命名文件
     *
     * @param oldName
     * @param newName
     * @return
     * @throws Exception
     */
    public ResultDto renameFile( String oldName , String newName ) throws Exception{
        if ( StringUtils.isEmpty( oldName ) || StringUtils.isEmpty( newName ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "请求参数为空!" );
        }
        FileSystem fs = HadoopUtil.getFileSystem( );
        Path oldPath = new Path( oldName );
        Path newPath = new Path( newName );
        boolean isOk = fs.rename( oldPath , newPath );
        fs.close( );
        if ( isOk ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "修改成功!" );
        } else {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "修改失败!" );
        }
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    public ResultDto deleteFile( String path ) throws Exception{
        FileSystem fs = HadoopUtil.getFileSystem( );
        Path newPath = new Path( path );
        boolean isOk = fs.deleteOnExit( newPath );
        fs.close( );
        //删除文件夹
        resourceService.delResourcePath( path );

        //删除文件
        resourceService.delResource( path );

        if ( isOk ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "删除成功!" );
        } else {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "删除失败!" );
        }

    }

    /**
     * 上传文件2
     *
     * @param path
     * @param uploadPath
     * @return
     * @throws Exception
     */
    public ResultDto uploadFile( String path , String uploadPath ) throws Exception{
        FileSystem fs = HadoopUtil.getFileSystem( );
        // 上传路径
        Path clientPath = new Path( path );
        // 目标路径
        Path serverPath = new Path( uploadPath );

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fs.copyFromLocalFile( false , clientPath , serverPath );
        fs.close( );
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "上传成功!" );


    }

    /**
     * 下载文件
     *
     * @param path
     * @param downloadPath
     * @return
     * @throws Exception
     */
    public ResultDto downloadFile( String path , String downloadPath ) throws Exception{
        FileSystem fs = HadoopUtil.getFileSystem( );
        // 上传路径
        Path clientPath = new Path( path );
        // 目标路径
        Path serverPath = new Path( downloadPath );

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fs.copyToLocalFile( false , clientPath , serverPath );
        fs.close( );
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "下载成功,文件路径:" + downloadPath );

    }

    /**
     * HDFS文件复制
     *
     * @param sourcePath
     * @param targetPath
     * @return
     * @throws Exception
     */
    public ResultDto copyFile( String sourcePath , String targetPath ) throws Exception{

        FileSystem fs = HadoopUtil.getFileSystem( );
        // 原始文件路径
        Path oldPath = new Path( sourcePath );
        // 目标路径
        Path newPath = new Path( targetPath );

        FSDataInputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            inputStream = fs.open( oldPath );
            outputStream = fs.create( newPath );

            IOUtils.copyBytes( inputStream , outputStream , 1024 * 1024 * 64 , false );
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "复制成功!" );
        } finally {
            inputStream.close( );
            outputStream.close( );
            fs.close( );
        }

    }

    /**
     * 生成分享码
     *
     * @param fullPath
     * @return
     */
    public ResultDto createCode (String fullPath){
        String code = resourceService.createCode(fullPath);
        if(null == code){
            return new ResultDto(SysExcCode.SysCommonExcCode.SYS_ERROR, "分享码生成失败");
        }
        return new ResultDto(SysExcCode.SysCommonExcCode.SYS_SUCCESS, "分享码生成失败", code);
    }
}

