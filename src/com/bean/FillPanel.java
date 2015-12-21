package com.bean;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class FillPanel  extends JPanel{
	private static final long serialVersionUID = -4046399293657102681L;

	public void paintComponent(Graphics g){
	      super.paintComponents(g);
	      Graphics2D g2 = (Graphics2D)g;
	      double leftX = 0;
	      double leftY = 0;
	      double width = 400;
	      double height = 400;
	     
	     Rectangle2D rec1 = new Rectangle2D.Double(leftX, leftY, width, height);
	     Rectangle2D rec2 = new Rectangle2D.Double(leftX+50, leftY+50, width-100, height-100);
	     Rectangle2D rec3 = new Rectangle2D.Double(42, 30, 315, 310);
	    Ellipse2D ese = new Ellipse2D.Double();
	    ese.setFrame(rec1);
	    g2.setPaint(Color.yellow);
	    g2.fill(ese); 
	    Ellipse2D ese2 = new Ellipse2D.Double();
	    ese2.setFrame(rec2);
	    g2.setPaint(Color.black);
	    g2.fill(ese2);
	    Ellipse2D ese3 = new Ellipse2D.Double();
	    ese3.setFrame(rec3);
	    g2.setPaint(Color.yellow);
	    g2.fill(ese3); 
	    Ellipse2D circle = new Ellipse2D.Double();
	    circle.setFrameFromCenter(120,150,140,170);
	    g2.setPaint(Color.black);
	    g2.fill(circle);
	    Ellipse2D circle1 = new Ellipse2D.Double();
	    circle1.setFrameFromCenter(280,150,300,170);
	    g2.setPaint(Color.black);
	    g2.fill(circle1);
	

	}
	}
