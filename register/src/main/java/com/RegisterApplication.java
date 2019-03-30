package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.web.mapper")
@SpringBootApplication
public class RegisterApplication{

    public static void main (String[] args){
        SpringApplication.run(RegisterApplication.class, args);
    }

}
