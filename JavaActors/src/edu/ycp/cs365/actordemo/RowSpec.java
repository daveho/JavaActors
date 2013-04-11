package edu.ycp.cs365.actordemo;

public class RowSpec {
	private final double xmin, y, dx;
	private int numSamples;
	
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
