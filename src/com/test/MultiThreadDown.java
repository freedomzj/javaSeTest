package com.test;

import com.util.DownUtil;

public class MultiThreadDown {

	
	public static void main(String[] args) throws Exception {
		final DownUtil downUtil=new DownUtil("http://www.firmpal.com/wb_26fbda17c646984d.txt","D://car.txt",1);
		//��ʼ����
		downUtil.download();
		new Thread(() ->{
			while(downUtil.getCompleteRate()<1){
				//ÿ��0.1���ѯһ���������ɽ���
				//GUI�����пƸ��ݸý��������ƽ�����
				System.out.println("����ɣ�"+downUtil.getCompleteRate());
				try{
					Thread.sleep(1000);
				}catch(Exception ex){}
			}
		}).start();
	}
}
