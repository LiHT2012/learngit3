package com.dbcool.api.cfg.asynchronous;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class DbcAsyncConfig implements AsyncConfigurer {

	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor poolTaskExecutor =  new ThreadPoolTaskExecutor();
		//线程池所使用的缓冲队列  
		poolTaskExecutor.setQueueCapacity(200);  
		//线程池维护线程的最少数量  
		poolTaskExecutor.setCorePoolSize(10);  
		//线程池维护线程的最大数量  
		poolTaskExecutor.setMaxPoolSize(1000);  
		poolTaskExecutor.initialize();  
		return poolTaskExecutor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new DbcAsyncExceptionHandler();
	}

}
