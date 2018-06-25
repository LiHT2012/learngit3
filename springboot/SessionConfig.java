package com.backend.kfc.cfg;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.session.data.redis.config.annotation.SpringSessionRedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableCaching
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 24*60*60*30)
public class SessionConfig {



    @Bean
    @SpringSessionRedisConnectionFactory
    @Primary
    @ConfigurationProperties(prefix="spring.redis")
     public RedisConnectionFactory lettuceConnectionFactory() {
         RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
         return new LettuceConnectionFactory(redisStandaloneConfiguration);
     }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return HeaderHttpSessionIdResolver.xAuthToken();//指定用x-auth-token，若无，则无法生效
    }

}
