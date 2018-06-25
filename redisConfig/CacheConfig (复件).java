package com.fengchao.config;

import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@EnableCaching
public class CacheConfig {
    
    @Bean
    @Primary //标记该cacheManager是系统默认的cacheManager
    public RedisCacheManager dataCacheManager(@Qualifier(value="dataConnectionFactory")RedisConnectionFactory connectionFactory) {
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())
                .withInitialCacheConfigurations(Collections.singletonMap("article:", RedisCacheConfiguration
                        .defaultCacheConfig().prefixKeysWith("article:").entryTtl(Duration.ofDays(2))))
                .transactionAware().build();
        return redisCacheManager;
    }
    /**
     * 用于缓存普通数据的redis connection factory
     * @return
     */
	@Bean(name="dataConnectionFactory")
	public RedisConnectionFactory dataLettuceConnectionFactory() {
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 3333);
		return new LettuceConnectionFactory(redisStandaloneConfiguration);
	}
	@Bean//创建该bean，是为了指定其connectionFactory
    public StringRedisTemplate stringRedisTemplate(
            @Qualifier(value="dataConnectionFactory")RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
	
	@Bean//创建该bean，是为了指定其connectionFactory
    public RedisTemplate<Object, Object> redisTemplate(
            @Qualifier(value="dataConnectionFactory")RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
	
}
