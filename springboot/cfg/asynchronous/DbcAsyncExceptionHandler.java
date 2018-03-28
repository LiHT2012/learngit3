package com.dbcool.api.cfg.asynchronous;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

public class DbcAsyncExceptionHandler implements AsyncUncaughtExceptionHandler {

	private static Logger logger = LogManager.getLogger(DbcAsyncExceptionHandler.class);
	
	@Override
	public void handleUncaughtException(Throwable ex, Method method, Object... params) {
		
		/**
		 * 编辑错误信息
		 */
		StringBuffer mesBuffer = new StringBuffer();
		mesBuffer.append("Method name : ").append(method.getName()).append("\n").
						append("Exceptin message : ").append(ex.getMessage()).append("\n").append(ex.getStackTrace());
		logger.error(mesBuffer);
		
		return;
	}
}
