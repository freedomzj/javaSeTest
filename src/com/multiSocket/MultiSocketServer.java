package com.multiSocket;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiSocketServer {
	
	
	public static  List<Socket> socketList=Collections.synchronizedList(new ArrayList<Socket>());
	
	public static void main(String[] args)  {
		
		try{
			ServerSocket ssk=new ServerSocket(30001);
			System.out.println("����������");
			while(true){
				Socket socket= ssk.accept();//�ȴ��ͻ�������
				socketList.add(socket);
				new Thread(new ServerThread(socket)).start();;//ÿ����һ��socket��һ���߳�װ��
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		
		
	}
}
