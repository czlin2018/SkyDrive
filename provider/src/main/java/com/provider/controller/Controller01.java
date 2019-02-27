package com.provider.controller;

import com.netflix.discovery.converters.Auto;
import com.provider.service.Service01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-27
 * @创建时间: 下午2:41
 */
@RestController
public class Controller01{

    @Autowired
    Service01 service01;

    @GetMapping(value = "/hello")
    public String  hello ( ){
        return service01.hello();
    }
}
