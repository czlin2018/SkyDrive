package com.comment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @描述:
 * @版权: Copyright (c) 2016-2018
 * @公司: lumi
 * @作者: 泽林
 * @创建日期: 2018-11-05
 * @创建时间: 下午6:22
 */

@Component
public class RedisUtil{

    @Autowired
    private RedisTemplate< Object, Object > redisTemplate;


    /**
     * 注册redis
     */
    public void register (String key, Object object){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.opsForValue().set(key, object);
    }
    /**
     *查询redis
     */
    public Object find (String key){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        Object o = redisTemplate.opsForValue().get(key);
        return o;
    }
    /**
     * 刷新
     */
    public void refresh (String key, Object object){
        RedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.opsForValue().set(key, object);
    }
}
