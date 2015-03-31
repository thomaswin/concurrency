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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

public class CountDownLatch_Test2 {

	public static void main(String[] args) throws InterruptedException {
		new MainThread().startTesting();
	}

	static String message;
	static ConcurrentHashMap<Integer, String> messageMap;
	static class MainThread {

		int Tasks = 10000;
		int N = 1;
		int length = 20;
		public void startTesting() {
			CountDownLatch startSignal = new CountDownLatch(1);
			CountDownLatch doneSignal = new CountDownLatch(N);

			for (int i = 1; i <= N; ++i){
				ParserRunnable task = new ParserRunnable(startSignal, doneSignal);
				task.setRange(i, Tasks/N);
				Thread thread = new Thread(task);
				thread.setName("thread " + i);
				thread.start();
			}
			StringBuilder str = new StringBuilder();
			for(int i = 1; i < length; i++){
				str.append(Calendar.getInstance().getTimeInMillis()).append(",");
			}
			message = str.toString();		
			messageMap = new ConcurrentHashMap<Integer, String>();
			long start = System.currentTimeMillis();
			startSignal.countDown();
			try {
				doneSignal.await();
			} catch (InterruptedException e) {
			}
			System.out.println("total size : " + messageMap.size());
			System.out.println("total time : " + (System.currentTimeMillis() - start));
		}
	}

	static class ParserRunnable implements Runnable {

		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;
		private int start;
		private int end;
		ParserRunnable(CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		public void setRange(int i, int j) {
			start = (i-1) *j +1;
			end = (i) *j;
			System.out.println(Thread.currentThread().getName() + " " + start + " " + end);			
		}

		@Override
		public void run() {
			try {
				startSignal.await();
				doComputing();
				doneSignal.countDown();
			} catch (InterruptedException e) { }
		}
		
		private void doComputing() {
			for(int i = start; i <= end; i++ ) {
				String copyString = new String(message);
				String[] split = copyString.split(",");
				List<Date> list = new ArrayList<Date>();
				for(String str : split) {
					list.add(new Date(Long.parseLong(str)));
				}
				System.out.println(Thread.currentThread().getName() + " " + i);
				messageMap.put(i, Thread.currentThread().getName());
			}
		}
	}
}
