package com.web.service;

import com.web.entity.Resource;
import com.web.entity.ResourcePath;
import com.web.mapper.ResourceMapper;
import com.web.mapper.ResourcePathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-05-19-5-6
 * @创建时间: 下午3:00
 */
@Service
public class ResourceService{

    @Autowired
    ResourcePathMapper resourcePathMapper;
    @Autowired
    ResourceMapper resourceManager;

    /**
     * 防止插入根目录
     */
    public boolean selectFirst( String nodeName , String nodePath ){

        int i = resourcePathMapper.selectFirst( nodeName , nodePath );
        if ( i == 0 ) {
            return false;
        }
        return true;
    }


    /**
     * 插入文件表
     */
    public boolean addResourcePath (ResourcePath path){
        int i = resourcePathMapper.insertSelective(path);
        if(i <= 0){
            return false;
        }
        return true;
    }

    /**
     * 删除文件表
     */
    public boolean delResourcePath (String path){
        //删除文件夹,及下面文件夹
        int i = resourcePathMapper.deleteAllPath(path);

        //删除文件夹下文件,及下文件
        int i1 = resourcePathMapper.deleteFileAndPath(path);

        if ( i <= 0 ) {
            return false;
        }
        return true;
    }

    /**
     * 插入资源表
     */
    public boolean addResource (Resource resource){
        int i = resourceManager.insertSelective(resource);
        if(i <= 0){
            return false;
        }
        return true;
    }

    /**
     * 删除文件表
     */
    public boolean delResource (String path){
        int i = resourcePathMapper.deleteFile(path);
        if(i <= 0){
            return false;
        }
        return true;
    }

    /**
     * 读取HDFS目录信息 -----DB
     *
     * @param path
     * @param userId
     * @param userType
     * @return
     * @throws Exception
     */
    public List <Map <String, Object>> readPathInfoFromDb( String path , String userId , String userType ){
        if ( "/".equals( path ) ) {
            path = "";
        }
        List <Map <String, Object>> list = resourcePathMapper.readPathInfoFromDb( path , userId , userType );
        return list;
    }
}
