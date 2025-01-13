package com.example.integratedHub.config;/*
 *@title RedisConfig
 *@description
 *@author echoes
 *@version 1.0
 *@create 2024/5/14 10:14
 */


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {
    //自定义序列化，反序列化对象

    public RedisSerializer jsonSerializer(){
        Jackson2JsonRedisSerializer serializer =new Jackson2JsonRedisSerializer(Object.class);
        //基于此对象进行序列化的方式
        ObjectMapper objectMapper = new ObjectMapper();
//表示get方法使用此序列化 //表示全部权限的方法可以被序列化，默认public
        objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);

        //修改序列化存储类型，对想序列化会将对象的类型存储到redis数据库
        //假如没有这个配置，redis储存时不储存类型，反序列化默认存储到map
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(),
                ObjectMapper.DefaultTyping.NON_FINAL,//激活序列化类型储存
                JsonTypeInfo.As.PROPERTY);//表示类型会以json对象属性形 式存储

        //对象属性为空时，不进行序列化储存
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        serializer.setObjectMapper(objectMapper);
        return  serializer;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        //字符串类型的数据的键为RedisSerializer序列化方式
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());
        //字符串类型的数据的值的序列化方式为自定义
        template.setValueSerializer(jsonSerializer());
        template.setHashValueSerializer(jsonSerializer());
        //spring规范中假如修改bean对象的默认特性,建议调用一下
        template.afterPropertiesSet();
        return template;
    }
}