package org.guava.test.listeners;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 * DeadEvent能够订阅到所有事件，但是如果有其他的方法已经订阅到，则DeadEvent不起作用
 **/
public class DeadEventListener {
	
    @Subscribe
    public void handle(DeadEvent event){
        //获取事件源
        System.out.println(event.getSource());//DEAD-EVENT-BUS
        //获取事件
        System.out.println(event.getEvent());//DeadEventListener event
    }
    
    // 
    /*@Subscribe
    public void test(final String event){
    	System.out.println("event is :" + event);
    }*/
}
