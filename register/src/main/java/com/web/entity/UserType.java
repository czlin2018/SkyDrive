package com.web.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "userType")
public class UserType implements Serializable {


    @Column(name = "id")
    private Integer id;

    /**
     * 姓名
     */

    @Column(name = "user_type")
    private String userType;

    /**
     * 账户
     */
    @Id
    @Column(name = "user_id")
    private String userId;


}