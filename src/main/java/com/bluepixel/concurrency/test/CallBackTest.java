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

package com.bluepixel.concurrency.test;

import java.util.concurrent.*;
import java.util.concurrent.ExecutorService;

public class CallBackTest {
	private static int NUM_OF_TASKS = 50;
	Object result;
	int cnt = 0;
	long begTest, endTest;

	public CallBackTest() {
		begTest = new java.util.Date().getTime();
	}

	public void callBack(Object result) {
		System.out.println("result "+result);
		this.result = result;
		if(++cnt == 50) {
			Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
			System.out.println("run time " + secs + " secs");
			System.exit(0);
		}
	}

	public void run() {
		int nrOfProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService es = Executors.newFixedThreadPool(10);
		CompletionService<Object> completionService = new ExecutorCompletionService<Object>(es);

		for (int index = 0; index < NUM_OF_TASKS; index++) {
			CallBackTask task = new CallBackTask(index);
			task.setCaller(this);
			completionService.submit(task);
		}
		
		for(int i = 0;  i < NUM_OF_TASKS; i++) {
			
			// at this point after submitting the tasks the
			// main thread is free to perform other work.
		}
	}

	public static void main(String[] args) {
		new CallBackTest().run();
	}
}