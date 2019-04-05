package com.web.dto;

import com.web.entity.User;
import lombok.Data;

@Data
public class UserDto extends User{
    /**
     * 邮箱验证码
     */
    private String verificationCode;
}