package com.web.controller;

import com.web.comment.unit.PageDto;
import com.web.comment.unit.ResultDto;
import com.web.dto.HadoopDto;
import com.web.service.HadoopService;
import com.web.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 类或方法的功能描述 :TODO
 *
 * @date: 2018-11-28 13:51
 */
@RestController
public class HadoopController {

    @Autowired
    HadoopService hadoopService;

    @Autowired
    ResourceService resourceService;

    /**
     * 创建文件夹
     *
     * @param hadoopDto
     * @return
     * @throws Exception
     */
    @PostMapping("/mkdir")
    public ResultDto mkdir( @RequestBody HadoopDto hadoopDto ) throws Exception{
        return hadoopService.mkdir( hadoopDto.getPath( ) , hadoopDto.getUserId( ) );
    }


    /**
     * 创建文件
     * @param path
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/createFile")
    public ResultDto createFile( @RequestParam("path") String path , @RequestParam("userId") String userId , @RequestParam("file") MultipartFile file ) throws Exception{
        return hadoopService.createFile( path , userId , file );
    }

    /**
     * 读取HDFS目录信息
     *
     * @param path
     * @return
     * @throws Exception
     */

    @GetMapping("/readPathInfo")
    public ResultDto readPathInfo( String path , String userId , String userType , PageDto pageDto ) throws Exception{
        // return hadoopService.readPathInfo( path , userId , pageDto );
        return hadoopService.readPathInfoFromDb( path , userId , pageDto , userType );
    }

    /**
     * 读取HDFS文件内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    @GetMapping("/readFile")
    public ResultDto readFile( String path ) throws Exception{
        return hadoopService.readFile( path );
    }



    /**
     * 读取文件列表
     *
     * @param path
     * @return
     * @throws Exception
     */
    @GetMapping("/listFile")
    public ResultDto listFile( String path ) throws Exception{
        return hadoopService.listFile( path );
    }

    /**
     * 重命名文件
     *
     * @param hadoopDto
     * @return
     * @throws Exception
     */
    @PostMapping("/renameFile")
    public ResultDto renameFile( @RequestBody HadoopDto hadoopDto ) throws Exception{
        return hadoopService.renameFile( hadoopDto.getOldName( ) , hadoopDto.getNewName( ) );
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    @GetMapping("/deleteFile")
    public ResultDto deleteFile( String path ) throws Exception{
        return hadoopService.deleteFile( path );
    }


    /**
     * 上传文件2
     *
     * @param hadoopDto
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public ResultDto uploadFile( @RequestBody HadoopDto hadoopDto ) throws Exception{
        return hadoopService.uploadFile( hadoopDto.getPath( ) , hadoopDto.getUploadPath( ) );
    }


    /**
     * 下载文件
     *
     * @param hadoopDto
     * @return
     * @throws Exception
     */
    @PostMapping("/downloadFile")
    public ResultDto downloadFile( @RequestBody HadoopDto hadoopDto ) throws Exception{
        hadoopDto.setDownloadPath( "/home/czl/桌面/下载" );
        return hadoopService.downloadFile( hadoopDto.getPath( ) , hadoopDto.getDownloadPath( ) );
    }

    /**
     * HDFS文件复制
     *
     * @param hadoopDto
     * @return
     * @throws Exception
     */
    @PostMapping("/copyFile")
    public ResultDto copyFile( @RequestBody HadoopDto hadoopDto ) throws Exception{
        return hadoopService.copyFile( hadoopDto.getSourcePath( ) , hadoopDto.getTargetPath( ) );
    }

    /**
     * 生成分享码
     *
     * @param hadoopDto
     * @return
     * @throws Exception
     */
    @PostMapping("/createCode")
    public ResultDto readPathInfo (@RequestBody HadoopDto hadoopDto) throws Exception{

        return hadoopService.createCode(hadoopDto.getFullPath());
    }

}
