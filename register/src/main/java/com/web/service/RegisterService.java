package com.web.service;

import com.comment.api.BeanCopyUtil;
import com.comment.api.DateApi;
import com.comment.api.RedisUtil;
import com.comment.api.SendEmail;
import com.comment.util.EncryptionUtil;
import com.comment.util.PageDto;
import com.comment.util.ResultDto;
import com.comment.util.SysExcCode;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.web.dto.UserDto;
import com.web.entity.User;
import com.web.entity.UserType;
import com.web.mapper.UserMapper;
import com.web.mapper.UserTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.GeneralSecurityException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
    UserTypeMapper userTypeMapper;

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
        if ( checkExistName( userDto ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "用户名已经存在!" );
        }
        if ( checkExisUserId( userDto ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "账户名已经存在!" );
        }

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

        //设置用户角色
        UserType userType = new UserType( );
        userType.setUserId( user.getUserId( ) );
        userType.setUserType( userDto.getUserType( ) );
        int insert1 = userTypeMapper.insert( userType );

        if ( insert < 1 || insert1 < 1 ) {
            return new ResultDto(SysExcCode.SysCommonExcCode.SYS_ERROR, "注册失败");
        }
        return new ResultDto(SysExcCode.SysCommonExcCode.SYS_SUCCESS, "注册成功");
    }

    /**
     * 更新
     *
     * @param userDto
     * @return
     */
    public ResultDto update( UserDto userDto ){
        if ( checkExistName( userDto ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "用户名已经存在!" );
        }
        if ( checkExisUserId( userDto ) ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "账户名已经存在!" );
        }
        User user = new User( );
        BeanCopyUtil.copy( userDto , user );
        int i = userMapper.updateByPrimaryKeySelective( user );
        int updateSize = userMapper.updateSize( user );
        UserType userType = new UserType( );
        userType.setUserId( userDto.getUserId( ) );
        userType.setUserType( userDto.getUserType( ) );
        int u = userTypeMapper.updateByPrimaryKeySelective( userType );

        if ( i <= 0 ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "修改失败" );
        }
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "修改成功" );

    }

    /**
     * 判断是否用户名存在
     *
     * @param userDto
     * @return
     */
    boolean checkExistName( UserDto userDto ){
        if ( null == userDto.getName( ) ) {
            return false;
        }
        User user = new User( );
        user.setName( userDto.getName( ) );
        User selectOne = userMapper.selectOne( user );
        if ( null == selectOne ) {
            return false;
        }
        //说明是新增
        if ( null == userDto.getId( ) ) {
            return true;
        }
        //说明是更新为自己
        if ( userDto.getId( ).equals( selectOne.getId( ) ) ) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否账户存在
     *
     * @param userDto
     * @return
     */
    boolean checkExisUserId( UserDto userDto ){
        if ( null == userDto.getUserId( ) ) {
            return false;
        }
        User user = new User( );
        user.setUserId( userDto.getUserId( ) );
        User selectOne = userMapper.selectOne( user );
        if ( null == selectOne ) {
            return false;
        }
        //说明是新增
        if ( null == userDto.getId( ) ) {
            return true;
        }
        //说明是更新为自己
        if ( userDto.getId( ).equals( selectOne.getId( ) ) ) {
            return false;
        }
        return true;
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
        user.setUserId( userDto.getName( ) );
        UserDto selectUser = userMapper.joinIn( user );
        if ( null == selectUser ) {
            return new ResultDto(SysExcCode.SysCommonExcCode.SYS_ERROR, "用户名或者密码不正确");
        }
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "登录成功" , selectUser );
    }


    /**
     * 获取全部用户
     * 用户名，账户
     *
     * @param name
     * @return
     */
    public ResultDto getList( String name , PageDto pageDto ){
        Page <Object> objectPage = PageHelper.startPage( pageDto.getPageNo( ) , pageDto.getPageSize( ) );
        List <UserDto> userDtoList = userMapper.getList( name );
        for ( UserDto userDto : userDtoList ) {
            double sizeHadUsred = userMapper.sizeHadUsred( userDto.getUserId( ) );
            DecimalFormat df = new DecimalFormat( "###.000" );
            userDto.setSizeHadUsred( Double.parseDouble( df.format( (sizeHadUsred / 1024) ) ) );
        }
        pageDto.setTotalCount( objectPage.getTotal( ) );
        pageDto.setPageData( userDtoList );
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , pageDto );

    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */

    public ResultDto delete( String ids ){
        String[] split = ids.split( "," );
        List <String> id = new ArrayList <>( );
        for ( String s : split ) {
            id.add( s );
        }

        Integer d = userMapper.deleteByIds( id );
        if ( d <= 0 ) {
            return new ResultDto( SysExcCode.SysCommonExcCode.SYS_ERROR , "删除失败" );
        }
        return new ResultDto( SysExcCode.SysCommonExcCode.SYS_SUCCESS , "成功删除" + d + "条" );
    }

    /**
     * 获得注册数量
     *
     * @return
     */
    public ResultDto getUserNum (){
        List< User > users = userMapper.selectAll();
        return new ResultDto(SysExcCode.SysCommonExcCode.SYS_SUCCESS, "获取成功", users.size());
    }
}
