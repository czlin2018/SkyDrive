package com.web.comment.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述: 任务线程类1
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-03-19-3-1
 * @创建时间: 下午3:14
 */

@Slf4j
public
class ThreadTask extends Thread{

    @Override
    public void run (){

        System.out.println(this.getName() + ", 正执行任务 ");
        //执行的任务
        System.out.println(this.getName() + ", 任务执行完成");
        System.out.println();
    }

}