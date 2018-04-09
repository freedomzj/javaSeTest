package com.lambda.function.impl;

import com.lambda.function.Observer;

public class NYTimes implements Observer {
	
	public void notify(String tweet) {
		if (tweet != null && tweet.contains("money")) {
			System.out.println("Breaking news in NY! " + tweet);
		}
	}
}