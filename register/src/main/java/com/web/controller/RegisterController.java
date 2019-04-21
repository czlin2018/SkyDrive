package com.web.controller;

import com.comment.url.Url;
import com.comment.util.PageDto;
import com.comment.util.ResultDto;
import com.web.dto.UserDto;
import com.web.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-27
 * @创建时间: 下午2:41
 */
@RestController
public class RegisterController{

    @Autowired
    RegisterService registerService;

    /**
     * 测试联通接口
     *
     * @return
     */
    @GetMapping(value = "/hello")
    public String hello (){
        return registerService.hello();
    }

    /**
     * 注册用户
     *
     * @param userDto 用户类
     * @return
     */
    @PostMapping(value = Url.REGISTER.INSERT)
    public ResultDto updateWorkOrderReturnDelayReason (@RequestBody UserDto userDto){
        return registerService.insert(userDto);
    }

    /**
     * 获得验证码
     *
     * @param userId 用户id (邮箱)
     * @return
     */
    @GetMapping(value = Url.REGISTER.GET_VERIFICATION_CODE)
    public ResultDto getVerificationCode (String userId){
        return registerService.getVerificationCode(userId);
    }

    /**
     * 登录,账户名获得账号登录
     *
     * @param userDto 用户类
     * @return
     */
    @PostMapping(value = Url.REGISTER.LOGIN)
    public ResultDto joinIn (@RequestBody UserDto userDto){
        return registerService.joinIn(userDto);
    }

    /**
     * 获取全部用户
     * 用户名，账户
     *
     * @param name
     * @return
     */
    @GetMapping(value = Url.REGISTER.GETLIST)
    public ResultDto getList( String name , PageDto pageDto ){
        return registerService.getList( name , pageDto );
    }

    /**
     * 获取全部用户
     * 用户名，账户
     *
     * @param ids
     * @return
     */
    @GetMapping(value = Url.REGISTER.DELETE)
    public ResultDto delete( String ids ){
        return registerService.delete( ids );
    }

    /**
     * 注册用户
     *
     * @param userDto 用户类
     * @return
     */
    @PostMapping(value = Url.REGISTER.UPDATE)
    public ResultDto update( @RequestBody UserDto userDto ){
        return registerService.update( userDto );
    }



}
