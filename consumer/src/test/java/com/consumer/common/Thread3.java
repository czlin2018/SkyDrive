package com.consumer.common;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-28
 * @创建时间: 下午3:41
 */
public class Thread3 extends Thread implements Runnable{
    private String name;

    public Thread3 (String name){
        this.name = name;
    }

    @Override
    public void run (){
        for(int i = 0 ; i < 10; i++){
            System.out.println(this.name+"执行:"+i);

        }
    }

    // @Test
    public static void main(String[] arg){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));



        for(int i=0; i<5; i++){
            Thread3 m1=new Thread3("A"+i);
            executor.execute(m1);
            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                    executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
            }
        executor.shutdown();

    }


}
