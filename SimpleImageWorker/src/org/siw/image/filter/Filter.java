package org.siw.image.filter;

import org.siw.image.Image;

public class Filter {

	private double[][] matrix;
	
	public Filter() {
		
	}
	
	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public void apply (Image img) {
		// TODO
		throw new RuntimeException("Feature not implemented.");
	}

	public static void main(String[] args) {
	}

}
