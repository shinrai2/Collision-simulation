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
	private double v_xx;

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
		frame.setTitle(" T ����ʼģ��");
		frame.addKeyListener(new MyKeyListener());
		
		gravity = 0.012;
		v = 0;
		ball = new Ball(-50, -50, 15, 
				Math.random()*0.005 + 0.2, Math.random()*0.005 + 0.01);
		tl = new TimerListener();
		t = new Timer(1,tl);
		
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
			g2.drawString("v:"+v_xx, (int)ball.getCenterX(), 100);
			g2.drawLine(0, (int)ball.getCenterY(), 1200, (int)ball.getCenterY());
			g2.drawLine((int)ball.getCenterX(), 0, (int)ball.getCenterX(), 450);
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
				double buttom_point = ball.getCenterY()+ball.getRaduis();
				
				if(buttom_point >= 399.7 && y_v > 0) {//�ٽ���
					if(buttom_point >= 400) {
						//���Խ���ϸ΢����
						double time = (buttom_point - 400)/y_v;
						ball.setCenter(ball.getCenterX()- x_v * time, 385);
						y_v = -y_v;
						y_v*=0.68;
						x_v*=0.995;
					}
					
					//��ȥ���΢������Ϊ���������㲻׼ȷ���µ��������
					if(Math.abs(y_v)>0.009) ;
					else {
						y_v = 0;
						ball.setCenter(ball.getCenterX(), 385);
					}
					if(x_v<0.001) x_v = 0;
				}
				v = y_v;
				v_xx = x_v;
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
				ball.setX_velocity(Math.random()*0.04 + 0.6);
				ball.setY_velocity(Math.random()*0.005 + 0.2);
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
