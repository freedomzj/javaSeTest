package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClientTest {
	
	public static void main(String[] args) {
		try {
			Socket socket=new Socket("127.0.0",3000);
			//获取服务器端的流
			String result="";
			String line;
			BufferedReader reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));  
			while((line=reader.readLine())!=null){
			
				result=line+"/n";
			}
			reader.close();
			socket.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
