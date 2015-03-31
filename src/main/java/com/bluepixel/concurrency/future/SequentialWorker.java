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

package com.bluepixel.concurrency.future;

import java.util.Date;

public class SequentialWorker {
    public SequentialWorker() {
    }
    private static int numberOfJobs = 5;

    public static void main(String[] args) {
        Date startTime = new Date();
        System.out.println("Start Work "  + startTime);
        for(int i=0;i < numberOfJobs;i++) {
          System.out.println("Start worker ");
          SlowWorker worker = new SlowWorker(i);
          worker.doWork();
        }
        System.out.println("... try to do something while the work is being done....");

        Date endTime = new Date();
        System.out.println("End work at " + endTime);
        System.out.println("Job took " + new Double(0.001*(endTime.getTime() - startTime.getTime()))+ " seconds ");
        System.exit(0);
    }
}