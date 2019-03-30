package com.comment.api;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 发送短信服务
 */
@Component
public class SmsUtils{
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";
    /**
     * accessKeyId: LTAIzLYbEWAj5jcZ
     * accessKeySecret: cF1HRFRjlxljjcf6ypkHmk4RTn6Gqf
     */

    @Value("${DX.accessKeyId}")
    private String accessKeyId = "LTAIzLYbEWAj5jcZ";
    @Value("${DX.accessKeySecret}")
    private String accessKeySecret = "cF1HRFRjlxljjcf6ypkHmk4RTn6Gqf";


    // 发送方法
    public SendSmsResponse sendSms (String phoneNumbers, String code) throws ClientException{
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumbers);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("你的短信签名");
        //必填:短信模板-可在短信控制台中找到     
        request.setTemplateCode("你的模板Id");
        //设置参数
        request.setTemplateParam("{\"code\":" + code + "}");
        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("123");
        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }

    // 查询方法
    public QuerySendDetailsResponse querySendDetails (String phoneNumber) throws ClientException{
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNumber);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);
        return querySendDetailsResponse;
    }


    public void testSend () throws Exception{
        SendSmsResponse response = sendSms("13420110632", "456789");
        System.out.println(response.getCode());
        System.out.println(response.getMessage());
        QuerySendDetailsResponse qr = querySendDetails("13420110632");
        System.out.println("该用户收到的短信条数：" + qr.getTotalCount());
        for(QuerySendDetailsResponse.SmsSendDetailDTO o : qr.getSmsSendDetailDTOs()){
            System.out.println("发送时间：" + o.getSendDate());
        }

    }
}
