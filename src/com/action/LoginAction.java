package com.action;

import com.annotation.RequestMapping;

@RequestMapping("/front")
public class LoginAction {
	
	@RequestMapping("/login.htm")
	public String loginUI(){
		System.out.println("测试annotation实现url跳转功能");
		return "loginUI";
	}
}
