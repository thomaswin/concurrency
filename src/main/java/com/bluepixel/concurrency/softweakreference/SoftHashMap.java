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

import java.util.*; 
import java.lang.ref.*; 


/** 
 * 
 * @author bdegerat 
 */ 
// this is based on Heinz Kabutz implementation/ I just remooved the most recently used cashed part 
public class SoftHashMap extends AbstractMap { 

	private final Map hash = new HashMap(); 
	private final ReferenceQueue queue = new ReferenceQueue(); 

	public SoftHashMap() { 
	} 

	@Override 
	public Object get(Object key) { 
		Object result = null; 
		SoftReference soft_ref = (SoftReference) hash.get(key); 
		if (soft_ref != null) { 
			result = soft_ref.get(); 
			if (result == null) { 
				hash.remove(key); 
			} else { 
			} 
		} 
		return result; 
	} 

	private static class SoftValue extends SoftReference { 

		private final Object key; // always make data member final 

		private SoftValue(Object k, Object key, ReferenceQueue q) { 
			super(k, q); 
			this.key = key; 
		} 
	} 

	private void processQueue() { 
		SoftValue sv; 
		while ((sv = (SoftValue) queue.poll()) != null) { 
			hash.remove(sv.key); 
		} 
	} 

	@Override 
	public Object put(Object key, Object value) { 
		processQueue(); 
		return hash.put(key, new SoftValue(value, key, queue)); 
	} 

	@Override 
	public Object remove(Object key) { 
		processQueue(); 
		return hash.remove(key); 
	} 

	@Override 
	public void clear() { 
		processQueue(); 
		hash.clear(); 
	} 

	@Override 
	public int size() { 
		processQueue(); 
		return hash.size(); 
	} 

	@Override 
	public Set entrySet() { 
		throw new UnsupportedOperationException(); 
	} 
} 
