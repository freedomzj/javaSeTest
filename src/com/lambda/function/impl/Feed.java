package com.lambda.function.impl;

import java.util.ArrayList;
import java.util.List;

import com.lambda.function.Observer;
import com.lambda.function.Subject;

public class Feed implements Subject {
	
	private final List<Observer> observers = new ArrayList<>();

	public void registerObserver(Observer o) {
		this.observers.add(o);
	}

	public void notifyObservers(String tweet) {
		observers.forEach(o -> o.notify(tweet));
	}
}