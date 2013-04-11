package edu.ycp.cs365.actordemo;

/**
 * Specification for one row of points in the Mandelbrot set computation.
 * 
 * @author David Hovemeyer
 */
public class RowSpec {
	private final double xmin, y, dx;
	private final int numSamples;
	
	/**
	 * Constructor.
	 * 
	 * @param xmin       minimum X (real) value for the row
	 * @param y          Y (imaginary) value for the row
	 * @param dx         X distance between points in the row
	 * @param numSamples how many points in the row
	 */
	public RowSpec(double xmin, double y, double dx, int numSamples) {
		this.xmin = xmin;
		this.y = y;
		this.dx = dx;
		this.numSamples = numSamples;
	}
	
	public double getXmin() {
		return xmin;
	}
	
	public double getY() {
		return y;
	}
	
	public double getDx() {
		return dx;
	}
	
	public int getNumSamples() {
		return numSamples;
	}
}
