package org.guava.test.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 一个监听类中的多个订阅，会根据事件发布信息的类型来决定触发哪个订阅消息。
 * 相同类型的订阅方法执行 没有 严格的顺序。
 * 
 **/
public class MultipleEventListeners {
    private final static Logger LOGGER = LoggerFactory.getLogger(MultipleEventListeners.class);

    @Subscribe
    public void task3(final String event){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("Received event [{}] and will take a action by ==task3==", event);
        }
    }
    
    @Subscribe
    public void task1(final String event){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("Received event [{}] and will take a action by ==task1==", event);
        }
    }

    @Subscribe
    public void task2(final String event){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("Received event [{}] and will take a action by ==task2==", event);
        }
    }

    /**
     *
     * @param event 此处数据类型必须为Integer，不能是int
     */
    @Subscribe
    public void intTask(final Integer event){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("Received event [{}] and will take a action by ==intTask==", event);
        }
    }
}
