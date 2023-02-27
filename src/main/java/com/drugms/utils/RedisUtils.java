package com.drugms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {


    static
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    public static Object getValue(String token){
        return redisTemplate.opsForValue().get(token);
    }

    /**
     * @param timeout 过期时间，单位为秒
     */
    public static void addWithExpired(String key,Object object,Long timeout){
        redisTemplate.opsForValue().setIfAbsent(key,object,timeout, TimeUnit.SECONDS);
    }

    public static void setWithExpired(String key,Object object,Long timeout){
        redisTemplate.opsForValue().set(key,object,timeout, TimeUnit.SECONDS);
    }
}
