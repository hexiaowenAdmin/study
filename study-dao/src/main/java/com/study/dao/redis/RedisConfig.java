package com.study.dao.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import redis.clients.jedis.JedisPoolConfig;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * redis配置类
 * @author 何小文
 */
@Configuration
public class RedisConfig {

	/**注入配置属性对象*/
	@Autowired
	private RedisConfigProperties redisConfigProperties;

	@Bean(name = "redisConnectionFactory")
	@Primary
    public RedisConnectionFactory redisConnectionFactory() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisConfigProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisConfigProperties.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisConfigProperties.getMaxWaitMillis());
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(redisConfigProperties.getHost());
        jedisConnectionFactory.setPort(redisConfigProperties.getPort());
        jedisConnectionFactory.setPassword(redisConfigProperties.getPassword());
        jedisConnectionFactory.setDatabase(redisConfigProperties.getDatabase());
        jedisConnectionFactory.setTimeout(redisConfigProperties.getTimeout());
        jedisConnectionFactory.setUsePool(true);    
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }

	/**
	 * redis模板,存储关键字是字符串，值是Jdk序列化
	 */
	@Bean(name = "redisTemplate")
	public RedisTemplate<Object, Object> redisTemplate(@Qualifier(value = "redisConnectionFactory") RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		/** 使用Jackson2JsonRedisSerialize 替换默认序列化 */
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

		/** 设置value的序列化规则和 key的序列化规则 */
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
