package com.consumer.comment.thread;

import com.web.comment.thread.LocalThreadApi;
import com.web.comment.thread.LocalThreadPollApI;
import com.web.comment.thread.ThreadTask;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-03-19-3-1
 * @创建时间: 下午4:15
 */
public class LocalThreadApITest{
    //@Test
    public void run1 (){
        LocalThreadPollApI.createThread(4, new ThreadTask());
    }

    //@Test
    public void run2 (){
        LocalThreadApi.createThread(1);
    }

}