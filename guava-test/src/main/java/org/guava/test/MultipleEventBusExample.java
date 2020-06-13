package org.guava.test;

import com.google.common.eventbus.EventBus;
import org.guava.test.listeners.MultipleEventListeners;
import org.guava.test.listeners.SimpleListener;

/**
 * 
 **/
public class MultipleEventBusExample {
    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        eventBus.register(new MultipleEventListeners());
        System.out.println("post the string event.");
        eventBus.post("I am String event");
        System.out.println("post the int event.");
        eventBus.post(1000);
    }
}
