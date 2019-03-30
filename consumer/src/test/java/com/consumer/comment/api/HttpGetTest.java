package com.consumer.comment.api;

import com.web.comment.api.HttpGet;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-03-19-3-1
 * @创建时间: 下午4:52
 */
public class HttpGetTest{

    /// @Test
    public void sendGet (){
        Map map = new HashMap();
        map.put("name", "1111");
        String s = HttpGet.sendGet("http://localhost:8091/hi", map);
        System.out.println(s);
    }
}