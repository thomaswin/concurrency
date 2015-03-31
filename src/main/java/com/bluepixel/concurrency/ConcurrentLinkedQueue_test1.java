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

import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConcurrentLinkedQueue_test1 {

	public static void main(String[] args) {

		Queue<Message> queue = new ConcurrentLinkedQueue<Message>();

		Producer p = new Producer(queue);
		Consumer c = new Consumer(queue);

		Thread t1 = new Thread(p);
		Thread t2 = new Thread(c);


		t1.start();
		t2.start();
	}
	
	static class Producer implements Runnable {

		private int ctr;
		private final Queue<Message> messageQueue;
		private final Random r;

		public Producer(Queue<Message> messageQueue) {
			this.messageQueue = messageQueue;
			r = new Random();
		}

		@Override
		public void run() {
			while (true) {
				produce();
				int wait = r.nextInt(5000);
				try {
					Thread.sleep(wait);
				} catch (InterruptedException ex) {
					Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

		private void produce() {
			Message m = new Message(++ctr, "Example message.");
			messageQueue.offer(m);
			synchronized (messageQueue) {
				messageQueue.notifyAll();
			}
			System.out.println("Producer: " + m);
		}
	}

	static class Consumer implements Runnable {

		private final Queue<Message> queue;

		public Consumer(Queue<Message> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {
			while (true) {
				consume();
				try {
					synchronized (queue) {
						queue.wait();
					}
				} catch (InterruptedException ex) {
					Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}

		private void consume() {
			while (!queue.isEmpty()) {
				Message m = queue.poll();
				if (m != null) {
					System.out.println("Consumer: " + m.toString());
				}
			}
		}
	}
	static class Message {

		private int id;
		private String message;

		public Message(int id, String message) {
			this.id = id;
			this.message = message;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return "Message{ id:" + id + ",message: " + message + "}";
		}
	}
}


