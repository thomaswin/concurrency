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

package com.bluepixel.concurrency.test;



import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.AndroidHttpTransport;

import java.util.concurrent.Callable;

public class Task implements Callable {
	private int seq;
	public Task() {}
	public Task(int i) { seq = i; }

	public Object call() {
		String str = "";
		long begTest = new java.util.Date().getTime();
		System.out.println("start - Task "+seq);
		try {
			// sleep for 1 second to simulate a remote call,
			// just waiting for the call to return
			
			Thread.sleep(100);
			// loop that just concatenate a str to simulate
			// work on the result form remote call
			for(int i = 0; i < 10000; i++)
				str = str + 't';
			
		} catch (InterruptedException e) {}
		//main();
		Double secs = new Double((new java.util.Date().getTime() - begTest)*0.001);
		System.out.println("run time " + secs + " secs");
		return seq;
	}
	
	public static void main() {
		// TODO Auto-generated method stub
		sendDataToServer("dev1222", "reg123", "ph123", "registered", "now");
	}
	
	private static void sendDataToServer(String device_id,
			String registeration_id,
			String phoneNo,
			String registration_status,
			String update_time) {
		
		final String METHOD_NAME = "login";
		final String WSDL_TARGET_NAMESPACE = "http://example.com/";
		final String SOAP_ADDRESS = "http://windhawksoap.appspot.com/project_retrieve_v2/c2dm";
		final String SOAP_ACTION = WSDL_TARGET_NAMESPACE + METHOD_NAME;
		
		SoapObject request = new SoapObject(WSDL_TARGET_NAMESPACE, METHOD_NAME);

		request.addProperty("device_id", device_id);
		request.addProperty("registeration_id", registeration_id);
		request.addProperty("phone_no", phoneNo);
		request.addProperty("registeration_status", registration_status);
		request.addProperty("update_time", update_time);        

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = false;
		envelope.setOutputSoapObject(request);
		
		AndroidHttpTransport androidHttpTransport = new AndroidHttpTransport(SOAP_ADDRESS);
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			Object obj = envelope.getResponse();       
			String response = obj.toString();
			System.out.println(response);
		} catch(Exception e) {        	
			e.printStackTrace();
		}
	}
}
