package com.web.comment.thread;

import com.web.comment.api.DateApi;
import com.web.comment.api.HadoopUtil;
import com.web.entity.Resource;
import com.web.mapper.ResourceMapper;
import com.web.service.ResourceService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

/**
 * @描述: hadoop上传任务线程类
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-03-19-3-1
 * @创建时间: 下午3:14
 */

@Slf4j
@Data
public class ThreadTaskForUpload extends Thread {

    @Autowired
    ResourceMapper resourceMapper;
    @Autowired
    ResourceService resourceService;

    MultipartFile file;
    String fullPath;
    String path;
    String userId;

    public ThreadTaskForUpload( String path , String userId , MultipartFile file , String fullPath ){
        this.file = file;
        this.fullPath = fullPath;
        this.path = path;
        this.userId = userId;
    }

    @Override
    public void run( ){
        try {
            String fileName = file.getOriginalFilename( );
            String fullPath = path + "/" + fileName;
            //插入文件表
            Resource resource = new Resource( );
            resource.setUserId( userId );
            resource.setResourceId( DateApi.getTimeId( ) );
            resource.setResourceName( fileName );
            resource.setPath( path );
            resource.setFullPath( fullPath );
            resource.setStatus( 0 );
            resource.setUpdateTime( DateApi.currentDateTime( ) );
            //resourceService.addResource( resource );
            System.out.println( "上传中..." );
            FileSystem fs = null;
            fs = HadoopUtil.getFileSystem( );

            // 上传时默认当前目录，后面自动拼接文件的目录
            Path newPath = new Path( fullPath );
            // 打开一个输出流
            FSDataOutputStream outputStream = fs.create( newPath );
            outputStream.write( file.getBytes( ) );
            outputStream.close( );
            // fs.close( );
            System.out.println( "上传完成..." );
            resource.setStatus( 1 );
            // resourceMapper.updateByPrimaryKey( resource );
        } catch (Exception e) {
            e.printStackTrace( );
        }
    }

}