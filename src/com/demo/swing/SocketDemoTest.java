package com.demo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SocketDemoTest {

	private static JTextField txtIpAddress;
	private static JButton btnStart;
	private static JTextArea ta;
	private static JTextField txtMsg;
	private static JButton sendMsg;
	private ServerSocket serverSocket = null;
	private static Map<Integer, String> userMap=new HashMap<Integer, String>();
	public static  List<Socket> socketList = Collections
			.synchronizedList(new ArrayList<Socket>());
	private static JLabel lblCount;
	
	private static JPanel left_panel;

	public static void main(String[] args) {
		MyFirstJf jf = new MyFirstJf("服务器端");
		// 设置布局方式
		jf.setLayout(new BorderLayout(30, 5));

		// 顶部
		JPanel top_panel = new JPanel();
		top_panel.add(new JLabel("ip地址"));
		btnStart = new JButton("开启服务器");
		btnStart.addActionListener(new SocketDemoTest().new BtnStart());
		txtIpAddress = new JTextField(10);
		txtIpAddress.setText("192.168.3.86");
		txtIpAddress.setEnabled(false);
		top_panel.add(txtIpAddress);
		top_panel.add(btnStart);

		// 消息中間內容
		JPanel p = new JPanel();
		ta = new JTextArea();
		ta.setForeground(Color.BLUE);
		ta.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(ta);
		p.setLayout(new BorderLayout());
		p.add(scrollPane, BorderLayout.CENTER);
		JPanel foot_panel = new JPanel();

		left_panel=new JPanel();
		left_panel.add(new JLabel("当前在线用户列表"));
		//底部
		foot_panel.add(new JLabel("当前喷子人数:"));
		lblCount=new JLabel("0");
		lblCount.setForeground(Color.red);
		foot_panel.add(lblCount);
		foot_panel.add(new JLabel("消息"));
		txtMsg = new JTextField(20);
		foot_panel.add(txtMsg);

		sendMsg = new JButton("发送");
		foot_panel.add(sendMsg);
		sendMsg.addActionListener(new SocketDemoTest().new BtnSendMsg());
		sendMsg.addKeyListener(new KeyAdapter() {
			 public void keyPressed(KeyEvent evt) {  
			        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {  
			        	JOptionPane.showMessageDialog(null, "aaa");
			        }  
			    }  
		});
		
		jf.add(top_panel, BorderLayout.NORTH);
		jf.add(p);
		jf.add(left_panel,BorderLayout.WEST);
		jf.add(foot_panel, BorderLayout.SOUTH);

		jf.setVisible(true);

	}

	class BtnSendMsg implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if(socketList!=null&&socketList.size()>0){
				if(!"".equals(txtMsg.getText())){
					new SocketDemoTest().new SendMsgThread().run();
				}else{
					JOptionPane.showMessageDialog(null, "发送内容不能为空请重新发送！");
				}
			}else{
				JOptionPane.showMessageDialog(null, "当前没有客户端连接请稍后重试");
			}
			
			
		}

	}

	class BtnStart implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			startServer();
		}

	}

	public void startServer() {
		try {
			serverSocket = new ServerSocket(9999);
			ta.append("服务器开启成功。。。。。\n");
		}
		catch (BindException e) {
			ta.append("服务器开启失败端口被占用。。。。。\n");
		}
		catch (IOException e) {
			ta.append("服务器开启失败。。。。。\n");
		}
		//开启线程等待接入
		new MultiSocketServer().start();
		
//		for (Object obj : userMap.keySet()) {
//			left_panel.add(new Label(userMap.get(obj)));
//		}
		btnStart.setEnabled(false);
	}

	private class MultiSocketServer extends Thread {

		private Socket socket = null;
		@Override
		public void run() {

			try {
				while (true) {
					socket = serverSocket.accept(); // 从连接请求队列中取出一个连接
					// 关闭socket延迟,以获取更快的速度
					socket.setTcpNoDelay(true);
					socketList.add(socket);// 每次创建一个对象就添加到集合中
					lblCount.setText(String.valueOf(socketList.size()));
//					ClientDemo.lblCount.setText(String.valueOf(socketList.size()));
					//对每个加入的人说一句欢迎你
					String readerLine="";
					DataInputStream dis=new DataInputStream(socket.getInputStream());
					DataOutputStream dos = new DataOutputStream(
							socket.getOutputStream());
					readerLine=	dis.readUTF();
					dos.writeUTF("服务器说:欢迎你"+readerLine+"\n");
					userMap.put(socket.getPort(), readerLine);
					
					ta.append(readerLine+socketList.size()+"加入成功！ ip地址为"
					 + socket.getInetAddress() + " :子端口为： " + socket.getPort()+"\n");
					//对所有用户推送上线消息
					for (Socket s : socketList) {
						if(s!=socket){
							DataOutputStream ds=new DataOutputStream(s.getOutputStream());
							ds.writeUTF("系统消息"+"！"+readerLine+"加入了聊天室！"+"\n");
						}
						
					}
					//接受来自客户端的消息
					new SocketDemoTest().new ReaderThread(socket).start();
					/*String readerLine="";
						//遍历socketList中的每个Socket
						//将读到的内容向每个Socket发送一次
						for (Socket s :socketList) {
							try{
								DataInputStream dis=new DataInputStream(s.getInputStream());
								readerLine=	dis.readUTF();
								dis.close();
								
								//如果捕获到异常，则表明该socket对应的客户端关闭并及时删除集合中的数据
							}catch(IOException e){
								socketList.remove(socket);
							}
						}*/
						
					// 获取客户端输出的字符
					
				}
				
				

				// 接收和发送数据
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}
		

		
		

	}
	
	
	private class ReaderThread extends Thread{
		private Socket socket;
		public ReaderThread( Socket socket){
			this.socket=socket;
		}
		@Override
		public void run() {
			while(true){
				try {
					String readLine="";
					DataInputStream ds=new DataInputStream(socket.getInputStream());
					readLine=ds.readUTF();
					ta.append(readLine);
					for (Socket s : socketList) {
						if(s!=socket){
							DataOutputStream dos=new DataOutputStream(s.getOutputStream());
							dos.writeUTF(readLine);
						}
						
					}
					//把接受到的信息推送给所有用户
//					for (Socket socket : socketList) {
//						DataInputStream ds=new DataInputStream(socket.getInputStream());
//						try{
//							System.out.println(socket.isClosed());
//							if(socket.isClosed()&&ds.readUTF()!=null)
//							
//							else
//								break;
//						}catch(Exception ex){
//						}
//					}
					
				} catch (IOException e) {
					//通信异常移除该聊天
					socketList.remove(socket);
					
					lblCount.setText(String.valueOf(socketList.size()));
//					txtMsg.setText(userMap.get(socket.getPort())+"退出了聊天室！！！！");
//					userMap.remove(socket.getPort());
//					
//					new SocketDemoTest().new SendMsgThread ().start();
				}
			}
		}
	}
	private class SendMsgThread extends Thread{
		@Override
		public void run() {
			try {
//			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				ta.append("服务器消息！  "+txtMsg.getText()+"\n");
//			System.out.println(socketList);
			for (Socket socket : socketList) {
				DataOutputStream ds=new DataOutputStream(socket.getOutputStream());
				ds.writeUTF("服务器消息！  "+txtMsg.getText()+"\n");
			}
			//循环完毕清空消息
			txtMsg.setText("");
			} catch (IOException e) {
				ta.append("服务器连接异常发送失败");
			}
			
		}
	}

}
