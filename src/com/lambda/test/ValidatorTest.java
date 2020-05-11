package com.lambda.test;

import com.lambda.function.impl.IsAllLowerCase;
import com.lambda.function.impl.IsNumeric;

public class ValidatorTest {

	public static void main(String[] args) {

		//测略模式 
		//传统方式
		Validator numericValidator = new Validator(new IsNumeric());
		boolean b1 = numericValidator.validate("aaaa");
		Validator lowerCaseValidator = new Validator(new IsAllLowerCase ()); 
		boolean b2 = lowerCaseValidator.validate("bbbb");
		System.out.println("b1:"+b1+"b2:"+b2);
		
		//Java 8方式
		Validator validator = new Validator((String s) -> s.matches("\\d+"));
		boolean b3 = validator.validate("aaaa");
		
		Validator validator1 = new Validator((String s) -> s.matches("[a-z]+"));
		boolean b4 = validator1.validate("bbbb");
		System.out.println("b3:"+b3+"b4:"+b4);
	}

}
