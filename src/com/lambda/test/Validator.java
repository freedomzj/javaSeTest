package com.lambda.test;

import com.lambda.function.ValidationStrategy;

public class Validator {

	private final ValidationStrategy strategy;

	public Validator(ValidationStrategy v) {
		this.strategy = v;
	}

	public boolean validate(String s) {
		return strategy.execute(s);
	}
}
