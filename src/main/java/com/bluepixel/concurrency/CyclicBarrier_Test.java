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

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


public class CyclicBarrier_Test {

	//A synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point. 
	//CyclicBarriers are useful in programs involving a fixed sized party of threads that must occasionally wait for each other. 
	//The barrier is called cyclic because it can be re-used after the waiting threads are released.
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	class Solver {
		final int N;
		final float[][] data;
		final CyclicBarrier barrier;

		class Worker implements Runnable {
			int myRow;
			Worker(int row) { myRow = row; }
			public void run() {
				while (!done()) {
					processRow(myRow);

					try {
						barrier.await();
					} catch (InterruptedException ex) {
						return;
					} catch (BrokenBarrierException ex) {
						return;
					}
				}
			}
			private void processRow(int myRow2) {
				
			}
			private boolean done() {
				return false;
			}
		}

		public Solver(float[][] matrix) {
			data = matrix;
			N = matrix.length;
			barrier = new CyclicBarrier(N, new Runnable() {
				public void run() {
					mergeRows();
				}

				private void mergeRows() {
					
				}
			});
			for (int i = 0; i < N; ++i)
				new Thread(new Worker(i)).start();

			waitUntilDone();
		}

		private void waitUntilDone() {
			
		}
	}
}
