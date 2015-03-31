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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;


public class Future_Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	interface ArchiveSearcher { 
		String search(String target); 
	}

	class App {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		ArchiveSearcher searcher = new ArchiveSearcher() {

			@Override
			public String search(final String target) {
				@SuppressWarnings("unchecked")
				Future<?> future= executor.submit(new Callable<Object>() {

					@Override
					public Object call() throws Exception {
						return searcher.search(target);

					}
				});

				FutureTask<?> future1 =new FutureTask<Object>(new Callable<Object>() {
					@Override
					public Object call() throws Exception {
						return searcher.search(target);
					}
				});
				executor.execute(future1);

				displayOtherThings(); // do other things while searching
				try {
					displayText(future.get()); // use future
				} catch (ExecutionException ex) { 
					cleanup();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			private void displayText(Object object) {
				// TODO Auto-generated method stub

			}

			private void cleanup() {
				// TODO Auto-generated method stub

			}

			private void displayOtherThings() {

			}
		};


	}

}
