package org.nettystudy.first.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CallableAndFuture2 {

	private volatile static boolean flag = false;
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newSingleThreadExecutor();//单例线程
        FutureTask<Integer> future = new FutureTask<Integer>(() -> {
        		System.out.println("future start");
            	Thread.sleep(5000);
            	flag = true;
                return new Random().nextInt(100);
        });
        threadPool.execute(future);
        try {
        	int i = 1;
        	while(!flag){
        		i ++;
        		Thread.sleep(10);
        	}
        	System.out.println(i);
            System.out.println(future.get());
            System.out.println("over");
        } catch (Exception e) {
            e.printStackTrace();
        } 
        // 不关闭的话，该线程池会一直存在
        threadPool.shutdown();
    }
}
