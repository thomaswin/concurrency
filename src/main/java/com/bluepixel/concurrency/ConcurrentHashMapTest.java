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




public class ConcurrentHashMapTest {
	private static final String CONCURRENCY_LEVEL_DEFAULT = "50";
	private static final String CONCURRENCY_KEY = "concurrency";
	private ConcurrentMap<Double, Double> sqrtCache = new ConcurrentHashMap<Double, Double>();

	public static void main(String args[]) {
		final ConcurrentHashMapTest test = new ConcurrentHashMapTest();
		final int concurrencyLevel = Integer.parseInt(System.getProperty(CONCURRENCY_KEY, CONCURRENCY_LEVEL_DEFAULT));
		final ExecutorService executor = Executors.newCachedThreadPool();

		try {
			for(int i = 0; i < concurrencyLevel; i++) {
				for(String s : args) {
					final Double d = Double.valueOf(s);
					executor.submit(new Runnable() {
						@Override public void run() {
							System.out.printf("sqrt of %s = %s in thread %s%n",
									d, test.getSqrt(d), Thread.currentThread().getName());
						}
					});
				}
			}
		} finally {
			executor.shutdown();
		}
	}

	// 4 steps as outlined above
	public double getSqrt(Double d) {
		Double sqrt = sqrtCache.get(d);
		if(sqrt == null) {
			sqrt = Math.sqrt(d);
			System.out.printf("calculated sqrt of %s = %s%n", d, sqrt);
			Double existing = sqrtCache.putIfAbsent(d, sqrt);
			if(existing != null) {
				System.out.printf("discard calculated sqrt %s and use the cached sqrt %s", sqrt, existing);
				sqrt = existing;
			}
		}
		return sqrt;
	}
}