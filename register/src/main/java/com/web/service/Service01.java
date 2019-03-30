package com.web.service;

import com.comment.util.ResultDto;
import com.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-27
 * @创建时间: 下午4:06
 */
@Service
public class Service01{

    @Autowired
    UserMapper userMapper;

    public String hello  ( ){
        return "hello,im yours111";
    }

    public ResultDto in (String name){
        String passage = userMapper.selectPassage(name);
        return new ResultDto(0, "0", passage);
    }
}
