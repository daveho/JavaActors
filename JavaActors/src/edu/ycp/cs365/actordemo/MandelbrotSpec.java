package edu.ycp.cs365.actordemo;

public class MandelbrotSpec {
	private final double xmin, ymin, xmax, ymax;
	private final int ncols, nrows;
	
	public MandelbrotSpec(double xmin, double ymin, double xmax, double ymax, int ncols, int nrows) {
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
		this.ncols = ncols;
		this.nrows = nrows;
	}
	
	public double getXmin() {
		return xmin;
	}
	
	public double getYmin() {
		return ymin;
	}
	
	public double getXmax() {
		return xmax;
	}
	
	public double getYmax() {
		return ymax;
	}
	
	public int getNcols() {
		return ncols;
	}
	
	public int getNrows() {
		return nrows;
	}
}
