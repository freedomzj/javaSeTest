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
			//ͨ��ѭ�����ϵĻ�ȡ���Կͻ��˵�����
			while(true){
				//ÿ���ͻ�������һ���������˶�Ӧ����һ��
				Socket socket=ssocket.accept();
				PrintWriter print=new PrintWriter(socket.getOutputStream());
				print.print("���Ƕ��Ǻú���");
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
