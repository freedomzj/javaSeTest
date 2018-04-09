package com.lambda.function.impl;

import com.lambda.function.Observer;

public class LeMonde implements Observer {

	public void notify(String tweet) {
		if (tweet != null && tweet.contains("wine")) {
			System.out.println("Today cheese, wine and news! " + tweet);
		}
	}

}
