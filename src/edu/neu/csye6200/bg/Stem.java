package edu.neu.csye6200.bg;

/**
 * @author Xiaobin Gao
 * NUID: 001445384
 */
public class Stem {
	
	private double startX, startY;
	private double length;
	private double direction;
	Stem[] children = new Stem[4];  // A stem may have 4 child stems
	
	/**
	 * Constructor
	 * @param startX x coordinate of start location
	 * @param startY y coordinate of start location
	 * @param length 
	 * @param direction angle offset in radian measure from the vertical
	 */
	public Stem(double startX, double startY, double length, double direction) {
		this.startX = startX;
		this.startY = startY;
		this.length = length;
		this.direction = direction;
	}
	
	/**
	 * @return x coordinate of start location
	 */
	public double startX() {
		return startX;
	}
	
	/**
	 * @return y coordinate of start location
	 */
	public double startY() {
		return startY;
	}
	
	/**
	 * @return x coordinate of end location
	 */
	public double endX() {
		return (startX + length * Math.sin(direction));
	}
	
	/**
	 * @return y coordinate of end location
	 */
	public double endY() {
		return (startY - length * Math.cos(direction));
	}
	
	/**
	 * @return length
	 */
	public double length() {
		return length;
	}
	
	/**
	 * @return direction
	 */
	public double direction() {
		return direction;
	}
}
