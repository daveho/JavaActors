package edu.ycp.cs365.actordemo;

public class Complex {
	private final double real, imag;
	
	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}
	
	public double getReal() {
		return real;
	}
	
	public double getImag() {
		return imag;
	}
	
	public Complex add(Complex other) {
		return new Complex(this.real + other.real, this.imag + other.imag);
	}
	
	public Complex multiply(Complex other) {
		double a = this.real;
		double b = this.imag;
		double c = other.real;
		double d = other.imag;
		return new Complex(a*c - b*d, b*c + a*d);
	}
	
	public double magnitude() {
		return Math.sqrt(real*real + imag*imag);
	}
}
