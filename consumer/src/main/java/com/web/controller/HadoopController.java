package com.web.controller;

import com.web.comment.unit.ResultDto;
import com.web.service.HadoopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/mkdir")
    public ResultDto mkdir( @RequestParam("path") String path ) throws Exception{
        return hadoopService.mkdir( path );
    }


    /**
     * 创建文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/createFile")
    public ResultDto createFile( @RequestParam("path") String path , @RequestParam("file") MultipartFile file ) throws Exception{
        return hadoopService.createFile( path , file );
    }


    /**
     * 读取HDFS文件内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readFile")
    public ResultDto readFile( @RequestParam("path") String path ) throws Exception{
        return hadoopService.readFile( path );
    }

    /**
     * 读取HDFS目录信息
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/readPathInfo")
    public ResultDto readPathInfo( @RequestParam("path") String path ) throws Exception{
        return hadoopService.readPathInfo( path );
    }

    /**
     * 读取文件列表
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/listFile")
    public ResultDto listFile( @RequestParam("path") String path ) throws Exception{
        return hadoopService.listFile( path );
    }

    /**
     * 重命名文件
     *
     * @param oldName
     * @param newName
     * @return
     * @throws Exception
     */
    @PostMapping("/renameFile")
    public ResultDto renameFile( @RequestParam("oldName") String oldName , @RequestParam("newName") String newName ) throws Exception{
        return hadoopService.renameFile( oldName , newName );
    }

    /**
     * 删除文件
     *
     * @param path
     * @return
     * @throws Exception
     */
    @PostMapping("/deleteFile")
    public ResultDto deleteFile( @RequestParam("path") String path ) throws Exception{
        return hadoopService.deleteFile( path );
    }


    /**
     * 上传文件
     *
     * @param path
     * @param uploadPath
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public ResultDto uploadFile( @RequestParam("path") String path , @RequestParam("uploadPath") String uploadPath ) throws Exception{
        return hadoopService.uploadFile( path , uploadPath );
    }


    /**
     * 下载文件
     *
     * @param path
     * @param downloadPath
     * @return
     * @throws Exception
     */
    @PostMapping("/downloadFile")
    public ResultDto downloadFile( @RequestParam("path") String path , @RequestParam("downloadPath") String downloadPath ) throws Exception{
        return hadoopService.downloadFile( path , downloadPath );
    }

    /**
     * HDFS文件复制
     *
     * @param sourcePath
     * @param targetPath
     * @return
     * @throws Exception
     */
    @PostMapping("/copyFile")
    public ResultDto copyFile( @RequestParam("sourcePath") String sourcePath , @RequestParam("targetPath") String targetPath ) throws Exception{
        return hadoopService.copyFile( sourcePath , targetPath );
    }

}
