package com.comment.util.thread;

/**
 * @描述:
 * @公司: lumi
 * @author: 泽林
 * @创建日期: 2019-03-19-3-1
 * @创建时间: 下午4:02
 */
public class LocalThreadApITest{

    public static void main (String[] arg){
        LocalThreadPollApI.createThread(10, new ThreadTask());
    }
}