package org.nettystudy.first.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureTaskTest {

	private static final int THREAD_POOL_NUM = 1;
	private static final ExecutorService CONFIG_EXECUTOR = new ThreadPoolExecutor(THREAD_POOL_NUM, THREAD_POOL_NUM,
	        Integer.MAX_VALUE, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(),
	        new NamedThreadFactory("ZKConfigThread", THREAD_POOL_NUM));
	
	/**
	 * 当FutureTask处于未启动或者已启动的状态时，调用FutureTask对象的get方法会将导致调用线程阻塞。
	 * 当FutureTask处于已完成的状态时，调用FutureTask的get方法会立即放回调用结果或者抛出异常。
	 * 当FutureTask处于未启动状态时，调用FutureTask对象的cancel方法将导致线程永远不会被执行；
	 * 当FutureTask处于已启动状态时，调用FutureTask对象cancel(true)方法将以中断执行此任务的线程的方式来试图停止此任务;
	 * 当FutureTask处于已启动状态时，调用FutureTask对象cancel(false)方法将不会对正在进行的任务产生任何影响；
	 * 当FutureTask处于已完成状态时，调用FutureTask对象cancel方法将返回false；
	 */
	private static void test(){
		
		FutureTask<String> future = new FutureTask<>(() -> {
			printCurrentThreadNameAndStr("future callback start");
			Thread.sleep(1000*3);
			printCurrentThreadNameAndStr("future callback end");
            return "abc";
        });
		printCurrentThreadNameAndStr("executor start");
		// 启动futureTask
        CONFIG_EXECUTOR.execute(future);
        String result = null;
		try {
			printCurrentThreadNameAndStr("try to get future result");
			/**
			 * get方法将阻塞当先线程，直到结果返回或超时
			 */
			result = future.get(1000*4, TimeUnit.MILLISECONDS);
			
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}
        printCurrentThreadNameAndStr(result);
	}
	
	private static void printCurrentThreadNameAndStr(String str){
		System.out.println(Thread.currentThread().getName() + "-->" + str);
	}
	
	public static void main(String[] args) {
		test();
		/**
		 * 这一步可以不执行，因为线程池的线程为守护线程，当main线程退出后，jvm检测到vm中只有守护线程，就会退出 ，守护线程也会随着 JVM一起退出
		 * 注意：守护线程中产生的新线程也是守护线程；gc线程为守护线程；非守护线程即为用户线程
		 */
		//CONFIG_EXECUTOR.shutdown(); // 关闭线程池
	}
}
