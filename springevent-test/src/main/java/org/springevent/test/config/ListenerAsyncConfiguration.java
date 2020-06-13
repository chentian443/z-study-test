package org.springevent.test.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 异步监听配置
 */
@Configuration
public class ListenerAsyncConfiguration implements AsyncConfigurer
{
    /**
     * spring 异步线程池默认配置：CorePoolSize=1，MaxPoolSize=无限，QueueCapacity=无限，存活时间=60s
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {
        //使用Spring内置线程池任务对象
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setThreadNamePrefix("MyExecutor-");
        //设置线程池参数
        taskExecutor.setCorePoolSize(5);
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        taskExecutor.setKeepAliveSeconds(60);
		taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
    
    /**
     * 测试 ApplicationContext的Environment，获取属性
     */
    
    
    
    
}