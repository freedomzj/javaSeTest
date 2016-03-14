package com.demo.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
public class ClientDemo {

	private static JTextField txtIpAddress;
	private static JButton btnStart;
	private static JTextArea ta;
	private static JTextField txtMsg;
	private static JButton sendMsg;
	private static JTextField txtPort;
	private static JTextField txtName;
	private static JButton btnEnd;
	public static Socket socket;
	public static JLabel lblCount;

	public static void main(String[] args) {
		MyFirstJf jf = new MyFirstJf("客户端");
		// 设置布局方式
		jf.setLayout(new BorderLayout(30, 5));

		// 顶部
		JPanel top_panel = new JPanel();
		top_panel.add(new JLabel("ip地址"));
		btnStart = new JButton("连接服务器");
		btnStart.addActionListener(new ClientDemo().new BtnConnect());
		txtIpAddress = new JTextField(10);
		txtIpAddress.setText("192.168.3.86");
		top_panel.add(txtIpAddress);
		top_panel.add(new JLabel("端口"));
		txtPort = new JTextField(10);
		txtPort.setText("9999");
		top_panel.add(txtPort);

		top_panel.add(new JLabel("用户名"));
		txtName = new JTextField(10);
		top_panel.add(txtName);

		top_panel.add(btnStart);
		btnEnd = new JButton("关闭连接");
		btnEnd.setEnabled(false);
		btnEnd.addActionListener(new ClientDemo().new BtnCloseConnect());
		top_panel.add(btnEnd);

		// 消息中間內容
		JPanel p = new JPanel(); 
		ta = new JTextArea();
		ta.setForeground(Color.BLUE);
		ta.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(ta);
		p.setLayout(new BorderLayout());
		p.add(scrollPane, BorderLayout.CENTER);
		JPanel foot_panel = new JPanel();

		foot_panel.add(new JLabel("当前喷子人数:"));
		lblCount=new JLabel("0");
		lblCount.setForeground(Color.red);
		foot_panel.add(lblCount);
		
		foot_panel.add(new JLabel("消息"));
		txtMsg = new JTextField(20);
		foot_panel.add(txtMsg);

		sendMsg = new JButton("发送");
		// 设置发送按钮禁用
		sendMsg.setEnabled(false);
		txtMsg.setEditable(false);
		foot_panel.add(sendMsg);
		sendMsg.addActionListener(new ClientDemo().new BtnSendMsg(txtName
				.getText()));

		jf.add(top_panel, BorderLayout.NORTH);
		jf.add(p);
		jf.add(foot_panel, BorderLayout.SOUTH);
		jf.setVisible(true);
	}
	private class connectSocket extends Thread{
		@Override
		public void run() {
			try {
				socket = new Socket(txtIpAddress.getText(),
						Integer.valueOf(txtPort.getText()));

				// 获取服务器返回的数据
				// DataInputStream di=new
				// DataInputStream(socket.getInputStream());
				
				// 对服务器进行通信传递用户名过去
				DataOutputStream dos = new DataOutputStream(
						socket.getOutputStream());
				dos.writeUTF(txtName.getText());

				ta.append("服务器连接成功"+socket.isClosed()+"：ip地址为 +" + txtIpAddress.getText()
						+ "端口为" + txtPort.getText() + "\n");
				// 关闭连接服务器与禁用修改端口
				//接受消息
				new ClientDemo().new ReaderThread(new DataInputStream(socket.getInputStream())).start();
				btnStart.setEnabled(false);
				btnEnd.setEnabled(true);
				// 开启发送按钮
				sendMsg.setEnabled(true);
				txtMsg.setEditable(true);
			} catch (NumberFormatException e1) {
				ta.append("端口转换异常" + "\n");
			} catch (UnknownHostException e1) {
				ta.append("服务未连接，请开启服务器" + "\n");
			} catch (IOException e1) {
				ta.append("服务连接异常，请开启服务器" + "\n");
			}
		}
	}
	
	private class ReaderThread extends Thread{
		DataInputStream dis = null;
		public ReaderThread(DataInputStream dis){
			this.dis = dis;
		}
		
		@Override
		public void run() {
			 try {
				 while(true){
						 ta.append(dis.readUTF());
					
				 }
			} catch (IOException e) {
			}catch (Exception ex){
			}
			
		}
		
	}
	
	private class BtnConnect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if ("".equals(txtIpAddress.getText())
					|| "".equals(txtPort.getText())
					|| "".equals(txtName.getText())) {
				JOptionPane.showMessageDialog(null, "端口或者ip地址不能为空！并且用户名不能为空");
			} else {
				//开启线程通信
				new ClientDemo().new connectSocket().start();
			}
		}

	}

	private class BtnCloseConnect implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				socket.close();
				ta.append("关闭服务器" + "\n");
				// 关闭连接服务器与禁用修改端口
				btnStart.setEnabled(true);
				btnEnd.setEnabled(false);
			} catch (IOException e) {
				ta.append("关闭服务器");
			}
		}
		
	}

	private class BtnSendMsg implements ActionListener {

		private String userName;

		public BtnSendMsg(String userName) {
			this.userName = userName;
		}

		@Override
		
		public void actionPerformed(ActionEvent e) {
//			if (SocketDemoTest.socketList != null
//					&& SocketDemoTest.socketList.size() > 0) {
				if (!"".equals(txtMsg.getText())) {
					// 加载线程进行发送消息并进行更新文本框；
					new ClientDemo().new SendMsgThread(socket).run();;
				} else {
					JOptionPane.showMessageDialog(null, "发送内容不能为空请重新发送！");
				}
//			} else {
//				JOptionPane.showMessageDialog(null, "当前没有客户端连接请稍后重试");
//			}

		}

	}

	private class SendMsgThread extends Thread {
		private Socket socket;

		private SendMsgThread(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				// BufferedWriter bw=new BufferedWriter(new
				// OutputStreamWriter(socket.getOutputStream()));
				DataOutputStream ds = new DataOutputStream(
						socket.getOutputStream());
				ta.append("你说:" + txtMsg.getText() + "\n");
				ds.writeUTF(txtName.getText() + "说:" + txtMsg.getText() + "\n");
				txtMsg.setText("");
			} catch (IOException e) {
				ta.append("服务器连接异常发送失败");
			}

		}
	}

}
