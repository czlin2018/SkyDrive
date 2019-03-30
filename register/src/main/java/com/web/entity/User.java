package com.web.entity;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
@Data
public class User implements Serializable {
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
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}