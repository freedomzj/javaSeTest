package com.test;

public class PrimeNumberGenerator {
	public static void main(String[] args) {
		Thread primeNumberGenerator=new Thread(new WorkerThread());
		primeNumberGenerator.setDaemon(true);
		primeNumberGenerator.start();
		try{
			Thread.sleep(10);
		}catch(Exception ex)
		{
			
		}
				
	}
	
	
}

class WorkerThread implements Runnable{

	@Override
	public void run() {
		long i=1;
		while(true){
			long j;
			for(j=2;j<i;j++){
				long n=i%j;
				if(n==0){
					break;
				}
			}
			if(i==j){
				System.err.print("  "+i);
			}
			i++;
		}
	}
	
}
