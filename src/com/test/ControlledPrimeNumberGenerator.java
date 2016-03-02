package com.test;

import java.io.IOException;
import java.io.InputStreamReader;

public class ControlledPrimeNumberGenerator {

	public static void main(String[] args) {
		Thread prime = new Thread(new WorkersThread());
		prime.start();
		InputStreamReader in = new InputStreamReader(System.in);
		try {
			while (in.read() != '\n') {
			}
			prime.interrupt();

			Thread.sleep(100);
		} catch (IOException ex) {
			ex.printStackTrace();

		} catch (InterruptedException ex) {
			ex.printStackTrace();

		}
		if (prime.isInterrupted()) {
			System.out.println("\n Number generation has" + "already been interrupted");
		} else {
			System.out.println("Number generator" + " is not currently running");

		}
		Thread lazyWorker = new Thread(new LazyWorker());
		lazyWorker.start();
		System.out.println("\n Running lazy worker");
		try {
			Thread.sleep(100);

		} catch (InterruptedException ex) {

		}
		lazyWorker.interrupt();

	}
}

class WorkersThread implements Runnable {

	@Override
	public void run() {
		long i = 1;
		while (true) {
			long j;
			for (j = 2; j < i; j++) {
				long n = i % j;
				if (n == 0) {
					break;
				}
			}
			if (i == j) {
				System.err.print("  " + i);
			}
			i++;

			if (Thread.interrupted()) {
				System.out.println("\nStopping prime" + "number generator");
				return;
			}
		}
	}

}

class LazyWorker implements Runnable {

	@Override
	public void run() {
		try {
			Thread.sleep(100000);
		} catch (InterruptedException ex) {
			System.out.println("lazy work" + ex.toString());
		}

	}

}
