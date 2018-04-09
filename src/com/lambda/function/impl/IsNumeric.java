package com.lambda.function.impl;

import com.lambda.function.ValidationStrategy;

public class IsNumeric implements ValidationStrategy {
	
	public boolean execute(String s) {
		return s.matches("\\d+");
	}

}
