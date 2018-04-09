package com.lambda.test;

import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.lambda.function.ProcessingObject;
import com.lambda.function.impl.HeaderTextProcessing;
import com.lambda.function.impl.SpellCheckerProcessing;

public class ProcessingTest {

	public static void main(String[] args) {
		//责任链模式是一种创建处理对象序列( 比如操作序列) 的通用方案。一个处理对象可能需要在完成一些工作之后，
		//将结果传递给另一个对象，这个对象接着做一些工作,再转交给下一个处理对象，以此类推。
		
		// 传统方式 
		ProcessingObject<String> p1 = new HeaderTextProcessing();
		ProcessingObject<String> p2 = new SpellCheckerProcessing();

		p1.setSuccessor(p2);
		String result = p1.handle("Aren't labdas really sexy?!!");
		System.out.println(result);

		// Java 8 lambda 方式
		UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;

		UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");

		Function<String, String> pipeline = headerProcessing.andThen(spellCheckerProcessing);

		String results = pipeline.apply("Aren't labdas really sexy?!!");
		
		System.out.println(results);
	}

}
