package com.consumer.comment.thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述: 线程工厂类
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-03-19-3-1
 * @创建时间: 下午2:59
 */

class ThreadFactory implements java.util.concurrent.ThreadFactory{
    private static String FACTORYNAME = "ThreadFactory";
    private static List< ? super Thread > threadList = new ArrayList<>();

    public void setFactoryName (String name){
        FACTORYNAME = name;
    }

    @Override
    public Thread newThread (Runnable r){
        synchronized(r){
            //这里可以自定义个Thread，用了处理在创建线程前后预处理
            Thread t = new Thread(r, FACTORYNAME + threadList.size());
            threadList.add(t);
            return t;
        }
    }
}
