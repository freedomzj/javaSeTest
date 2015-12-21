package com.bean;

public class FinalizeEscapeGc {
	
	public static FinalizeEscapeGc hook=null;
	
	public void isAlive(){
		System.out.println("yes ,i am still alive");
	}

	@Override
	//������Ա�gcʱ�Ծ�һ��  finalize����ֻ��ִ��һ��
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		System.out.println("finalize method executed!");
		FinalizeEscapeGc.hook=this;
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		hook=new FinalizeEscapeGc();
		
		//���������Լ���һ��
		hook=null;
		System.gc();
		//finalizeִ�й����ȵ�0.5��
		Thread.sleep(500);
		if(hook!=null){
			hook.isAlive();
		}else{
			System.err.println("no i am dead");
		}
		
		hook=null;
		
		System.gc();
		Thread.sleep(500);
		if(hook!=null){
			hook.isAlive();
		}else{
			System.err.println("no i am dead");
		}
		
		
	}
}
