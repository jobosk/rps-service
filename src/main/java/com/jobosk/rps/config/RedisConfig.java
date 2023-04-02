package com.jobosk.rps.config;

import com.jobosk.rps.model.MoveCodeEnum;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;

@Data
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Bean
    public RedisTemplate<String, MoveCodeEnum> redisTemplateUserPlays(RedisConnectionFactory factory) {
        RedisIntegerSerializer redisIntSerializer = new RedisIntegerSerializer();

        RedisTemplate<String, MoveCodeEnum> template = new RedisTemplate<>();

        template.setConnectionFactory(factory);
        template.setKeySerializer(RedisSerializer.string());
        template.setValueSerializer(redisIntSerializer);
        template.setHashKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(redisIntSerializer);

        return template;
    }

    private static class RedisIntegerSerializer implements RedisSerializer<MoveCodeEnum> {

        @Override
        public byte[] serialize(MoveCodeEnum i) throws SerializationException {
            return String.valueOf(i).getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public MoveCodeEnum deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null) {
                return null;
            }
            return MoveCodeEnum.valueOf(new String(bytes, StandardCharsets.UTF_8));
        }

        @Override
        public Class<?> getTargetType() {
            return Integer.class;
        }
    }

}
