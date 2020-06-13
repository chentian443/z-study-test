package org.quartz.test.config;

import java.io.IOException;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

//@Configuration
//@EnableConfigurationProperties(QuartzProperties.class) 
public class SchedulerConfig {
	
	@Autowired
    private QuartzProperties quartzProperties;

    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(quartzProperties.getQuartzProperties());
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        factory.setQuartzProperties(quartzProperties.getQuartzProperties());
        return factory;
    }

    /*@Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        Properties properties =  propertiesFactoryBean.getObject();
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(properties);
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        return properties;
    }*/
  
    /*
     * quartz初始化监听器
     */
    @Bean
    public QuartzInitializerListener executorListener() {
       return new QuartzInitializerListener();
    }
    
    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="Scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }
    

}

