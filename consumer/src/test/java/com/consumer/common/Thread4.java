package com.consumer.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-03-19-3-1
 * @创建时间: 下午2:45
 */
public class Thread4 {
    private static Runnable getThread(final int i){
        return new Runnable() {
            @Override
            public void run() {
                try {

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        };
    }

    public static void main(String args[]) throws InterruptedException {
        ExecutorService singPool = Executors.newFixedThreadPool(100);
        for (int i=0;i<100;i++){
            singPool.execute(getThread(i));
        }
        singPool.shutdown();
    }
}