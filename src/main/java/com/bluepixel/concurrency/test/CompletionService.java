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

public class CompletionService {
	
	private static int NUM_OF_TASKS = 1000;
			
	public CompletionService() {
		
	}
	
	public void run() {
		
		long begTest = new java.util.Date().getTime();

		int nrOfProcessors = Runtime.getRuntime().availableProcessors();
		ExecutorService executorService = Executors.newFixedThreadPool(50);
		java.util.concurrent.CompletionService<Object> completionService = new ExecutorCompletionService<Object>(executorService);

		for (int index = 0; index < NUM_OF_TASKS; index++) {
			completionService.submit(new Task(index));
		}

		Object taskResult;
		for(int index = 0; index < NUM_OF_TASKS; index++) {
			try {
				taskResult = completionService.take().get();
				System.out.println("result "+taskResult);
			}
			catch (InterruptedException e) {}
			catch (ExecutionException e) {}
		}
		Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
		System.out.println("run time " + secs + " secs");
	}

	public static void main(String[] args) {
		new CompletionService().run();
		System.exit(0);
	}
}

















