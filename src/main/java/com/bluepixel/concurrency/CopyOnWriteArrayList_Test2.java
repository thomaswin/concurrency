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

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayList_Test2 {
	public static void main(String[] args) throws InterruptedException {

		final CopyOnWriteArrayList<Integer> numbers = new CopyOnWriteArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));

		// new thread to concurrently modify the list
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// sleep a little so that for loop below can print part of
					// the list
					Thread.sleep(250);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				numbers.add(10);
				System.out.println("numbers:" + numbers);
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// sleep a little so that for loop below can print part of
					// the list
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				System.out.println("numbers:" + numbers);
			}
		}).start();

		// don't update concurrent loop
		for (int i : numbers) {
			System.out.println(i);
			// sleep a little to let other thread finish adding an element
			// before iteration is complete
			Thread.sleep(1000);
		}

		// update concurrent loop
		for (int i = 0; i < numbers.size(); i++) {
			System.out.println("index : " + i + " value : " + numbers.get(i));
			// sleep a little to let other thread finish adding an element
			// before iteration is complete
			Thread.sleep(1000);
		}
		
	}
}
