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
	 * �̳߳�
	 * 
	 */

	public static void main(String[] args) {

		/**
		 * @param corePoolSize  �����߳��� //
		 *  * @param maximumPoolSize �̳߳������ɵ�����߳���
		 * @param keepAliveTime �Ǻ����߳����ó�ʱʱ��
		 * @param unit          ָ����ʱʱ�䵥λ
		 * @param workQueue     �̳߳��еĶ�������
		 * @param threadFactory �̹߳���
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
