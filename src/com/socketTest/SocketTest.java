package com.socketTest;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketTest {
	
	
	public static void main(String[] args) {
		
		
		try {
			ServerSocket ssocket=new ServerSocket(30000);
			if(!ssocket.isClosed()){
				ssocket.close();
			}
			//通过循环不断的获取来自客户端的请求
			while(true){
				//每当客户端请求一个服务器端对应生成一个
				Socket socket=ssocket.accept();
				PrintWriter print=new PrintWriter(socket.getOutputStream());
				print.print("我们都是好孩子");
				print.flush();
				print.close();
				socket.close();
				}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
