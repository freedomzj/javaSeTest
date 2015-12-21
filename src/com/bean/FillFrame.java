package com.bean;

import javax.swing.JFrame;

public class FillFrame extends JFrame{
	public static final int DEFAULT_WIDTH = 500;
    public static final int DEFAULT_HEIGHT = 500;
	private static final long serialVersionUID = -1201739273448542559L;
	public FillFrame(){
		this.setTitle("Ð¦Á³");
          setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
          setLocation(100,100);

          this.add(new FillPanel());
  }

    
}


