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

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;


public class CopyOnWriteArrayList_Test1 {

	public static void main(String[] args) throws InterruptedException {
		//List<Integer> list = new Vector<Integer>();
		
		int N = 5000;
		CopyOnWriteArrayList<Integer> list=new CopyOnWriteArrayList<Integer>();
		
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(N);
		
		for (int i = 1; i <= N; i++) {
			Thread a1 = new Thread(new addElement(list,i, startSignal, doneSignal));
			a1.start();
		}
		startSignal.countDown();
		doneSignal.await();
		Thread p1 = new Thread(new printElement(list));
		p1.start();
	}
}

class printElement implements Runnable {
	private List<Integer> list;
	public printElement (List<Integer> myList) { 
		list = myList;  
	}
	public void run() {
		Iterator<Integer> it = list.iterator();
		while(it.hasNext()) 
			System.out.println(it.next()+" ");
		System.out.println("total : " + list.size());
	}
}

class addElement implements Runnable {
	
	private final CountDownLatch startSignal;
	private final CountDownLatch doneSignal;
	
	private List<Integer> list;
	private int element;
	public addElement (List<Integer> myList, int e, CountDownLatch startSignal, CountDownLatch doneSignal) {
		list = myList;
		element = e;
		this.startSignal = startSignal;
		this.doneSignal = doneSignal;
	}
	public void run() { 
		try {
			startSignal.await();
			list.add(element);
			System.out.println ("element : " + element + " " + list.size());
			doneSignal.countDown();
		} catch (InterruptedException e) {
		}	
	}
}







