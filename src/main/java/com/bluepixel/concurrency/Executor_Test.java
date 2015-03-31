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

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;




public class Executor_Test {

	private static final int NTHREDS = 10;

	public static void main(String[] args) {
//		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);
//		for (int i = 0; i < 500; i++) {
//			Runnable worker = new MyRunnable(10000000L + i);
//			executor.execute(worker);
//		}
//
//		// This will make the executor accept no new threads
//		// and finish all existing threads in the queue
//		executor.shutdown();
//
//		// Wait until all threads are finish
//		while (!executor.isTerminated()) {
//
//		}
//		System.out.println("Finished all threads");
		
		Executor directExecutor = new ThreadPerTaskExecutor();
		directExecutor.execute(new MyRunnable(1));
		directExecutor.execute(new MyRunnable(2));	
	}

	//an executor can run the submitted task immediately in the caller's thread:
	static class DirectExecutor implements Executor {
		public void execute(Runnable r) {
			r.run();
		}
	}

	//The executor below spawns a new thread for each task.
	static class ThreadPerTaskExecutor implements Executor {
		public void execute(Runnable r) {
			new Thread(r).start();
		}
	}

	//The executor below serializes the submission of tasks to a second executor, illustrating a composite executor.
	static class SerialExecutor implements Executor {
		final Queue<Runnable> tasks = new ArrayDeque<Runnable>();
		final Executor executor;
		Runnable active;

		SerialExecutor(Executor executor) {
			this.executor = executor;
		}

		public synchronized void execute(final Runnable r) {
			tasks.offer(new Runnable() {
				public void run() {
					try {
						r.run();
					} finally {
						scheduleNext();
					}
				}
			});
			if (active == null) {
				scheduleNext();
			}
		}
		protected synchronized void scheduleNext() {
			if ((active = tasks.poll()) != null) {
				executor.execute(active);
			}
		}
	}

	static class MyRunnable implements Runnable {
		private final long countUntil;

		MyRunnable(long countUntil) {
			this.countUntil = countUntil;
		}

		@Override
		public void run() {
			long sum = 0;
			for (long i = 1; i < countUntil; i++) {
				sum += i;
			}
			System.out.println(sum);
		}
	} 
}


