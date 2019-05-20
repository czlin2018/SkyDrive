package com.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

import javax.servlet.MultipartConfigElement;

@MapperScan(basePackages = "com.web.mapper")
@EnableEurekaClient
@SpringBootApplication
public class ConsumerApplication{

    public static void main( String[] args ){
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @LoadBalanced
    @Bean
    public RestTemplate restTemplate ( ){
        return new RestTemplate();
    }

    /**
     * 文件上传临时路径
     */
    @Bean
    MultipartConfigElement multipartConfigElement( ){
        MultipartConfigFactory factory = new MultipartConfigFactory( );
        factory.setLocation( "/home/czl/文档/SkyDrive" );
        return factory.createMultipartConfig( );
    }
}
