package com.web.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class Share implements Serializable {

    private Integer id;
    @Id
    @Column(name = "user_id")
    private String userId;
    @Column(name = "resource_id")
    private String resourceId;

    @Column(name = "invented_path")
    private String inventedPath;

    @Column(name = "update_time")
    private Date updateTime;

}