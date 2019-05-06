package com.web.service;

import com.web.entity.ResourcePath;
import com.web.mapper.ResourcePathMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-05-19-5-6
 * @创建时间: 下午3:00
 */
@Service
public class ResourcePathService{

    @Autowired
    ResourcePathMapper resourcePathMapper;

    /**
     * 插入
     */
    public boolean addResourcePath (ResourcePath path){
        int i = resourcePathMapper.insertSelective(path);
        if(i <= 0){
            return false;
        }
        return true;
    }

    /**
     * 删除
     */
    public boolean delResourcePath (String path){
        int i = resourcePathMapper.deleteAllPath(path);
        if(i <= 0){
            return false;
        }
        return true;
    }


}
