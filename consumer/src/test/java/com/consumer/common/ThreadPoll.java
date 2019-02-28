package com.consumer.common;

import org.apache.tomcat.jni.Time;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-28
 * @创建时间: 下午5:37
 */
public class ThreadPoll{
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));

        for(int i=0;i<15;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                    executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }
}


class MyTask implements Runnable {
    private int taskNum;

    public MyTask(int num) {
        this.taskNum = num;
    }

    @Override
    public void run() {
        System.out.println("正在执行task "+taskNum);
        System.out.println("task "+taskNum+"执行完毕");
    }
}