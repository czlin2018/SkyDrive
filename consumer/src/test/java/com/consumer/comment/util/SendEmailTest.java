package com.consumer.comment.util;

import com.web.comment.api.SendEmail;

import java.security.GeneralSecurityException;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-28
 * @创建时间: 下午5:18
 */
public class SendEmailTest{

    //  @Test
    public void send (){
        try{
            SendEmail.Send("953564227@qq.com","hello");
        }catch(GeneralSecurityException e){
            e.printStackTrace();
        }
    }
}