package com.web.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Table(name = "user")
public class User implements Serializable {

    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 账户
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 密码
     */
    private String password;


    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "size")
    private double size;

}