package com.parquimetro.infra.config.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class BooleanRedisSerializer implements RedisSerializer<Boolean> {

    @Override
    public byte[] serialize(Boolean value) throws SerializationException {
        return (value != null && value) ? new byte[]{(byte) 1} : new byte[]{(byte) 0};
    }

    @Override
    public Boolean deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return bytes[0] == 1;
    }
}
