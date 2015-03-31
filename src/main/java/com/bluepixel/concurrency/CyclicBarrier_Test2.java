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

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrier_Test2 {



	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		test1();
		//test1();
	}
	
	public static void test1() throws InterruptedException, BrokenBarrierException {
		int N = 10;
		CyclicBarrier barrier = new CyclicBarrier(N);
		for (int i = 0; i < N; ++i){

			Thread thread = new Thread(new Worker(barrier));
			thread.setName("thread " + i);
			thread.start();
		}
		System.out.println("waiting main thread ");
		barrier.await();
		System.out.println("done all");

	}

	public static void test2() throws InterruptedException, BrokenBarrierException {
		CyclicBarrier barrier = new CyclicBarrier(2);

		new Thread(new Worker(barrier)).start();
		barrier.await();
		System.out.println("done cycle 1");

		new Thread(new Worker(barrier)).start();
		barrier.await();
		System.out.println("done cycle 2");
	}

	public static class Worker implements Runnable {
		private CyclicBarrier barrier;

		public Worker(CyclicBarrier barrier){
			this.barrier = barrier;
		}

		@Override
		public void run() {
			try {
				System.out.println("Thread  " + Thread.currentThread().getName() + " at : " + new Date());
				Thread.sleep(1000);
				barrier.await();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}

}
