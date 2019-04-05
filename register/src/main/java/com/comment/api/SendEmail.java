package com.comment.api;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-28
 * @创建时间: 下午5:00
 */

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

public class SendEmail
{
    public static String send (String to, String text) throws GeneralSecurityException
    {
        // 收件人电子邮箱
        //to = "953564227@qq.com";

        // 发件人电子邮箱
        String from = "953564227@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            @Override
            public PasswordAuthentication getPasswordAuthentication()
            {
                //发件人邮件用户名、密码
                return new PasswordAuthentication(from, "ehpjzbiiekpbbfaf");
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("陈泽林的毕业设计网盘注册验证码");

            // 设置消息体
            message.setText("验证码:" + text);

            // 发送消息
            Transport.send(message);

        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
        return text;
    }
}