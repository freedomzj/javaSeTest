package com.multiSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread implements Runnable {

	private Socket socket;
	BufferedReader br = null;

	public ClientThread(Socket socket) throws IOException {
		this.socket = socket;
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		try {
			String content = null;
			// 不断地读取Socket输入流中的内容，并将这些内容打印输出
			while ((content = br.readLine()) != null) {
				System.out.println(content);
			}
		} catch (Exception e) {
			
		}
	}
}