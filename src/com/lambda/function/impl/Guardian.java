package com.lambda.function.impl;

import com.lambda.function.Observer;

public class Guardian implements Observer {

	public void notify(String tweet) {
		if (tweet != null && tweet.contains("queen")) {
			System.out.println("Yet another news in London... " + tweet);
		}
	}
	
}
