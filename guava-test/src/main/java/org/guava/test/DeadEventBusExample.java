package org.guava.test;

import com.google.common.eventbus.EventBus;
import org.guava.test.listeners.DeadEventListener;
import org.guava.test.listeners.ExceptionListener;

/**
 * 
 **/
public class DeadEventBusExample {
    public static void main(String[] args) {
        //重写EventBus的toString方法，使eventBus的名称为DEAD-EVENT-BUS
        final EventBus eventBus = new EventBus(){
            @Override public String toString() {
                return "DEAD-EVENT-BUS";
            }
        };
        DeadEventListener deadEventListener = new DeadEventListener();
        eventBus.register(deadEventListener);
        eventBus.post("DeadEventListener event");

        //取消注册在eventBus上的Listener方法，注销后，订阅将不起作用
        eventBus.unregister(deadEventListener);
        eventBus.post("DeadEventListener event after unregister");
    }
}
