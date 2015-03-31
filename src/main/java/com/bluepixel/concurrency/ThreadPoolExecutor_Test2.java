/*
 * Copyright (c)
 *
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Win Tun Lin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.bluepixel.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;




public class ThreadPoolExecutor_Test2 {


	int poolSize = 2;	 
	int maxPoolSize = 3; 
	long keepAliveTime = 10; 
	ThreadPoolExecutor threadPool = null;

	final ArrayBlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(30);

	public ThreadPoolExecutor_Test2() {
		threadPool = new ThreadPoolExecutor(poolSize, maxPoolSize, keepAliveTime, TimeUnit.SECONDS, queue);

	}

	public void runTask(Runnable task)  {
		// System.out.println("Task count.."+threadPool.getTaskCount() );
		// System.out.println("Queue Size before assigning the
		// task.."+queue.size() );
		threadPool.execute(task);
		
		// System.out.println("Queue Size after assigning the
		// task.."+queue.size() );
		// System.out.println("Pool Size after assigning the
		// task.."+threadPool.getActiveCount() );
		// System.out.println("Task count.."+threadPool.getTaskCount() );
		System.out.println("Task count.." + queue.size());

	}

	public void shutDown() {
		threadPool.shutdown();
	}

	public static void main(String args[]) {

		ThreadPoolExecutor_Test2 threadPoolExecutorTest = new ThreadPoolExecutor_Test2();
		for(int taskIndex = 0; taskIndex < 30; taskIndex++){
			final int taskId = taskIndex;
			threadPoolExecutorTest.runTask(new Runnable() {
				public void run() {
					for (int i = 0; i < 10; i++) {
						try {
							System.out.println(taskId + " Task : " + i);
							Thread.sleep(1000);
						} catch (InterruptedException ie) {
						}
					}
				}
			});
		}
		threadPoolExecutorTest.shutDown();
	}
}
