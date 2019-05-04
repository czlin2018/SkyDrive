package com.web.service;

import com.web.comment.api.HadoopUtil;
import com.web.comment.unit.ResultDto;
import com.web.comment.unit.SysExcCode;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
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

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    public ResultDto mkdir( String path ) throws Exception{

        if ( StringUtils.isEmpty( path ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "请求参数为空!" );
        }
        // 文件对象
        FileSystem fs = HadoopUtil.getFileSystem( );
        // 目标路径
        Path newPath = new Path( path );
        // 创建空文件夹
        boolean isOk = fs.mkdirs( newPath );
        fs.close( );
        if ( isOk ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "创建成功!" );
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

    public ResultDto createFile( String path , MultipartFile file ) throws Exception{
        if ( StringUtils.isEmpty( path ) || null == file.getBytes( ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "请求参数为空!" );
        }
        String fileName = file.getOriginalFilename( );
        FileSystem fs = HadoopUtil.getFileSystem( );
        // 上传时默认当前目录，后面自动拼接文件的目录
        Path newPath = new Path( path + "/" + fileName );
        // 打开一个输出流
        FSDataOutputStream outputStream = fs.create( newPath );
        outputStream.write( file.getBytes( ) );
        outputStream.close( );
        fs.close( );
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "创建成功!" );

    }

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

    public ResultDto readPathInfo( String path ) throws Exception{
        FileSystem fs = HadoopUtil.getFileSystem( );
        Path newPath = new Path( path );
        FileStatus[] statusList = fs.listStatus( newPath );
        List <Map <String, Object>> list = new ArrayList <>( );
        if ( null != statusList && statusList.length > 0 ) {
            for ( FileStatus fileStatus : statusList ) {
                Map <String, Object> map = new HashMap <>( );
                map.put( "filePath" , fileStatus.getPath( ) );
                map.put( "fileStatus" , fileStatus.toString( ) );
                list.add( map );
            }
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "读取成功!" , list );
        } else {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "目录内容为空!" );

        }
    }

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

    public ResultDto deleteFile( String path ) throws Exception{
        FileSystem fs = HadoopUtil.getFileSystem( );
        Path newPath = new Path( path );
        boolean isOk = fs.deleteOnExit( newPath );
        fs.close( );
        if ( isOk ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "删除成功!" );
        } else {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "删除失败!" );
        }

    }

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

    public ResultDto downloadFile( String path , String downloadPath ) throws Exception{
        FileSystem fs = HadoopUtil.getFileSystem( );
        // 上传路径
        Path clientPath = new Path( path );
        // 目标路径
        Path serverPath = new Path( downloadPath );

        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fs.copyToLocalFile( false , clientPath , serverPath );
        fs.close( );
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "下载成功!" );

    }

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
}

