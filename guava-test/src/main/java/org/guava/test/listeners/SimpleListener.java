package org.guava.test.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 注册到eventBus后，即可监听eventBus的post行为；
 * 非常类似于spring的event-listener功能
 */
public class SimpleListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleListener.class);

    /**
     * 一个简单的Listener方法
     * @param event Guava规定此处只能有一个参数
     */
    @Subscribe
    public void doAction(final String event){
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("Received event [{}] and will take a action", event);
        }
    }
    
}
