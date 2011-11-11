package org.siw.image;

import org.siw.util.ComplexNumber;

public class Pixel extends ComplexNumber {
	
	public Pixel () {
		setAbsoluteColor(0);
	}

	public Pixel (int color) {
		setAbsoluteColor(color);
	}

	public Pixel(ComplexNumber z) {
		super(z);
	}

	public Pixel(double a, double b) {
		super(a, b);
	}

	public void setAbsoluteColor   (int color  ) { 
		if (getColor() < 0) setColor(0);
		else if (getColor() > 255) setColor(255);
		else setColor(color);
	}
	
	public void setColor(double color) {
		this.setReal(color);
		this.setImaginary(0);
	}
	
	public void setColor(int color) {
		this.setColor((double) color);
	}

	/*public void setColor (int r, int g, int b) {
		this.setReal(new Color(r, g, b).getRGB());
		this.setImaginary(0);
	}*/
	
	public int getColor   () { return (int) abs(); }

	public void add(int color) { this.setAbsoluteColor(this.getColor() + color); }
	
	public void log(double c) { 
		// se c for 0, usa o default
		if (c == 0) c = (256 / Math.log(256));

		this.setAbsoluteColor((int) (c * Math.log(1 + this.getColor())));
	}
	
	public void exp(double gamma) { 
		double c = Math.pow(256, - (gamma - 1));

		this.setAbsoluteColor((int) (c * Math.pow(this.getColor(), gamma)));
	}
}
