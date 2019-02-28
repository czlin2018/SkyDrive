package com.consumer.common;

import org.junit.Test;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-02-19-2-28
 * @创建时间: 下午3:03
 */
class Thread1 extends Thread {
    private  String name;

    public Thread1 (String name){
        this.name = name;
    }

    public void run ( ){
        for(int i = 0 ; i < 5 ; i++){
            System.out.println(this.name+"执行:"+i);
            try{
                sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    @Test
    public static void main1(String[] arg){
        for(int i=0;i<1000;i++){
        Thread1 m1=new Thread1("A"+i);
        m1.start();
        }
    }

    @Test
    public static void main(String[] arg){
        for(int i=0;i<1000;i++){
            Thread1 m1=new Thread1("A"+i);
            m1.run();
        }
    }

}