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
			System.out.println("启动服务器");
			while(true){
				Socket socket= ssk.accept();//等待客户端连接
				socketList.add(socket);
				new Thread(new ServerThread(socket)).start();;//每启动一个socket用一个线程装载
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
		
		
	}
}
