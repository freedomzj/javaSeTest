package com.test;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

public class SineWaveAnimator extends JFrame implements Runnable {

	private int frame = 0;

	public SineWaveAnimator() {
		setTitle("Sine wave animator");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(5000, 800);
		setVisible(true);
	}

	public static void main(String[] args) {
		SineWaveAnimator app = new SineWaveAnimator();
		Thread animator = new Thread(app);
		animator.setDaemon(true);
		animator.start();

	}

	@Override
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
			}
			frame++;
		}

	}

	public void paint(Graphics g) {
		Rectangle d = getBounds();
		g.clearRect(0, 0, d.width, d.height);
		int h = d.height / 2;
		for (int x = 0; x < d.width; x++) {
			int y1 = (int) ((1.0 + Math.sin((x - frame) * 0.09)) * h);
			int y2 = (int) ((1.0 + Math.sin((x + frame) * 0.01)) * h);
			g.drawLine(x, y1, x, y2);
		}

	}

}
