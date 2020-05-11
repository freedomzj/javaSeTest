package com.lambda.test;

import com.lambda.function.impl.Feed;
import com.lambda.function.impl.Guardian;
import com.lambda.function.impl.LeMonde;
import com.lambda.function.impl.NYTimes;

public class FeedTest {

	public static void main(String[] args) {
		// 观察者模式是一种比较常见的方案，某些事件发生时(比如状态转变),如果一个对象(通
		// 常我们称之为主题) 需要自动地通知其他多个对象(称为观察者),就会采用该方案

		// 传统方式
		Feed f = new Feed();
		 f.registerObserver(new NYTimes());
		 f.registerObserver(new Guardian());
		 f.registerObserver(new LeMonde());
		
		// Java 8 lambda方式 并且不需要显示的创建三个观察者
		f.registerObserver((String tweet) -> {
			if (tweet != null && tweet.contains("money")) {
				System.out.println("Breaking news in NY! " + tweet);
			}
			if (tweet != null && tweet.contains("queen")) {
				System.out.println("Yet another news in London... " + tweet);
			}
			if (tweet != null && tweet.contains("wine")) {
				System.out.println("Today cheese, wine and news! " + tweet);
			}
		});

		
		f.notifyObservers("The queen said her favourite book is Java 8 in Action!");
		f.notifyObservers("money money money!");
	}
}
