package _default;

import java.awt.geom.Ellipse2D;

public class Ball {
	private double raduis;//0-360(without 360)
	private double x_velocity;
	private double y_velocity;
	private Ellipse2D ellipse;
	
	public Ball(double x, double y, double r, double x_v, double y_v) {
		x_velocity = x_v;
		y_velocity = y_v;
		raduis = r;
		ellipse = new Ellipse2D.Double(x+r, y+r, 2*r, 2*r);
	}
	public double getRaduis() {
		return raduis;
	}
	public double getCenterX() {
		return ellipse.getCenterX();
	}
	public double getCenterY() {
		return ellipse.getCenterY();
	}
	public void setCenter(double x, double y) {
		ellipse.setFrame(x-raduis, y-raduis, 2*raduis, 2*raduis);
	}
	public Ellipse2D getEllipse() {
		return ellipse;
	}
	public double getX_velocity() {
		return x_velocity;
	}
	public void setX_velocity(double x_velocity) {
		this.x_velocity = x_velocity;
	}
	public double getY_velocity() {
		return y_velocity;
	}
	public void setY_velocity(double y_velocity) {
		this.y_velocity = y_velocity;
	}
}
