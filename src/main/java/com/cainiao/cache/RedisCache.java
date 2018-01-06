package com.cainiao.cache;

import com.cainiao.ProtoStuffSerializerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

//import javax.swing.*;
import java.util.List;
import java.util.Set;

@Component
public class RedisCache {
    public static final String GOODSCACHE = "cache";
    public static final int EXPIRETIME = 60;
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    public <T> boolean putCache(String key, T obj) {
        final byte[] bKey = key.getBytes();
        final byte[] bValue = ProtoStuffSerializerUtil.serializer(obj);
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) (connection) -> {
            return connection.setNX(bKey, bValue);
        });//函数接口是个泛型接口
        return result;
    }

    public <T> boolean putCacheWithExpireTime(String key, T obj, final long time) {
        final byte[] bKey = key.getBytes();
        final byte[] bValue = ProtoStuffSerializerUtil.serializer(obj);
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) (connection) -> {
            connection.setEx(bKey, time, bValue);
            return true;
        });
        return result;
    }

    public <T> boolean putListCache(String key, List<T> objList) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = ProtoStuffSerializerUtil.listSerializer(objList);
        boolean result = redisTemplate.execute((RedisCallback<Boolean>) (connection) -> {
            return connection.setNX(bkey, bvalue);
        });
        return result;
    }
    public <T> boolean putListCacheWithExpireTime(String key, List<T> objList, final long expireTime) {
        final byte[] bkey = key.getBytes();
        final byte[] bvalue = ProtoStuffSerializerUtil.listSerializer(objList);
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                connection.setEx(bkey, expireTime, bvalue);
                return true;
            }
        });
        return result;
    }

    public <T> T getCache(final String key, Class<T> targetClass) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return ProtoStuffSerializerUtil.deserializer(result, targetClass);
    }

    public <T> List<T> getListCache(final String key, Class<T> targetClass) {
        byte[] result = redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.get(key.getBytes());
            }
        });
        if (result == null) {
            return null;
        }
        return ProtoStuffSerializerUtil.deserializeList(result, targetClass);
    }

    /**
     * 精确删除key
     *
     * @param key
     */
    public void deleteCache(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 模糊删除key
     *
     * @param pattern
     */
    public void deleteCacheWithPattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

    /**
     * 清空所有缓存
     */
    public void clearCache() {
        deleteCacheWithPattern(RedisCache.GOODSCACHE + "|*");
    }
}
