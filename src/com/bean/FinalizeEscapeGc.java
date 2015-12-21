package com.bean;

public class FinalizeEscapeGc {
	
	public static FinalizeEscapeGc hook=null;
	
	public void isAlive(){
		System.out.println("yes ,i am still alive");
	}

	@Override
	//对象可以被gc时自救一次  finalize方法只会执行一次
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		System.out.println("finalize method executed!");
		FinalizeEscapeGc.hook=this;
		
	}
	
	public static void main(String[] args) throws InterruptedException {
		hook=new FinalizeEscapeGc();
		
		//对象拯救自己第一次
		hook=null;
		System.gc();
		//finalize执行过慢等到0.5秒
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
