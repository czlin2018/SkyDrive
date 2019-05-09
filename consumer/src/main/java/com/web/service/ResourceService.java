package com.web.service;

import com.web.comment.api.DateApi;
import com.web.entity.Resource;
import com.web.entity.ResourceMapping;
import com.web.entity.ResourcePath;
import com.web.entity.Share;
import com.web.mapper.ResourceMapper;
import com.web.mapper.ResourceMappingMapper;
import com.web.mapper.ResourcePathMapper;
import com.web.mapper.ShareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    ResourceMappingMapper resourceMappingMapper;
    @Autowired
    ShareMapper shareMapper;

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
        List <Map <String, Object>> list = resourcePathMapper.readPathInfoFromDb( path , userId , userType );
        for(Map< String, Object > stringObjectMap : list){
            String strFormat = "yyyy-MM-dd HH:mm:ss";
            DateFormat df = new SimpleDateFormat(strFormat);
            String str = df.format(stringObjectMap.get("updateTime"));
            stringObjectMap.put("updateTime", str);
        }
        return list;
    }

    /**
     * 获得分享码
     */
    public String createCode (String fullPath){
        String resourceId = resourcePathMapper.getResourceId(fullPath);
        ResourceMapping resourceMapping = new ResourceMapping();
        resourceMapping.setMappingId(DateApi.getTimeId());
        resourceMapping.setResourceId(resourceId);
        resourceMapping.setTakeCode(String.valueOf(new Date().getTime()));
        resourceMapping.setUpdateTime(DateApi.currentDateTime());
        int i = resourceMappingMapper.insertSelective(resourceMapping);
        if(i > 0){
            return resourceMapping.getTakeCode();
        }
        return null;
    }

    /**
     * 获得分享
     */
    public boolean getCode( String path , String fileCodeFromOter , String userId ){
        String resourceId = resourcePathMapper.getResourceIdFromMapper( fileCodeFromOter );
        Share share = new Share( );

        if ( path.length( ) > 2 && path.substring( path.length( ) - 1 , path.length( ) ).equals( "/" ) ) {
            path = path.substring( 0 , path.length( ) - 1 );
        }
        share.setShareId(DateApi.getTimeId());
        share.setInventedPath( path );
        share.setResourceId( resourceId );
        share.setUserId( userId );
        share.setUpdateTime( DateApi.currentDateTime( ) );
        int i = shareMapper.insertSelective( share );
        if ( i <= 0 ) {
            return false;
        }
        return true;
    }

    /**
     * 删除分享表文件
     *
     * @param path
     */
    public void delSharedResource( String path ){
        String resourceId = resourcePathMapper.getResourceId( path );
        //删除分享表
        resourcePathMapper.delSharedResource( resourceId );
        //删除映射表
        resourcePathMapper.delMappingResource( resourceId );
    }

    /**
     * 根据id删除分享表文件
     */
    public boolean delSharedResourceId (String id){
        //删除分享表
        int i = resourcePathMapper.delSharedResourceId(id);
        if(i > 0)
            return true;
        return false;
    }
}
