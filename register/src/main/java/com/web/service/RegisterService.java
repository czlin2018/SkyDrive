package com.web.service;

import com.comment.api.BeanCopyUtil;
import com.comment.api.DateApi;
import com.comment.api.RedisUtil;
import com.comment.api.SendEmail;
import com.comment.util.EncryptionUtil;
import com.comment.util.ResultDto;
import com.comment.util.SysExcCode;
import com.web.dto.UserDto;
import com.web.entity.User;
import com.web.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-27
 * @创建时间: 下午4:06
 */
@Service
public class RegisterService{

    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisUtil redisUtil;

    public String hello  ( ){
        return "连上了注册服务";
    }


    /**
     * 注册用户
     *
     * @param userDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ResultDto insert (UserDto userDto){
        User user = new User();
        BeanCopyUtil.copy(userDto, user);

        //从redis取得验证码,判断验证码是否正确
        Object verificationCode = redisUtil.find(userDto.getUserId());
        if(! userDto.getVerificationCode().equals(verificationCode)){
            return new ResultDto(SysExcCode.SysCommonExcCode.SYS_ERROR, "验证码输入不正确");
        }

        //调用md5进行加密
        String md5 = EncryptionUtil.getMD5(userDto.getPassword());
        user.setPassword(md5);
        user.setUpdateTime(DateApi.currentDateTime());
        int insert = userMapper.insert(user);
        if(insert < 1){
            return new ResultDto(SysExcCode.SysCommonExcCode.SYS_ERROR, "注册失败");
        }
        return new ResultDto(SysExcCode.SysCommonExcCode.SYS_SUCCESS, "注册成功");
    }

    /**
     * 获得验证码
     *
     * @param userId 用户id (邮箱)
     * @return
     */
    public ResultDto getVerificationCode (String userId){
        String verificationCode = DateApi.getTimeId();

        //注册在redis
        redisUtil.register(userId, verificationCode);

        //发送电子邮件
        try{
            SendEmail.send(userId, verificationCode);
        }catch(GeneralSecurityException e){
            return new ResultDto(SysExcCode.SysCommonExcCode.SYS_ERROR, "获取失败");
        }
        return new ResultDto(SysExcCode.SysCommonExcCode.SYS_SUCCESS, verificationCode);

    }

    /**
     * 登录,账户名获得账号登录
     *
     * @param userDto 用户类
     * @return
     */
    public ResultDto joinIn (UserDto userDto){

        String userName = null;
        //用户名登录
        User user = new User();
        user.setPassword(EncryptionUtil.getMD5(userDto.getPassword()));
        user.setName(userDto.getName());
        User selectUser1 = userMapper.selectOne(user);
        if ( null != selectUser1 ) {
            userName = selectUser1.getName( );
        }
        //账号登录
        user = new User();
        user.setPassword(EncryptionUtil.getMD5(userDto.getPassword()));
        user.setUserId(userDto.getName());
        User selectUser2 = userMapper.selectOne(user);
        if ( null != selectUser2 ) {
            userName = selectUser2.getName( );
        }
        if(null == selectUser2 && null == selectUser1){
            return new ResultDto(SysExcCode.SysCommonExcCode.SYS_ERROR, "用户名或者密码不正确");
        }
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "登录成功" , userName );
    }
}
