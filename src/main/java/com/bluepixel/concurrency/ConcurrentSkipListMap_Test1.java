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

import java.util.NavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;


public class ConcurrentSkipListMap_Test1 {

	public static void main(String[] args) {
		System.out.println("Navigable Map Example!\n");

		NavigableMap <Integer, String>navMap = new ConcurrentSkipListMap<Integer, String>();
		navMap.put(1, "January");
		navMap.put(2, "February");
		navMap.put(3, "March");
		navMap.put(4, "April");
		navMap.put(5, "May");
		navMap.put(6, "June");
		navMap.put(7, "July");
		navMap.put(8, "August");
		navMap.put(9, "September");
		navMap.put(10, "October");
		navMap.put(11, "November");
		navMap.put(12, "December");

		//Displaying all data
		System.out.println("Data in the navigable map: " + navMap.descendingMap()+"\n");

		//Retrieving first data
		System.out.print("First data: " + navMap.firstEntry()+"\n");

		//Retrieving last data
		System.out.print("Last data: " + navMap.lastEntry()+"\n\n");

		//Retrieving the nreatest less than or equal to the given key
		System.out.print("Nearest less than or equal to the given key: " + navMap.floorEntry(5)+"\n");

		//Retrieving the greatest key strictly less than the given key
		System.out.println("Retrieving the greatest key strictly less than the given key: " + navMap.lowerEntry(3));
		
		//Retrieving a key-value associated with the least key  strictly greater than the given key		
		System.out.println("Retriving data from navigable map greter than the given key: " + navMap.higherEntry(5)+"\n");
		
		//Removing first
		System.out.println("Removing First: " + navMap.pollFirstEntry());
		
		//Removing last
		System.out.println("Removing Last: " + navMap.pollLastEntry()+"\n");
		
		//Displaying all data
		System.out.println("Now data: " + navMap.descendingMap());
	}
}
