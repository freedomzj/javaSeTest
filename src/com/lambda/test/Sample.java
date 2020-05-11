package com.lambda.test;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sample {
	public static void call(Runnable runnable) {
		System.out.println("calling runnable");

		// level 2 of stack
		runnable.run();
	}

	public static void main(String[] args) {
		int value = 4; // level 1 of stack
		call(() -> System.out.println(value)); // level 3 of stack
		print();
		print1();
		Map<String, Object> hm = Collections.synchronizedMap(new HashMap<String, Object>());
		hm.put("a", value);
		System.out.println(0.0 / 0.0);
		
		System.out.println(0.0 == -0.01);
		Runnable runnable = create();
//		Runnable runnable = () -> System.out.println(value);
		
		System.out.println("In main");
		runnable.run();

		int factor = 3;
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9);
		numbers.stream().filter(e -> e % 2 == 0).map(e -> e * factor).collect(toList());
	}

	public static void print() {
		String location = "World";
		Runnable runnable = () -> System.out.println("Hello " + location);
		runnable.run();
	}

	public static void print1() {
		String location = "World";
		Runnable runnable = new Runnable() {
			public void run() {
				System.out.println("Hello " + location);
			}
		};
		runnable.run();
	}

	public static Runnable create() {
		int value = 4;
		Runnable runnable = () -> System.out.println(value);
		System.out.println("exiting create");
		return runnable;
	}

}