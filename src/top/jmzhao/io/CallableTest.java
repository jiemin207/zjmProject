package top.jmzhao.io;

import java.util.concurrent.Callable;

public class CallableTest implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		int b = 0;
		b++;
		return b;
	}

}
