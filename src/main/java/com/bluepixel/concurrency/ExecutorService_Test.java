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

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ExecutorService_Test {

	public ExecutorService_Test(){

	}

	//network service in which threads in a thread pool service incoming requests.
	//It uses the preconfigured newFixedThreadPool(int) factory method:
	class NetworkService implements Runnable {
		private final ServerSocket serverSocket;
		private final ExecutorService pool;

		public NetworkService(int port, int poolSize) throws IOException {
			serverSocket = new ServerSocket(port);
			pool = Executors.newFixedThreadPool(poolSize);
		}

		public void run() { // run the service
			try {
				for (;;) {
					pool.execute(new Handler(serverSocket.accept()));
				}
			} catch (IOException ex) {
				pool.shutdown();
			}
		}

		//The following method shuts down an ExecutorService in two phases, 
		//first by calling shutdown to reject incoming tasks, and then calling shutdownNow, 
		//if necessary, to cancel any lingering tasks:
		void shutdownAndAwaitTermination(ExecutorService pool) {
			pool.shutdown(); // Disable new tasks from being submitted
			try {
				// Wait a while for existing tasks to terminate
				if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
					pool.shutdownNow(); // Cancel currently executing tasks
					// Wait a while for tasks to respond to being cancelled
					if (!pool.awaitTermination(60, TimeUnit.SECONDS))
						System.err.println("Pool did not terminate");
				}
			} catch (InterruptedException ie) {
				// (Re-)Cancel if current thread also interrupted
				pool.shutdownNow();
				// Preserve interrupt status
				Thread.currentThread().interrupt();
			}
		}
	}

	class Handler implements Runnable {
		private final Socket socket;
		Handler(Socket socket) {
			this.socket = socket; 
		}
		public void run() {
			// read and service request on socket
		}
	}


}
