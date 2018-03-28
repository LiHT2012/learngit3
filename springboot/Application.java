package com.dbc.qrservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * SpringBoot工程生成war包，部署到外部应用服务器部署的启动入口
 *
 */
@Configuration
@ComponentScan(basePackages = "com.dbc.qrservice")
@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

	private static Class<Application> applicationClass = Application.class;
	
    public static void main(String[] args) {
       SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }
}
