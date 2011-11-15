package org.siw.image.filter;

import org.siw.util.matrixoperations.MatrixShift;

public class ConvolutionGaussian extends ConvolutionFilter {
	public ConvolutionGaussian (double sigma, int width, int height) {
		matrix = new double[height][width];
		
		double sqrSigma = 2.0 * sigma*sigma;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double x = j - width/2.0;
				double y = i - height/2.0;
				matrix[i][j] = Math.exp(-((x*x) + (y*y)) / (sqrSigma));
			}
		}
		
		// shifta
		MatrixShift.execute(matrix);
	}
}
