package com.wms.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

//@Configuration
//@EnableRedisRepositories
//public class RedisConfig {
//	@Bean
//	public RedisConnectionFactory connectionFactory() {
//		RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//				.master("masterName")
//				.sentinel("127.0.0.1", 26379)
//				.sentinel("127.0.0.1", 26380);
//		return new JedisConnectionFactory(sentinelConfig);
//	}
//
//	@Bean
//	public RedisTemplate<?, ?> redisTemplate() {
//		RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
//		template.setConnectionFactory(connectionFactory());
//		template.setKeySerializer(new StringRedisSerializer());
//		template.setValueSerializer(new GenericJackson2JsonRedisSerializer(new ObjectMapper()));
//		template.setHashKeySerializer(new StringRedisSerializer());
//		template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(new ObjectMapper()));
//		return template;
//	}
//
//}
