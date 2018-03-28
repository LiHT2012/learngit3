package com.dbc.qrservice;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class DbcApplicationContextUtil implements ApplicationContextAware{

	private static ApplicationContext applicationContext;

	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}

	public void setApplicationContext(ApplicationContext contex) throws BeansException {
		DbcApplicationContextUtil.applicationContext = contex;
	}

	public static ApplicationContext getContext() {
		return applicationContext;
	}
}
/**
使用
public static SystemMessageManager getSysMessageManagerBean(Integer type) {
        return (SystemMessageManager) DbcApplicationContextUtil.getBean(MESSAGE_TYPE_BEAN_NAME_MAP.get(type));
    }
**/
