package com.lambda.bean;

public class Loan extends Product {
	
	private Integer orderId;
	
	private Integer cost;
	
	private String userName;
	
	
	public Loan(Integer orderId,Integer cost,String userName)
	{
		this.orderId=orderId;
		this.cost=cost;
		this.userName=userName;
	}
	
	public Loan(){
		System.out.println("欢迎使用贷款业务");
	}

	
	public void filling(){
		System.out.println(userName+"贷款填单中+++++++申请金额"+cost+"生成订单号"+orderId);
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCost() {
		return cost;
	}

	public void setCost(Integer cost) {
		this.cost = cost;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}


