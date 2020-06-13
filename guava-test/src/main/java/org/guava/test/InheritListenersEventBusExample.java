package org.guava.test;

import com.google.common.eventbus.EventBus;
import org.guava.test.listeners.ConcreteListener;

/**
 * 
 **/
public class InheritListenersEventBusExample {
    public static void main(String[] args) {
    	
        final EventBus eventBus = new EventBus();
        eventBus.register(new ConcreteListener());
        System.out.println("post the string event.");
        eventBus.post("I am String event");
        System.out.println("post the int event.");
        eventBus.post(1000);
    }
}
