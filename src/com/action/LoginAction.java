package com.action;

import com.annotation.RequestMapping;

@RequestMapping("/front")
public class LoginAction {
	
	@RequestMapping("/login.htm")
	public String loginUI(){
		System.out.println("����annotationʵ��url��ת����");
		return "loginUI";
	}
}
