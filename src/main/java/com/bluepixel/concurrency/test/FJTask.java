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

import java.util.concurrent.RecursiveTask;
class FJTask extends RecursiveTask {
	private int seq;

	public FJTask(int n) { this.seq = n; }

	public Integer compute() {
		String str = "";
		long begTest = new java.util.Date().getTime();
		System.out.println("start - Task "+seq);
		try {
			Thread.sleep(1000);
			for(int index = 0; index < 20000; index++)
				str = str + 't';
		} catch (InterruptedException e) {}
		Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
		System.out.println("run time " + secs + " secs");
		return seq;
	}
}
