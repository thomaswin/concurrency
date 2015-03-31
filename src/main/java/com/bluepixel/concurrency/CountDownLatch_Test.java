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

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class CountDownLatch_Test {

	//A synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.
	//one-shot phenomenon -- the count cannot be reset. If you need a version that resets the count, consider using a CyclicBarrier.
	// can control when the thread start
	public static void main(String[] args) throws InterruptedException {
		Driver driver = new Driver();
		driver.main();
	}
	static class Driver {
		int N = 10;
		Driver(){

		}
		void main() throws InterruptedException {
			CountDownLatch startSignal = new CountDownLatch(1);
			CountDownLatch doneSignal = new CountDownLatch(N);

			for (int i = 0; i < N; ++i){

				Thread thread = new Thread(new Worker(startSignal, doneSignal));
				thread.setName("thread " + i);
				thread.start();
			}

			doSomethingElse();            // don't let run yet
			startSignal.countDown();      // let all threads proceed
			doSomethingElse();
			System.out.println("WAITING ..............");
			doneSignal.await();           // wait for all to finish
			System.out.println("ALL COMPLETED");
		}
		private void doSomethingElse() {
			System.out.println("do something");
			// TODO Auto-generated method stub
		}
	}

	static class Worker implements Runnable {
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;
		Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}
		public void run() {
			try {
				startSignal.await();
				doWork();
				doneSignal.countDown();
			} catch (InterruptedException ex) {} 
		}

		void doWork() {  
			System.out.println(Thread.currentThread().getName() + " do work");
		}
	}

	static class Driver2{
		int N = 5;
		void main() throws InterruptedException {
			CountDownLatch doneSignal = new CountDownLatch(N);
			Executor e = Executors.newFixedThreadPool(10);

			for (int i = 0; i < N; ++i) // create and start threads
				e.execute(new WorkerRunnable(doneSignal, i));

			doneSignal.await();           // wait for all to finish
			System.out.println("done");
		}
	}

	static class WorkerRunnable implements Runnable {
		private final CountDownLatch doneSignal;
		private final int i;
		WorkerRunnable(CountDownLatch doneSignal, int i) {
			this.doneSignal = doneSignal;
			this.i = i;
		}
		public void run() {
			doWork(i);
			doneSignal.countDown();
		}

		void doWork(int i ) { 
			System.out.println("DO work");
		}
	}
}
