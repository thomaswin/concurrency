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

package com.bluepixel.concurrency.weakreference;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class ReferencesTest {

	// test the behavior of VM when memory is out of enough .
	// start garbable collectioin
	private WeakReference<Map<Integer, String>> myMap;
	
	public static void main(String[] args) {
		new ReferencesTest().doFunction();
	}

	private void doFunction() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		myMap = new WeakReference<Map<Integer,String>>(map);
		
		map = null;
		
		int i = 0; 
		while(true) {
			if (myMap != null){
				if(myMap.get() != null) {
					myMap.get().put(i++, "test " +  i);
					System.out.println("still working");	
				} else {
					System.out.println("Free");	
				}	
			}
		}
	}
}
