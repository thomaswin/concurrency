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

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.w3c.dom.ranges.Range;


public class ConcurrentHashMapTest3 {

	/**
	 * @param args
	 */

	private ConcurrentMap<Integer, String> cache1 = new ConcurrentHashMap<Integer, String>();
	private ConcurrentMap<Integer, String> cache2 = new ConcurrentHashMap<Integer, String>();
	public static void main(String[] args) {
		ConcurrentHashMapTest3 test = new ConcurrentHashMapTest3();
		test.test1();
	}

	private static final String CONCURRENCY_LEVEL_DEFAULT = "50";
	private static final String CONCURRENCY_KEY = "concurrency";
	long startTime;
	public void test1() {

		final int concurrencyLevel = Integer.parseInt(System.getProperty(CONCURRENCY_KEY, CONCURRENCY_LEVEL_DEFAULT));
		final ExecutorService executor = Executors.newCachedThreadPool();
		System.out.println(concurrencyLevel);
		startTime = System.currentTimeMillis();
		System.out.println("end time " + (System.currentTimeMillis() - startTime));
		for (int i =0; i < concurrencyLevel; i++){
			executor.submit(new MyTask(i, 1000));
		}
	}

	public void serialTest() {
		final int concurrencyLevel = Integer.parseInt(System.getProperty(CONCURRENCY_KEY, CONCURRENCY_LEVEL_DEFAULT));
		final ExecutorService executor = Executors.newCachedThreadPool();
		System.out.println(concurrencyLevel);
		startTime = System.currentTimeMillis();
		for (int i =0; i < concurrencyLevel; i++){
			int start;
			int end;

			start = i * 1000+ 1;
			end = (i+1) * 1000;
			String threadName = Thread.currentThread().getName();
			for(int j = start; j <= end; j++) {
				cache1.put(j, threadName + " " + j);
				System.out.println(threadName + " " + cache1.size());
				double value = 0;
				for(int k = 0 ; k <= 100000; k++) {
					value = k * 10000 / 102324;
				}
			}
		}
		System.out.println("end time " + (System.currentTimeMillis() - startTime));	
	}

	/*
		i *1000 + 1 		  (i+1) * 1000
	0 	= 0 -> 1000;		0 *1000 + 1 = 0001 	: (0+1) * 1000 = 1000
	1  = 1001 -> 2000;		1 *1000 + 1 = 1001  : (1+1) * 1000 = 2000	
	2  = 2001 -> 3000; 	2 *1000 + 1 = 2001  : (2+1) * 1000 = 3000
	3  = 3001 -> 4000; 	3 *1000 + 1 = 3001  : (3+1) * 1000 = 4000 
	 */

	class MyTask implements Runnable{
		int start;
		int end;
		public MyTask(int index, int range ) {
			start = index * range + 1;
			end = (index+1) * range;
		}
		@Override
		public void run() {
			String threadName = Thread.currentThread().getName();
			for(int i = start; i <= end; i++) {

				cache1.put(i, threadName + " " + i);
				//				try {
				//					Thread.sleep(100);
				//				} catch (InterruptedException e) {
				//				}
				double value = 0;
				for(int k = 0 ; k <= 100000; k++) {
					value = k * 10000 / 102324;
				}

				System.out.println(threadName + " " + cache1.size());
				System.out.println("end time " + (System.currentTimeMillis() - startTime));
			}
		}
	}
}




