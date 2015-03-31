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

import java.util.concurrent.Exchanger;

/*
 - synchronization point at which thread can pair and swap elements within pairs. 
 - each thread prsent some object on entry to exchange method
 - match with a partner thread. receive it parter's object on return.
 - bi directional form of synchronous queue. 
 - useful in application genetic algorithm and pipeline design.
 - example. swap buffers between thread.  
 */
public class Exchanger_Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FillAndEmpty main = new FillAndEmpty();
		main.start();
	}
	static class FillAndEmpty {
		Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
		DataBuffer initialEmptyBuffer = new DataBuffer();
		DataBuffer initialFullBuffer = new DataBuffer();

		class FillingLoop implements Runnable {
			public void run() {
				DataBuffer currentBuffer = initialEmptyBuffer;
				try {
					while (currentBuffer != null) {
						addToBuffer(currentBuffer);
						if (currentBuffer.isFull()) {
							System.out.println("exchange start");
							currentBuffer = (DataBuffer) exchanger.exchange(currentBuffer);
							System.out.println("exchange end");
						}
						System.out.println("sleep ");
						//Thread.sleep(2000);

					} 
				} catch (InterruptedException ex) {

				}
			}
			private void addToBuffer(DataBuffer currentBuffer) {
				currentBuffer.data =  "data " + counter;				
				System.out.println("Add : " + currentBuffer.data);
				counter++;
			}			
			int counter = 0;
		}

		class EmptyingLoop implements Runnable {
			public void run() {
				DataBuffer currentBuffer = initialFullBuffer;
				try {
					while (currentBuffer != null) {
						
						if (currentBuffer.isEmpty()) {
							System.out.println("\t\texchange start");
							currentBuffer = (DataBuffer) exchanger.exchange(currentBuffer);
							System.out.println("\t\texchange end");
						} 
						takeFromBuffer(currentBuffer);
						System.out.println("\t\tsleep ");
						Thread.sleep(3000);
					}
				} catch (InterruptedException ex) {

				}
			}

			private void takeFromBuffer(DataBuffer currentBuffer) {
				System.out.println("		Take : " + currentBuffer.data);
				currentBuffer.data = "";
			}
		}

		void start() {
			new Thread(new FillingLoop()).start();
			new Thread(new EmptyingLoop()).start();
		}
	}
	static class DataBuffer {
		String data = "";

		public boolean isFull() {
			return (!data.equals(""));			
		}

		public boolean isEmpty() {
			return (data.equals(""));
		}
	}
}
