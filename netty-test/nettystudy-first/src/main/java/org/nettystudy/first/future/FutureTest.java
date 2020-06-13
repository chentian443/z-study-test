package org.nettystudy.first.future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTest {
	private volatile static boolean flag = false; // 内存实时可见
    public static void main(String[] args) {
        Callable<Integer> callable = new Callable<Integer>() {
        	@Override
            public Integer call() throws Exception {
        		System.out.println("future start");
            	Thread.sleep(5000);
            	flag = true;
                return new Random().nextInt(100);
            }
        };
        FutureTask<Integer> future = new FutureTask<Integer>(callable);
        new Thread(future).start(); // 会开启新线程，并执行callable的run()
        try {
        	int i = 1;
        	while(!flag){
        		i ++;
        		Thread.sleep(10);
        	}
        	System.out.println(i);
            System.out.println(future.get());
            System.out.println("over");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
