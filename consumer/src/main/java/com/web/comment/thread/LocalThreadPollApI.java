package com.web.comment.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @描述: 提供给外部的线程Api, 加工厂类的线程池
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-03-19-3-1
 * @创建时间: 下午3:36
 */
public class LocalThreadPollApI{
    private static ThreadPoolExecutor executor = null;

    static{
        executor = new ThreadPoolExecutor(6, 7, 200,
                TimeUnit.SECONDS, new ArrayBlockingQueue< Runnable >(5), new ThreadFactory(), new ThreadPoolExecutor.AbortPolicy()
        );

        //预启动一个线程
        executor.prestartCoreThread();

        //预启动corePoolSize个线程到线程池
        //executor.prestartAllCoreThreads();
    }

    public static void createThread (int n, Thread thread){

        for(int i = 1 ; i <= n ; i++){

            //       System.out.println("线程池是否关闭:"+executor.isShutdown());
            thread.setName("thread-" + i);
            executor.execute(thread);
            System.out.println("线程池线程数： " + executor.getPoolSize() + ";阻塞队列中线程数：" + executor.getQueue().size() + ";已执行完线程数：" + executor.getCompletedTaskCount());

        }

        executor.shutdown();
    }

}
/**
 * corePoolSize	核心线程池大小
 * maximumPoolSize	最大线程池大小
 * keepAliveTime	线程池中超过corePoolSize数目的空闲线程最大存活时间；可以allowCoreThreadTimeOut(true)使得核心线程有效时间
 * TimeUnit	keepAliveTime时间单位
 * TimeUnit.DAYS          //天
 * TimeUnit.HOURS         //小时
 * TimeUnit.MINUTES       //分钟
 * TimeUnit.SECONDS       //秒
 * TimeUnit.MILLISECONDS  //毫秒
 * TimeUnit.NANOSECONDS   //毫微秒
 * TimeUnit.MICROSECONDS  //微秒
 * workQueue	阻塞任务队列
 * threadFactory	新建线程工厂
 * RejectedExecutionHandler	当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
 */