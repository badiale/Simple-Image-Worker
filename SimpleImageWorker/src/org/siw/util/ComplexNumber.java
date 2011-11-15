package org.siw.util;

public class ComplexNumber {
	public double a, b;

	// complex number: a + b * i
	public ComplexNumber() {
		this.a = 0;
		this.b = 0;
	}

	public ComplexNumber(double a, double b) {
		this.a = a;
		this.b = b;
	}

	public ComplexNumber(ComplexNumber z) {
		this.a = z.a;
		this.b = z.b;
	}

	public void setReal(double a) {this.a = a;}
	public void setImaginary(double b) {this.b = b;}
	
	public double getReal() { return this.a;}
	public double getImaginary() { return this.b; }
	
	public double abs () {
		return Math.sqrt(a*a + b*b);
	}
	
	public void add(ComplexNumber c2) {
		this.a += c2.a;
		this.b += c2.b;
	}

	public void subtract(ComplexNumber c2) {
		this.a -= c2.a;
		this.b -= c2.b;
	}

	public void divide(ComplexNumber c2) {
		double c = this.a;
		this.a = (this.a * c2.a + this.b * c2.b) / (c2.a * c2.a + c2.b * c2.b);
		this.b = (c * c2.b * (-1) + this.b * c2.a) / (c2.a * c2.a + c2.b * c2.b);
	}
	
	public void divide(double scalar) {
		this.a = this.a / scalar;
		this.b = this.b / scalar;
	}

	public void multiply(ComplexNumber c2) {
		double c = this.a;
		this.a = this.a * c2.a - this.b * c2.b;
		this.b = c * c2.b + this.b * c2.a;
	}
	
	public void multiply(double scalar) {
		this.a = this.a * scalar;
		this.b = this.b * scalar;
	}

	public void conjugate () {
		this.b = -this.b;
	} 
	
	public String toString() {
		return a + " + " + b + "i";
	}
	
	// elevates the euler number to this complex number
	public void exp() {
		double cte = Math.exp(a);
		double newa = cte * Math.cos(b);
		double newb = cte * Math.sin(b);
		this.a = newa;
		this.b = newb;
	}
	
	public static ComplexNumber add(ComplexNumber c1, ComplexNumber c2) {
		ComplexNumber ret = new ComplexNumber(c1);
		ret.add(c2);
		return ret;
	}

	public static ComplexNumber subtract(ComplexNumber c1, ComplexNumber c2) {
		ComplexNumber ret = new ComplexNumber(c1);
		ret.subtract(c2);
		return ret;
	}

	public static ComplexNumber divide(ComplexNumber c1, ComplexNumber c2) {
		ComplexNumber ret = new ComplexNumber(c1);
		ret.divide(c2);
		return ret;
	}
	
	public static ComplexNumber multiply(ComplexNumber c1, ComplexNumber c2) {
		ComplexNumber ret = new ComplexNumber(c1);
		ret.multiply(c2);
		return ret;
	}
	
	public static ComplexNumber multiply(ComplexNumber c, double d) {
		ComplexNumber ret = new ComplexNumber(c);
		ret.multiply(d);
		return ret;
	}
	
	public static ComplexNumber exp(ComplexNumber c1) {
		ComplexNumber ret = new ComplexNumber(c1);
		ret.exp();
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof org.siw.util.ComplexNumber) {
			ComplexNumber c2 = (ComplexNumber) obj;
			double delta = 0.00000000001;
			return (this.a - delta <= c2.a && this.a + delta >= c2.a) && (this.b -delta <= c2.b && this.b + delta >= c2.b);
		} else {
			return super.equals(obj);
		}
	}
	
}
