package com.test;

import java.text.DateFormatSymbols;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class AnnualSalesCalc {
	private static int NUMBER_OF_CUSTOMERS = 100;
	private static int NUMBER_OF_MONTHS = 12;

	private static int salesMatrix[][];

	private static class Summer implements Callable {

		private int companyId;

		public Summer(int companyId) {

			this.companyId = companyId;
		}
		public Integer call() {
			int sum = 0;
			for (int col = 0; col < NUMBER_OF_MONTHS; col++) {
				sum += salesMatrix[companyId][col];
			}
			System.out.printf("Totaling for client 1%02d completed%n", companyId);
			return sum;
		}
	}

	public static void main(String[] args) throws Exception {
		generateMatrix();
		printMatrix();

		ExecutorService executor = Executors.newFixedThreadPool(10);
		Set<Future<Integer>> set = new HashSet<Future<Integer>>();
		for (int row = 0; row < NUMBER_OF_CUSTOMERS; row++) {
			Callable<Integer> callable = new Summer(row);
//			Future<Integer> future = executor.submit(callable);
			FutureTask<Integer> future=new FutureTask<Integer>(callable);
			future.run();
			set.add(future);
		}
		int sum = 0;
		for (Future<Integer> future : set) {
			sum += future.get();
		}
		System.out.printf("%nThe annual turnover (bags): %s%n%n", sum);
		executor.shutdown();
	}

	private static void generateMatrix() {
		salesMatrix = new int[NUMBER_OF_CUSTOMERS][NUMBER_OF_MONTHS];
		for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
			for (int j = 0; j < NUMBER_OF_MONTHS; j++) {
				salesMatrix[i][j] = (int) (Math.random() * 100);
			}
		}
	}
	
	private static void printMatrix(){
		System.out.println("\t\t");
		String[] monthDisplayNames=(new DateFormatSymbols().getShortMonths());
		for(String strName: monthDisplayNames){
			System.out.printf("%8s",strName);
		}
		System.out.printf("%n%n");
		for (int i = 0; i < NUMBER_OF_CUSTOMERS; i++) {
			System.out.printf("Client ID: 1%02d",i);
			for (int j = 0; j < NUMBER_OF_MONTHS; j++) {
				System.out.printf("%8d",salesMatrix[i][j]);
				
			}
			System.out.println();
		}
		System.out.println("%n%n");
		
	}

}
