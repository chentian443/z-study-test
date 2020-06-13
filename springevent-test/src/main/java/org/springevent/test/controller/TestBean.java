package org.springevent.test.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class TestBean implements ApplicationContextAware{

	/**
	 * applicationContext.getEnvironment()获取到spring容器的属性管理器
	 */
	
	static{
		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
		// Oracle Corporation    vm  args
		System.out.println(System.getProperty("java.vendor"));
	}
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println(applicationContext.getEnvironment().getProperty("server.port"));
	}

}
