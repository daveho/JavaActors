package edu.ycp.cs365.actordemo;

/**
 * Iteration counts for one row of the Mandelbrot set computation.
 * 
 * @author David Hovemeyer
 */
public class Row {
	private double y;
	private int[] iterCounts;
	
	public Row(double y, int numSamples) {
		this.y = y;
		this.iterCounts = new int[numSamples];
	}
	
	public double getY() {
		return y;
	}
	
	public void setIterCount(int index, int value) {
		iterCounts[index] = value;
	}
	
	public int[] getIterCounts() {
		return iterCounts;
	}
}
