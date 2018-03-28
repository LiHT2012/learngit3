package com.dbcool.api.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.session.web.http.HttpSessionStrategy;

/**
 * 
 * Session过期时间默认设置为改为30天
 *
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 24*60*60*30)
@PropertySource(value="classpath:application.properties")
public class SessionConfig {
	
	/**
	 *  redis连接的端口和主机名称在配置文件读取
	 *  目前是生产环境，在修改为部署环境的时候要改为amazon的服务主机名和IP
	 * @return  RedisConnectionFactory实例
	 */
	@Autowired
	Environment env;
	
	@Bean
    public RedisConnectionFactory connectionFactory() {
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setPort(env.getProperty("spring.redis.port", int.class));
        connectionFactory.setHostName(env.getProperty("spring.redis.host-name"));
        
        return connectionFactory;
    }
	
	@Bean
    public HttpSessionStrategy httpSessionStrategy() {
            return new HeaderHttpSessionStrategy(); 
    }
}
