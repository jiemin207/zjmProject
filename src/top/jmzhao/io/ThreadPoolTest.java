package top.jmzhao.io;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

	/**
	 * 
	 * 线程池
	 * 
	 */

	public static void main(String[] args) {

		/**
		 * @param corePoolSize  核心线程数 //
		 *  * @param maximumPoolSize 线程池能容纳的最大线程数
		 * @param keepAliveTime 非核心线程闲置超时时间
		 * @param unit          指定超时时间单位
		 * @param workQueue     线程池中的队列任务
		 * @param threadFactory 线程工厂
		 * 
		 */
		ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 5, 30, TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>());
	
		poolExecutor.execute(new Runnable() {	
			@Override
			public void run() {
				System.out.println("I'm running" );
			}
		});
		
		Future<Integer> submit = poolExecutor.submit( new CallableTest());
		
	}
	


	
}
