package com.lambda.function.impl;

import com.lambda.function.ProcessingObject;

public class SpellCheckerProcessing extends ProcessingObject<String> {
	
	public String handleWork(String text){
        return text.replaceAll("labda", "lambda");
}

}
