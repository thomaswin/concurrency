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

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingDeque_Test {
	
	//- A blocking deque is represented by the java.util.concurrent.BlockingDeque interface.
	//- A blocking deque is a deque that waits to become non-empty before retrieving an element 
	//  and waits for space to become available before storing an element.
	//- A blocking deque is thread-safe and does not permit null elements.
	
	public static void main(String[] args) {
		BlockingDeque<Integer> deque = new LinkedBlockingDeque<Integer>(5);
		Runnable producer = new Producer("Producer", deque);
		Runnable consumer1 = new Consumer("Consumer 1", deque);
		Runnable consumer2 = new Consumer("Consumer 2", deque);
		new Thread(producer).start();
		new Thread(consumer1).start();
		new Thread(consumer2).start();
		new Thread(new Consumer("Consumer 3", deque)).start();
		new Thread(new Consumer("Consumer 4", deque)).start();
		new Thread(new Consumer("Consumer 5", deque)).start();
		new Thread(new Consumer("Consumer 6", deque)).start();
		new Thread(new Consumer("Consumer 7", deque)).start();
		new Thread(new Consumer("Consumer 8", deque)).start();
		new Thread(new Consumer("Consumer 9", deque)).start();
		new Thread(new Consumer("Consumer 10", deque)).start();
		
	}

	static class Producer implements Runnable {
		private String name;

		private BlockingDeque<Integer> deque;

		public Producer(String name, BlockingDeque<Integer> deque) {
			this.name = name;
			this.deque = deque;
		}

		public synchronized void run() {

			for (int i = 1; i <= 1000; i++) {
				try {
					deque.putFirst(i);
					System.out.println(name + " puts " + i);
					Thread.sleep(300);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class Consumer implements Runnable {
		private String name;

		private BlockingDeque<Integer> deque;

		public Consumer(String name, BlockingDeque<Integer> deque) {
			this.name = name;
			this.deque = deque;
		}

		public synchronized void run() {
			while(true) {
				try {
					int j = deque.takeLast();
					System.out.println("\t" + name + " takes " + j);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}




