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

package com.bluepixel.concurrency.softweakreference;

public abstract class Subscriber implements Runnable { 

	protected String subscriberName; 
	protected volatile Fluctuation fluctuation; 

	public Subscriber(String threadName) { 
		this.subscriberName = threadName; 
	} 

	public Subscriber() { 
	} 

	public Subscriber(Fluctuation fluctuation, String subscriberName) { 
		this.subscriberName = subscriberName; 
		this.fluctuation = fluctuation; 
	} 


	public void run() { 
		doYourThing(); 
		String objectName = fluctuation.getDescription(); 
		this.fluctuation = null; 
		System.out.println(subscriberName + " realeased strong reference on " + objectName + " and calls for gc."); 
		System.gc(); 
		System.out.println(subscriberName + ": Hoping gc executed. Checking the WeakHashMap size : " + ServerBroker.currentlyProcessedFluctuations.size()); 
	} 
	abstract void doYourThing(); 
} 