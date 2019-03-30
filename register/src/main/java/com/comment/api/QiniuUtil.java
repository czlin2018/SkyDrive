package com.comment.api;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;


@Component
public class QiniuUtil{

    private final Logger logger = LoggerFactory.getLogger(QiniuUtil.class);
    /**
     * accessKey: TcdYzawyHUr6B7-0J-IHaW4CHgKK_VGWR2A75CdV
     * secretKey: ot9p_yHwX7NADp-OZQ5Ekw1qttq6oXp8oETp5u0Y
     * bucket: iimage
     * path: phk8c13oo.bkt.clouddn.com
     */
    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;

    @Value("${qiniu.path}")
    private String path;

    /**
     * 将图片上传到七牛云
     *
     * @param file
     * @param key  保存在空间中的名字，以时间措为名字
     * @return
     */
    public String uploadImg (FileInputStream file, String key){
        //构造一个带指定Zone对象的配置类,華南
        Configuration cfg = new Configuration(Zone.zone2());

//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try{
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try{
                Response response = uploadManager.put(file, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
                String return_path = path + "/" + putRet.key;
                logger.info("保存地址={}", return_path);
                return return_path;
            }catch(QiniuException ex){
                Response r = ex.response;
                System.err.println(r.toString());
                try{
                    System.err.println(r.bodyString());
                }catch(QiniuException ex2){
                    //ignore
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
