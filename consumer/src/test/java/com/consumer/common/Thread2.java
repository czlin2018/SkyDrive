package com.consumer.common;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-28
 * @创建时间: 下午3:41
 */
public class Thread2 implements Runnable{
    private String name;

    public Thread2 (String name){
        this.name = name;
    }

    @Override
    public void run (){
        for(int i = 0 ; i < 100000; i++){
            System.out.println(this.name+"执行:"+i);

        }
    }

    @Test
    public static void main(String[] arg){
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
            Thread2 m1=new Thread2("A");
            Thread2 m2=new Thread2("B");
            executor.execute(m1);
            executor.execute(m2);

            System.out.println("线程池中线程数目："+executor.getPoolSize()+"，队列中等待执行的任务数目："+
                executor.getQueue().size()+"，已执行玩别的任务数目："+executor.getCompletedTaskCount());
            executor.shutdown();
//             m1.setPriority(1);
//             m2.setPriority(10);
//             m1.start();
//             m2.start();

    }
}
