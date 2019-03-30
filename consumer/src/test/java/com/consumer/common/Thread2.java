package com.consumer.common;

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
        for(int i = 0 ; i < 5 ; i++){
            System.out.println(this.name+"执行:"+i);
            try{
                sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    // @Test
    public static void main1(String[] arg){
        for(int i=0;i<1000;i++){
            Thread1 m1=new Thread1("A"+i);
            m1.start();
        }
    }

    // @Test
    public static void main(String[] arg){
            Thread1 m1=new Thread1("A");
             Thread1 m2=new Thread1("B");
             m1.setPriority(1);
             m2.setPriority(10);
             m1.start();
             m2.start();

    }
}
