package com.consumer.controller;

import com.web.controller.ConsumerController;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-28
 * @创建时间: 下午12:06
 */
public class ConsumerControllerTest{


    @Autowired
    ConsumerController consumerController;

    //@Test
    public  void run1() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            try {
                Thread.sleep(index * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int j = 0; j < 10; j++){
                System.out.print(j+"线程:");
                cachedThreadPool.execute(new Runnable(){
                    public void run (){
                        System.out.println(index);
                    }
                });
            }
        }
    }

    // @Test
    public void main (){
        consumerController.hi("MY");
    }
}