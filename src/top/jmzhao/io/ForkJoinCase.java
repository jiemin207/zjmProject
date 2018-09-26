package top.jmzhao.io;

	import java.util.concurrent.ForkJoinPool;
	import java.util.concurrent.ForkJoinTask;
	import java.util.concurrent.RecursiveTask;
	
	public class ForkJoinCase extends RecursiveTask<Double> {
	
		private static final long serialVersionUID = 1L;
	
		public ForkJoinCase() {		
		}
			 
		 // 进行forkJoin的界限，数量少于100直接进行计算
		 static final int THRESHOLD = 100;
		 double[] array;
		    int start;
		    int end;
	
		    ForkJoinCase(double[] array, int start, int end) {
		    this.array = array;
		        this.start = start;
		        this.end = end;
		    }
		
		@Override
		protected Double compute() {
			if (end - start <= THRESHOLD) {
	            // 如果任务足够小,直接计算:
				double sum = 0;
	            for (int i = start; i < end; i++) {
	                sum += array[i];
	            }
	            return sum;
	        }
	        // 任务太大,一分为二:
	        int middle = (end + start) / 2;
	        ForkJoinCase subtask1 = new ForkJoinCase(this.array, start, middle);
	        ForkJoinCase subtask2 = new ForkJoinCase(this.array, middle, end);
	        invokeAll(subtask1, subtask2);
	        Double subresult1 = subtask1.join();
	        Double subresult2 = subtask2.join();
	        Double result = subresult1 + subresult2;
	        return result;
		}
		
		public static void main(String[] args) {
			
			double[] array = new double[400];
			for(int i=0; i<array.length; i++) {
				array[i] = Math.random() * 100;
			}
			
		    // fork/join task:
		    ForkJoinPool fjp = new ForkJoinPool(4); // 最大并发数4
		    ForkJoinTask<Double> task = new ForkJoinCase(array, 0, array.length);
		    long startTime = System.currentTimeMillis();
		    Double result = fjp.invoke(task);
		    long endTime = System.currentTimeMillis();
		    System.out.println("Fork/join sum: " + result + " in " + (endTime - startTime) + " ms.");	
		}
	}
