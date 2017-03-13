package _default;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


import java.awt.BorderLayout;
import java.awt.Color;

public class Window {

	private JFrame frame;
	private Ball ball;
	private Timer t;
	private TimerListener tl;
	private JPanel panel;
	private double gravity;
	private double v;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Window() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new MyKeyListener());
		
		gravity = 0.05;
		v = 0;
		ball = new Ball(-50, -50, 15, 
				Math.random()*0.010 + 0.4, Math.random()*0.010 + 0.02);
		tl = new TimerListener();
		t = new Timer(5,tl);
		
		panel = new myPanel();
		panel.setBackground(Color.WHITE);
		frame.getContentPane().add(panel, BorderLayout.CENTER);
	}

	class myPanel extends JPanel {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setPaint(Color.BLACK);
			
			g2.draw(ball.getEllipse());
			g2.fill(ball.getEllipse());
			g2.drawString("v:"+v, 100, (int)ball.getCenterY());
			g2.drawLine(0, 400, 1200, 400);
		}
	}
	class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getSource() == t) {
				double y_v = ball.getY_velocity();
				double x_v = ball.getX_velocity();
				ball.setCenter(ball.getCenterX()+x_v, ball.getCenterY()+y_v);
				y_v += gravity;
				if(ball.getCenterY()+ball.getRaduis() >= 400 && y_v > 0) {
//					y_v = -y_v;
					double yt = y_v*0.88;
					double xt = x_v*0.995;
					if(yt>0.04)
						y_v = -yt;
					else y_v = 0;
					if(xt>0.02)
						x_v = xt;
					else x_v = 0;
				}
				if(ball.getCenterY()+ball.getRaduis() >= 398 && y_v > 0) {
					if(y_v>0.2) ;
					else {
						y_v = 0;
						ball.setCenter(ball.getCenterX()+x_v, 385);
					}
				}
				v = y_v;
				ball.setY_velocity(y_v);
				ball.setX_velocity(x_v);
				
				if(y_v == 0 && x_v == 0) {
					t.stop();
					System.out.println("Timer stop.");
				}
				panel.repaint();
			}
		}
		
	}
	class MyKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub
			int keyCode = arg0.getKeyCode();
			if(keyCode == KeyEvent.VK_T) {
				System.out.println("Timer start.");
				ball.setCenter(Math.random()*50 + 20, Math.random()*50 + 20);
				ball.setX_velocity(Math.random()*0.010 + 0.4);
				t.start();
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
