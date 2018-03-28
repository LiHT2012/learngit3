package com.dbcool.api.cfg;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 系统缓存配置使用redis，从配置文件读取redis服务的各种参数
 * @author gaoyong
 *
 */

@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport{
	//springboot启动后负责创建一个单例的RedisConnectionFactory
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	//缓存自动失效时间
	public static final int DEFAULT_EXPIRE_TIME_SECONDS = 60*30;

    @Bean
    public KeyGenerator wiselyKeyGenerator(){
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };

    }
    
    @Primary
    @Bean(name = "cacheManager")
    public CacheManager compositeCacheManager() {
    	
//    	System.out.println("---------create compositeCacheManager------" );
    	CompositeCacheManager cacheManager = new CompositeCacheManager();
    	//设置true确定在没有具体 第三方cache方案的时候依然可以跑的通
    	cacheManager.setFallbackToNoOpCache(true);
    	List<CacheManager> managerList = new ArrayList<>();
    	managerList.add(redisCacheManager(redisTemplate(redisConnectionFactory)));
    	cacheManager.setCacheManagers(managerList);
    	
        return cacheManager;
    }

    @Bean(name = "rediscacheManager")
//    @Bean
    public RedisCacheManager redisCacheManager(
            @SuppressWarnings("rawtypes") RedisTemplate redisTemplate) {
    	
//    	System.out.println("---------create CacheManager------" );
//    	System.out.println("" + redisTemplate.getConnectionFactory().getConnection().getClientName());
    	RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
    	cacheManager.setDefaultExpiration(DEFAULT_EXPIRE_TIME_SECONDS); // 设置缓存30分钟有效
    	
    	//下面必须设置true
    	cacheManager.setUsePrefix(true);
    	
//    	cacheManager.setCacheNames(Arrays.asList(new String[]{"dbccache","testcache"}));
    	
        return cacheManager;
    }

    //factory是spring启动的时候所创建
    @Bean
    public RedisTemplate<String, String> redisTemplate(
            RedisConnectionFactory factory) {
//    	System.out.println(" ======create RedisTemplate=======" );
        StringRedisTemplate template = new StringRedisTemplate(factory);
        
        @SuppressWarnings({ "rawtypes", "unchecked" })
		Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
}

