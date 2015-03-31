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

import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.net.Socket; 
import java.util.logging.Level; 
import java.util.logging.Logger; 


public class FluctuationPublisher implements Runnable { 

	private static int nbSentObjects = 0; 
	private final int port; 

	public FluctuationPublisher(int port) { 
		this.port = port; 
	} 

	public void run() { 
		ObjectOutputStream ostream = null ; 
		try { 
			Socket socket = new Socket(Main.HOST, port); 
			ostream = new ObjectOutputStream(socket.getOutputStream()); 
			Fluctuation fluctuation = Fluctuation.generateRandomFluctuation(); 
			fluctuation.setDescription("Fluctuation-" + getNbSentObjects()); 
			ostream.writeObject(fluctuation); 
			ostream.flush(); 
			System.out.println(fluctuation.getDescription() + " published. " + fluctuation.getAsset() + " took " + fluctuation.getFluctuation() + "%"); 
			fluctuation = null; 
			ostream.close(); 
		} catch (IOException ex) { 
			Logger.getLogger(FluctuationPublisher.class.getName()).log(Level.SEVERE, null, ex); 
		} finally { 
			try { 
				ostream.close(); 
			} catch (IOException ex) { 
				Logger.getLogger(FluctuationPublisher.class.getName()).log(Level.SEVERE, null, ex); 
			} 
		} 
	} 

	//The class is the monitor 
	public synchronized static int getNbSentObjects(){ 
		return ++nbSentObjects; 
	} 

} 
