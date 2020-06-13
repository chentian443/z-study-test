package org.guava.test.listeners;

import com.google.common.eventbus.Subscribe;
import org.guava.test.events.Apple;
import org.guava.test.events.Fruit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发布事件的数据类型为子类，则监听子类和父类的方法都会被触发； apple --> apple + fruit
 * 发布事件的数据类型为父类，则只有监听父类的方法会被触发； fruit --> fruit
 **/
public class FruitEaterListener {
    private final static Logger LOGGER = LoggerFactory.getLogger(FruitEaterListener.class);

    @Subscribe
    public void eat(Fruit event){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("Fruit eat[{}]. ", event);
        }
    }
    @Subscribe
    public void eat(Apple event){
        if (LOGGER.isInfoEnabled()){
            LOGGER.info("Apple eat[{}]. ", event);
        }
    }
}
