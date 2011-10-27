package org.siw.image;

public class Pixel {
	private int color;

	public Pixel () {
		setAbsoluteColor(0);
	}

	public Pixel (int color) {
		setAbsoluteColor(color);
	}

	public void setAbsoluteColor   (int color  ) { 
		if (color < 0) this.color = 0;
		else if (color > 255) this.color = 255;
		else setColor(color);
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	public int getColor   () { return this.color  ; }

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
