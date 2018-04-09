package com.lambda.function.impl;

import com.lambda.function.ProcessingObject;

public class HeaderTextProcessing extends ProcessingObject<String> {
	
	public String handleWork(String text) {
		return "From Raoul, Mario and Alan: " + text;
	}
	
}
