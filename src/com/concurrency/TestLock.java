package com.concurrency;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestLock {

	
	 static int count  = 0;

	    public static void main(String[] args) throws InterruptedException {
	       ExecutorService executorService = Executors.newFixedThreadPool(100);
	       CountDownLatch countDownLatch = new CountDownLatch(100);
	       SimpleSpinningLock simpleSpinningLock = new SimpleSpinningLock();
	       for (int i = 0 ; i < 100 ; i++){
	           executorService.execute(new Runnable() {
	               @Override
	               public void run() {
	                   simpleSpinningLock.lock();
	                   ++count;
	                   simpleSpinningLock.unLock();
	                   countDownLatch.countDown();
	               }
	           });

	       }
	       countDownLatch.await();
	       System.out.println(count);
	    }
}
