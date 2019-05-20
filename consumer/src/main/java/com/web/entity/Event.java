package com.web.entity;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;

@Data
public class Event implements Serializable {
    private Integer id;

    private String event;

    @Column(name = "user_id")
    private String userId;

    private String status;

    @Column(name = "update_time")
    private Date updateTime;


    @Column(name = "to_admin")
    private  Integer toAdmin;


}