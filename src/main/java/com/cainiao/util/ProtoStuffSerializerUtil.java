package com.cainiao.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ProtoStuffSerializerUtil {
    static {
        //https://github.com/protostuff/protostuff/issues/132
        //禁止反序列化时构造方法被调用
        System.getProperties().setProperty("protostuff.runtime.always_use_sun_reflection_factory","true");
    }
    public static <T> byte[] serializer(T object) {
        if (object == null) {
            throw new RuntimeException("Serialize object(" + object + ")failed!");//object == null, will it work well?
        }
        //获取schema
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(object.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        byte[] protostuff = null;
        try {
            protostuff = ProtostuffIOUtil.toByteArray(object, schema, buffer);
        } catch (Exception e) {
            throw new RuntimeException("serialize object(" + object + ")failed!");
        } finally {
            buffer.clear();
        }
        return protostuff;
    }


    public static <T> T deserializer(byte[] arrayOfByte, Class<T> targetClass) {
        if (arrayOfByte == null || arrayOfByte.length == 0 || targetClass == null) {//需要判断targetClass吗？
            throw new RuntimeException("Deserialize failed!");
        }
        T instance = null;
        try {
            instance = targetClass.newInstance();//ruguo
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Deserialize failed!", e);
        }
        Schema schema = RuntimeSchema.getSchema(targetClass);
        ProtostuffIOUtil.mergeFrom(arrayOfByte, instance, schema);
        return instance;
    }

    //序列化列表
    public static <T> byte[] listSerializer(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new RuntimeException("List serialize failed!");
        }
        Schema<T> schema = (Schema<T>) RuntimeSchema.getSchema(list.get(0).getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] protostuff = null;
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            ProtostuffIOUtil.writeListTo(bos, list, schema, buffer);
            protostuff = bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Serialize list object failed!");
        } finally {
            buffer.clear();
            try {
                if (bos != null) {
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return protostuff;
    }

    public static <T> List<T> deserializeList(byte[] paramArrayOfByte, Class<T> targetClass) {
        if (paramArrayOfByte == null || paramArrayOfByte.length == 0) {
            throw new RuntimeException("Deserialize failed, parameter is null!");
        }

        Schema<T> schema = RuntimeSchema.getSchema(targetClass);
        List<T> result = null;
        try {
            result = ProtostuffIOUtil.parseListFrom(new ByteArrayInputStream(paramArrayOfByte), schema);
        } catch (IOException e) {
            throw new RuntimeException("Deserialize failed!", e);
        }
        return result;
    }
}