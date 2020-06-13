package org.guava.test;

import java.util.concurrent.Executor;

import org.guava.test.listeners.SimpleListener;

import com.google.common.eventbus.AsyncEventBus;

public class SimpleAsyncEventBusExample {
	public static void main(String[] args) {
		
		// 构建一个 Executor 用于异步执行
		Executor executor = new Executor() {
		    @Override
		    public void execute(Runnable command) {
		        new Thread(command).start();
		    }
		};
		// 实例化AsyncEventBus对象
		AsyncEventBus asyncEventBus = new AsyncEventBus("Async", executor);
		// 其他的使用和同步一样
		asyncEventBus.register(new SimpleListener());
		
		asyncEventBus.post("Async Simple Event");
		
		// 主线程会一直阻塞等待，直到订阅方法完成
        System.out.println("main over");
        
	}
}
