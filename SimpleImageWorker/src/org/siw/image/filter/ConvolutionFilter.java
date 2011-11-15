package org.siw.image.filter;

import org.siw.util.ComplexNumber;
import org.siw.util.matrixoperations.Expand;
import org.siw.util.matrixoperations.MatrixOperations;

public class ConvolutionFilter {
	protected double[][] matrix;
	
	public ConvolutionFilter() {}
	public ConvolutionFilter (double[][] matrix) { setMatrix(matrix); }
	
	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public ComplexNumber[][][] process (ComplexNumber[][][] img) {
		int numPixels = img.length;
		int height = img[0].length;
		int width = img[0][0].length;
		if (matrix[0].length != width || matrix.length != height) { 
			MatrixOperations op = new Expand(width, height);
			matrix = op.execute(matrix);
		}
		
		ComplexNumber[][][] ret = new ComplexNumber[numPixels][height][width];
		for (int k = 0; k < numPixels; k++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					ret[k][y][x] = ComplexNumber.multiply(img[k][y][x], matrix[y][x]);
				}
			}
		}
		return ret;
	}
	
	public void save (ComplexNumber[][][] img, ComplexNumber[][][] colors) {
		int numPixels = img.length;
		int height = img[0].length;
		int width = img[0][0].length;
		
		for (int k = 0; k < numPixels; k++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					img[k][y][x] = new ComplexNumber(colors[k][y][x]);
				}
			}
		}
	}
	
	public void execute (ComplexNumber[][][] img) {
		save(img, process(img));
	}
}
