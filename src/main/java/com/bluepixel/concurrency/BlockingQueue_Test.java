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
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;


public class BlockingQueue_Test {


	public static void main(String[] args) {
		@SuppressWarnings("unchecked")
		BlockingQueue<Object> q = new LinkedBlockingQueue<Object>(5);
		Producer p = new Producer(q);
		Consumer c1 = new Consumer(q);
		Consumer c2 = new Consumer(q);
		new Thread(p).start();
		new Thread(c1).start();
		new Thread(c2).start();

	}
	static class Producer implements Runnable {
		private final BlockingQueue<Object> queue;
		Producer(BlockingQueue<Object> q) {
			queue = q; 
		}
		public void run() {
			for(int i = 0; i < 10; i++) {
				try {
					queue.put(produce(i));
					System.out.println("Producer puts " + i + " " + queue.remainingCapacity());
					Thread.sleep(300);
				} catch (InterruptedException e) {

				} 
			}
		}
		Object produce(int i ) {

			return "Hello " + i;
		}
	}

	static class Consumer implements Runnable {
		private final BlockingQueue<Object> queue;
		Consumer(BlockingQueue<Object> q) { 
			queue = q; 
		}
		public void run() {
			try {
				while (true) { 
					consume(queue.take()); 
					Thread.sleep(3000);
				}
			} catch (InterruptedException ex) {

			}
		}
		void consume(Object x) { 

			System.out.println("\t consumer gets " + (String) x);
		}
	}


}
