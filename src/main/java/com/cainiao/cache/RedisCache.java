package com.cainiao.cache;

import com.cainiao.ProtoStuffSerializerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class RedisCache {
    public static final String GOODSCACHE = "cache";
    public static final int EXPIRETIME = 60;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public <T> boolean putCache(String key, T obj) {
        final byte[] bKey = key.getBytes();
        final byte[] bValue = ProtoStuffSerializerUtil.serializer(obj);
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) (connection)->{
            return connection.setNX(bKey,bValue);});//函数接口是个泛型接口
        return result;
    }
}
