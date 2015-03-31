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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;


public class ThreadPoolExecutor_Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		PausableThreadPoolExecutor threadPoolExecutorTest = new PausableThreadPoolExecutor();
		for(int taskIndex = 0; taskIndex < 10; taskIndex++){
			final int taskId = taskIndex;
			
			threadPoolExecutorTest.execute(new Runnable() {
				int counter = 0;
				public void run() {
					for (int i = 0; i < 10000; i++) {
						System.out.println(taskId + " Task : " + i);
						counter++;
					}
				}
			});
		}
		threadPoolExecutorTest.shutdown();
	}
	static class SimpleThreadFactory implements ThreadFactory {
		public Thread newThread(Runnable r) {
			return new Thread(r);
		}
	}

	static BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();
	static class PausableThreadPoolExecutor extends ThreadPoolExecutor {
		private boolean isPaused;
		private ReentrantLock pauseLock = new ReentrantLock();
		private Condition unpaused = pauseLock.newCondition();

		//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue)		
		//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory)		
		//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler)		
		//ThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler)

		//corePoolSize		the number of threads to keep in the pool, even if they are idle, unless allowCoreThreadTimeOut is set
		//maximumPoolSize	the maximum number of threads to allow in the pool
		//keepAliveTime		when the number of threads is greater than the core, this is the maximum time that excess idle threads will wait for new tasks before terminating.
		//unit				the time unit for the keepAliveTime argument
		//workQueue			the queue to use for holding tasks before they are executed. This queue will hold only the Runnable tasks submitted by the execute method.

		public PausableThreadPoolExecutor() {
			super(40, 40, 1, TimeUnit.SECONDS, queue, new SimpleThreadFactory());
		}

		protected void beforeExecute(Thread t, Runnable r) {
			super.beforeExecute(t, r);
			pauseLock.lock();
			try {
				while (isPaused) 
					unpaused.await();
			} catch (InterruptedException ie) {
				t.interrupt();
			} finally {
				pauseLock.unlock();
			}
		}

		public void pause() {
			pauseLock.lock();
			try {
				isPaused = true;
			} finally {
				pauseLock.unlock();
			}
		}

		public void resume() {
			pauseLock.lock();
			try {
				isPaused = false;
				unpaused.signalAll();
			} finally {
				pauseLock.unlock();
			}
		}		
	}
}
