package com.web.comment.thread;

import com.web.comment.api.HttpGet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @描述: 简单线程池
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-28
 * @创建时间: 下午3:41
 */
public class LocalThreadApi implements Runnable{
    private String name;

    public LocalThreadApi (String name){
        this.name = name;
    }

    @Override
    public void run (){
        Map map = new HashMap();
        map.put("name", "1111");
        String s = HttpGet.sendGet("http://localhost:8091/hi", map);
        System.out.println(this.name + "线程结果:" + s);
    }


    public static void createThread (int n){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue< Runnable >(5));
        for(int i = 0 ; i < n ; i++){
            LocalThreadApi m1 = new LocalThreadApi(String.valueOf(i));
            executor.execute(m1);
        }

        System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        executor.shutdown();

    }


    public static void main (String[] arg){

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue< Runnable >(5));
        for(int i = 0 ; i < 20 ; i++){
            LocalThreadApi m1 = new LocalThreadApi("A");
            executor.execute(m1);
        }
        System.out.println("线程池中线程数目：" + executor.getPoolSize() + "，队列中等待执行的任务数目：" +
                executor.getQueue().size() + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        executor.shutdown();

    }
}