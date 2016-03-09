package com.demo.swing;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.bean.FillPanel;

public class MyFirstJf extends JFrame {

	public static final int DEFAULT_WIDTH = 1400;
	public static final int DEFAULT_HEIGHT =900;
	private  String title;

	public MyFirstJf(String title) {
		this.title=title;
		this.setTitle(title);
		// 获取当前屏幕
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = screen.width;
		int screenHeight = screen.height;
		this.setBounds((screenWidth - DEFAULT_WIDTH) / 2, (screenHeight - DEFAULT_HEIGHT) / 2,
				DEFAULT_WIDTH, DEFAULT_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
