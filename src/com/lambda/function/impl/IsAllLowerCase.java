package com.lambda.function.impl;

import com.lambda.function.ValidationStrategy;

public class IsAllLowerCase implements ValidationStrategy  {

	@Override
	public boolean execute(String s) {
		return s.matches("[a-z]+");
	}

}
