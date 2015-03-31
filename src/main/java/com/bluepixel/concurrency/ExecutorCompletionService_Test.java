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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.Future;
//http://embarcaderos.net/2011/01/23/parallel-processing-and-multi-core-utilization-with-java/

public class ExecutorCompletionService_Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	void solve(Executor e, Collection<Callable> solvers) throws InterruptedException, ExecutionException {
		CompletionService<Object> ecs = new ExecutorCompletionService<Object>(e);
		for (Callable<Object> s : solvers)
			ecs.submit(s);
		int n = solvers.size();
		for (int i = 0; i < n; ++i) {
			Result r = (Result) ecs.take().get();
			if (r != null)
				use(r);

		}
	}
	private void use(Result r) {


	}

	void solve1(Executor e, Collection<Callable> solvers)
			throws InterruptedException {
		CompletionService ecs = new ExecutorCompletionService(e);
		int n = solvers.size();
		List<Future<?>> futures = new ArrayList<Future<?>>(n);
		Result result = null;
		try {
			for (Callable s : solvers)
				futures.add(ecs.submit(s));
			for (int i = 0; i < n; ++i) {
				try {
					Result r = (Result) ecs.take().get();
					if (r != null) {
						result = r;
						break;
					}
				} 
				catch (ExecutionException ignore) {}
			}
		} catch(Exception exception){

		} finally {
			for (Future f : futures)
				f.cancel(true);
		}

		if (result != null)
			use(result);
	}
}
