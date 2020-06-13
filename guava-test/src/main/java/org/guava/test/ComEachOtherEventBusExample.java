package org.guava.test;

import com.google.common.eventbus.EventBus;
import org.guava.test.events.Apple;
import org.guava.test.events.Fruit;
import org.guava.test.listeners.FruitEaterListener;
import org.guava.test.service.QueryService;
import org.guava.test.service.RequestQueryHandler;

/**
 * 
 **/
public class ComEachOtherEventBusExample {
	
    public static void main(String[] args) {
    	
        final EventBus eventBus = new EventBus();
        // QueryService 与 RequestQueryHandler监听同一个EventBus，可以实现这两个类之间的通信
        QueryService queryService = new QueryService(eventBus);
        RequestQueryHandler requestQueryHandler = new RequestQueryHandler(eventBus);
        queryService.query("123123");
    }
}
