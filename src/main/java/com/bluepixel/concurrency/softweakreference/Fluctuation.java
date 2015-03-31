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

import java.io.Serializable; 
import java.util.Random; 

/** 
 * 
 * @author bdegerat 
 */ 
public class Fluctuation implements Serializable{ 

	public enum Asset {MICROSOFT, 
		ORACLE, 
		BARCLAYS, 
		BOUYGUES, 
		ORANGE, 
		VOODAFONE, 
		RBS, 
		VALEO, 
		GOOGLE, 
		SFR, 
		NIKE, 
		ADIDAS, 
		MOTOROLA, 
		SIEMENS, 
		APPEL, 
		NOKIA, 
		INTEL, 
		DELL, 
		TOCHIBA, 
		AMD} 


	protected String additionalInformation; 
	protected Asset asset; 
	protected double fluctuation; 


	public Fluctuation(String additionalInformation, Asset asset, double fluctuation) { 
		this.additionalInformation = additionalInformation; 
		this.asset = asset; 
		this.fluctuation = fluctuation; 
	} 

	public Fluctuation() { 
	} 

	public String getDescription() { 
		return additionalInformation; 
	} 

	public void setDescription(String description) { 
		this.additionalInformation = description; 
	} 

	public Asset getAsset() { 
		return asset; 
	} 

	public void setAsset(Asset asset) { 
		this.asset = asset; 
	} 

	public double getFluctuation() { 
		return fluctuation; 
	} 

	public void setFluctuation(double fluctuation) { 
		this.fluctuation = fluctuation; 
	} 


	public static Fluctuation generateRandomFluctuation(){ 
		Fluctuation fluctuation = new Fluctuation(); 
		fluctuation.setAsset(rand.random()); 
		fluctuation.setFluctuation(randomValue(-5, 5)); 

		return fluctuation; 
	} 

	private static final RandomEnum<Asset> rand = new RandomEnum<Asset>(Asset.class); 

	private static class RandomEnum<E extends Enum> { 

		private static final Random RND = new Random(); 
		private final E[] values; 

		public RandomEnum(Class<E> token) { 
			values = token.getEnumConstants(); 
		} 

		public E random() { 
			return values[RND.nextInt(values.length)]; 
		} 
	} 

	public static double randomValue(int low, int high) { 
		Random rnd = new Random(); 
		return Math.min(low, high) + rnd.nextInt(Math.abs(high - low)) + Math.random(); 
	} 
} 
