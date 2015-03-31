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

import java.util.concurrent.Semaphore;


public class Semaphore_Test {

	class Pool {
		private static final int MAX_AVAILABLE = 100;
		private final Semaphore available = new Semaphore(MAX_AVAILABLE, true);

		public Object getItem() throws InterruptedException {
			available.acquire();
			return getNextAvailableItem();
		}

		public void putItem(Object x) {
			if (markAsUnused(x))
				available.release();
		}

		// Not a particularly efficient data structure; just for demo

		protected Object[] items = null; //... whatever kinds of items being managed
		protected boolean[] used = new boolean[MAX_AVAILABLE];

		protected synchronized Object getNextAvailableItem() {
			for (int i = 0; i < MAX_AVAILABLE; ++i) {
				if (!used[i]) {
					used[i] = true;
					return items[i];
				}
			}
			return null; // not reached
		}

		protected synchronized boolean markAsUnused(Object item) {
			for (int i = 0; i < MAX_AVAILABLE; ++i) {
				if (item == items[i]) {
					if (used[i]) {
						used[i] = false;
						return true;
					} else
						return false;
				}
			}
			return false;
		}
	}
}
