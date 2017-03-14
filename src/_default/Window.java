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
		frame.setTitle(" T 键开始模拟");
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
//			g2.drawString("v:"+v+" y:"+ball.getCenterY(), 100, (int)ball.getCenterY());
//			g2.drawString("v:"+v_xx, (int)ball.getCenterX(), 100);
//			g2.drawLine(0, (int)ball.getCenterY(), 1200, (int)ball.getCenterY());
//			g2.drawLine((int)ball.getCenterX(), 0, (int)ball.getCenterX(), 450);
//			g2.drawLine(0, 400, 1200, 400);
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
				if(y + y_v >= 385) {
					y = 385;
					if(y_v!=0) x += (1 - ((y+y_v)-385)/y_v) * x_v;
					else x += x_v;
					if (y_v < 0.0065 && y == 385) y_v = 0;//y方向接近为0
					if (x_v < 0.005) x_v = 0;//x方向接近为0
					if(y_v!=0) {
						y_v = -y_v;
						y_v *= 0.85;
					}
					if(x_v!=0) x_v *= 0.998;
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
			if(keyCode == KeyEvent.VK_T) {
				System.out.println("Timer start.");
				ball.setCenter(Math.random()*50 + 20, Math.random()*50 + 20);
				ball.setX_velocity(Math.random()*0.04 + 0.28);
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
