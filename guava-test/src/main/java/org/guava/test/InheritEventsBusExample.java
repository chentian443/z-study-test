package org.guava.test;

import com.google.common.eventbus.EventBus;
import org.guava.test.events.Apple;
import org.guava.test.events.Fruit;
import org.guava.test.listeners.ConcreteListener;
import org.guava.test.listeners.FruitEaterListener;

/**
 * @Description:
 * @Author: Dong.Wang
 * @Date: 2018-05-19 14:39
 **/
public class InheritEventsBusExample {
    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        
        eventBus.register(new FruitEaterListener());
        eventBus.post(new Apple("apple"));

        System.out.println("---------------------");
        eventBus.post(new Fruit("Fruit"));
    }
}
