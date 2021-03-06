package com.demo.config.redis;

import com.demo.bean.redis.ByteRedisTemplate;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.RedisClient;
import io.lettuce.core.masterreplica.StatefulRedisMasterReplicaConnection;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableCaching
public class RedisConfig {


    /**
     *  ??????redis?????????????????????????????????
     */
//    @Bean
//    public LettuceClientConfigurationBuilderCustomizer lettuceClientConfigurationBuilderCustomizer(){
//
//        return clientConfigurationBuilder ->{
//            clientConfigurationBuilder.readFrom(ReadFrom.REPLICA);
//        };
//
//    }

    /**
     *  ??????redis??????????????????RedisTemplate
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisSerializer<String> stringSerializer = new StringRedisSerializer();

        Jackson2JsonRedisSerializer jacksonSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        // ???????????????????????????field,get???set,????????????????????????ANY???????????????private???public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // ????????????????????????????????????????????????final????????????final?????????????????????String,Integer??????????????????
//        om.activateDefaultTyping(null,ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSerializer.setObjectMapper(om);

        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jacksonSerializer);

        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jacksonSerializer);
        template.setConnectionFactory(redisConnectionFactory);
        template.afterPropertiesSet();

        return template;
    }

    /**
     * ????????????????????????value
     */
    @Bean
    public ByteRedisTemplate byteRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new ByteRedisTemplate(redisConnectionFactory);
    }


    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        RedisCacheManager cacheManager = new RedisCacheManager(redisCacheWriter, redisCacheConfiguration);
        return cacheManager;
    }

}
