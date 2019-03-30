package com.web.controller;

import com.comment.util.ResultDto;
import com.web.service.Service01;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    /**
     *
     */
    @GetMapping(value = "/hello1")
    public ResultDto updateWorkOrderReturnDelayReason (@RequestParam("name") String name){
        return service01.in(name);
    }


}
