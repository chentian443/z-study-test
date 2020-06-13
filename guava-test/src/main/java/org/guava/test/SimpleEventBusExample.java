package org.guava.test;

import com.google.common.eventbus.EventBus;
import org.guava.test.listeners.SimpleListener;

/**
 *总结：
1. subscriber对象是一个普通的类
2. subscribe方法必须被public 和 void 修饰
3. subscribe方法必须仅仅只有一个参数
4. subscribe方法必须添加 @Subscribe 注释。
5. 需要防止并发调用时，可在@Subscribe注解下再加上@AllowConcurrentEvents
 **/
public class SimpleEventBusExample {
    public static void main(String[] args) {
        final EventBus eventBus = new EventBus();
        //注册Listener
        eventBus.register(new SimpleListener());
        System.out.println("post the simple event.");
        //向订阅者发送消息
        eventBus.post("Simple Event");
        
        // 主线程会一直阻塞等待，直到订阅方法完成
        System.out.println("main over");
    }
}
