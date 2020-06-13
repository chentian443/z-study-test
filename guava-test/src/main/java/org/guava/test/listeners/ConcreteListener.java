package org.guava.test.listeners;

import com.google.common.eventbus.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 继承的订阅方法也会被触发，只要数据类型一致
 **/
public class ConcreteListener extends BaseListener {

    private final static Logger LOGGER = LoggerFactory.getLogger(ConcreteListener.class);

    @Subscribe
    public void conTask(final String event){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("Received event [{}] will be handle by {}.{}", new Object[]{event,this.getClass().getSimpleName(),"conTask"});
        }
    }
}
