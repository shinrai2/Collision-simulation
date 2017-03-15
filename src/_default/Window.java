package _default;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

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
		frame.setBounds(100, 100, 1200, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(" �ո�� ��ʼģ��");
		frame.addKeyListener(new MyKeyListener());
		
		gravity = 0.008;
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
//			g2.drawString("v:"+v+" y:"+ball.getCenterY(), 100, (int)ball.getCenterY());
//			g2.drawString("v:"+v_xx, (int)ball.getCenterX(), 100);
//			g2.drawLine(0, (int)ball.getCenterY(), 1200, (int)ball.getCenterY());
//			g2.drawLine((int)ball.getCenterX(), 0, (int)ball.getCenterX(), 450);
			g2.drawLine(0, 400, 1200, 400);//�ױ�
			g2.drawLine(1100, 0, 1100, 480);//�ұ�
			g2.drawLine(100, 0, 100, 480);//���
		}
	}
	class TimerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(arg0.getSource() == t) {
				double y_v = ball.getY_velocity();
				double x_v = ball.getX_velocity();
				double x = ball.getCenterX();
				double y = ball.getCenterY();
				
				if(y + y_v >= 385 && x + x_v < 1085 && x + x_v > 115) {//�ױ� y_max:400 x_max:1100
					y = 385;//��������
					if(y_v!=0) x += (1 - ((y+y_v)-385)/y_v) * x_v;//��������
					else x += x_v;//y��Ϊ0ʱƽ��
					if (y_v < 0.0065 && y == 385) y_v = 0;//y����ӽ�Ϊ0
					if (Math.abs(x_v) < 0.005) x_v = 0;//x����ӽ�Ϊ0
					if(y_v!=0) {//y���ٶȷ�ת��˥��
						y_v = -y_v;
						y_v *= 0.85;
					}
					if(x_v!=0) x_v *= 0.998;//x���ٶ�˥��
				}
				else if(y + y_v < 385 && x + x_v >= 1085) {//�ұ�
					x = 1085;//��������
					if(x_v!=0) y += (1 - ((x+x_v)-1085)/x_v) * y_v;//��������
					else y += y_v;//x��Ϊ0ʱƽ��
					if(x_v!=0) {
						x_v = -x_v;
						x_v *= 0.998;
					}
					if(y_v!=0) y_v *= 0.998;
				}
				else if(y + y_v >= 385 && x + x_v >= 1085) {//���½�
					double _x = x+x_v-1085;//��������
					double _y = y+y_v-385;
					if(_x == _y) {
						x = 1085;
						y = 385;
						if(x_v!=0) {
							x_v = -x_v;
							x_v *= 0.998;
						}
						if(y_v!=0) {
							y_v = -y_v;
							y_v *= 0.85;
						}
					}
					else if (_x > _y) {
						x = 1085;
						if(x_v!=0) y += (1 - ((x+x_v)-1085)/x_v) * y_v;
						else y += y_v;
						if(x_v!=0) {
							x_v = -x_v;
							x_v *= 0.998;
						}
						if(y_v!=0) y_v *= 0.998;
					}
					else {
						y = 385;
						if(y_v!=0) x += (1 - ((y+y_v)-385)/y_v) * x_v;
						else x += x_v;
						if(y_v!=0) {//y���ٶȷ�ת��˥��
							y_v = -y_v;
							y_v *= 0.85;
						}
						if(x_v!=0) x_v *= 0.998;//x���ٶ�˥��
					}
				}
				else if(y + y_v < 385 && x + x_v <= 115) {//���
					x = 115;//��������
					if(x_v!=0) y += (1 - (115-(x+x_v))/(-x_v)) * y_v;//��������
					else y += y_v;//x��Ϊ0ʱƽ��
					if(x_v!=0) {
						x_v = -x_v;
						x_v *= 0.998;
					}
					if(y_v!=0) y_v *= 0.998;
				}
				else if(y + y_v >= 385 && x + x_v <= 115) {//���½�
					double _x = 115-x+x_v;//��������
					double _y = y+y_v-385;
					if(_x == _y) {
						x = 115;
						y = 385;
						if(x_v!=0) {
							x_v = -x_v;
							x_v *= 0.998;
						}
						if(y_v!=0) {
							y_v = -y_v;
							y_v *= 0.85;
						}
					}
					else if (_x > _y) {
						x = 115;
						if(x_v!=0) y += (1 - (115-(x+x_v))/(-x_v)) * y_v;
						else y += y_v;
						if(x_v!=0) {
							x_v = -x_v;
							x_v *= 0.998;
						}
						if(y_v!=0) y_v *= 0.998;
					}
					else {
						y = 385;
						if(y_v!=0) x += (1 - ((y+y_v)-385)/y_v) * x_v;
						else x += x_v;
						if(y_v!=0) {//y���ٶȷ�ת��˥��
							y_v = -y_v;
							y_v *= 0.85;
						}
						if(x_v!=0) x_v *= 0.998;//x���ٶ�˥��
					}
				}
				else {
					x += x_v;
					y += y_v;
				}
				
				ball.setCenter(x, y);
				if(y_v != 0) y_v += gravity;
				ball.setY_velocity(y_v);
				ball.setX_velocity(x_v);
				v = y_v;
				v_xx = x_v;
				if(x_v ==0 && y_v == 0) {
					System.out.println("Timer stop.");
					t.stop();
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
			if(keyCode == KeyEvent.VK_SPACE) {
				System.out.println("Timer start.");
				ball.setCenter(Math.random()*50 + 35, Math.random()*50 + 35);
				ball.setX_velocity(Math.random()*0.04 + 1.228);
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
