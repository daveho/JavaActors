package edu.ycp.cs365.actordemo;

/**
 * Specification of the overall Mandelbrot set computation:
 * which region of the complex plane, and how many rows and columns
 * of points to sample.
 * 
 * @author David Hovemeyer
 */
public class MandelbrotSpec {
	private final double xmin, ymin, xmax, ymax;
	private final int ncols, nrows;
	
	/**
	 * Constructor.
	 * 
	 * @param xmin    minimim X (real) value
	 * @param ymin    minimum Y (imaginary) value
	 * @param xmax    maximum X (real) value
	 * @param ymax    maximum Y (imaginary) value
	 * @param ncols   number of columns of points to sample
	 * @param nrows   number of rows of points to sample
	 */
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
