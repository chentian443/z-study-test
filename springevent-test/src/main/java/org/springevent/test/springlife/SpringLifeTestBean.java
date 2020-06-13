package org.springevent.test.springlife;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


/**
 * @author chent
 * spring生命周期总结：spring实例化该bean并放入spring容器，总共要经历以下的步骤；
 * 方法从上到下依次执行，
 * 其中setBeanName，BeansetBeanFactory，setApplicationContext，postConstruct，afterPropertiesSet，initMethod
 * 只执行一次，而postProcessBeforeInitialization 与 postProcessAfterInitialization要交替执行很多次。
 * 最后 销毁方法依次为：preDestroy，destroy，destroyMethod
 */
//@Component
public class SpringLifeTestBean implements BeanNameAware,BeanFactoryAware,ApplicationContextAware,
									BeanPostProcessor,InitializingBean,DisposableBean{

	@Override
	public void setBeanName(String name) {
		System.out.println("setBeanName:"+name+"++++++++++++++++++++++++++++++++++++++");
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out.println("setBeanFactory:++++++++++++++++++++++++++++++++++++++");
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("setApplicationContext:++++++++++++++++++++++++++++++++++++++");
	}
	
	@PostConstruct
	public void postConstruct() {
	    System.out.println("postConstruct:++++++++++++++++++++++++++++++++++++++");
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("afterPropertiesSet:++++++++++++++++++++++++++++++++++++++");
	}
	
	public void initMethod(){ // 外部@bean指定initMethod才能执行该方法
		System.out.println("initMethod:++++++++++++++++++++++++++++++++++++++");
	}

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessBeforeInitialization:"+beanName+"++++++++++++++++++++++++++++++++++++++");
		return bean;
	}
	
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		System.out.println("postProcessAfterInitialization:"+beanName+"++++++++++++++++++++++++++++++++++++++");
		return bean;
	}
	
	@PreDestroy
	public void preDestroy() {
	    System.out.println("PreDestroy:++++++++++++++++++++++++++++++++++++++");
	}
	
	@Override
	public void destroy() throws Exception {
		System.out.println("destroy:++++++++++++++++++++++++++++++++++++++");
	}
	
	public void destroyMethod(){
		System.out.println("destroyMethod:++++++++++++++++++++++++++++++++++++++");
	}

}
