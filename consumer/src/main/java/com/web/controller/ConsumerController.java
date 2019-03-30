package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-27
 * @创建时间: 下午7:18
 */
@RestController
public class ConsumerController{
    @Autowired
    RestTemplate restTemplate;

@GetMapping(value = "/hi")
public String hi  ( String  name){
    System.out.println("hi");
    String forObject = restTemplate.getForObject("http://register/hello", String.class);
    return name + "say:" + forObject;
}

    @GetMapping(value = "/hi1")
    public String hi1 (String name){
        System.out.println("hi");
        Map map = new HashMap();
        map.put("name", name);
        String forObject = restTemplate.getForObject("http://register/hello1", String.class, map);
        return name + "say:" + forObject;
    }
}
