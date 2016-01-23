package com.multiSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClientTest {
	
	public static void main(String[] args) {
		try {
			Socket socket=new Socket("192.168.2.102",30000);
			socket.setSoTimeout(10000);
			//获取服务器端的流
			String result="";
			String line;
			BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));  
			while((line=reader.readLine())!=null){
			
				result=line+"\n";
			}
			reader.close();
			socket.close();
			System.out.println(result);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
