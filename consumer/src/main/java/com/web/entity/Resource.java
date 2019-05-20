package com.web.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class Resource implements Serializable {
    private Integer id;

    @Id
    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "resource_name")
    private String resourceName;

    private String path;

    @Column(name = "full_path")
    private String fullPath;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "status")
    private Integer status;


}