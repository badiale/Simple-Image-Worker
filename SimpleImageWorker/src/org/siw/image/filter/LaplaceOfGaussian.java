package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class LaplaceOfGaussian extends Filter {

	public LaplaceOfGaussian (double sigma) {
		int size = (int) (6 * sigma);
		if (size % 2 == 0) size++;

		matrix = new double[size][size];
		
		double sqrSigma = 2 * sigma*sigma;
		double cte = (1 / (Math.PI * Math.pow(sigma, 4)));
		
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				// LoG
				int x = (-size/2)+i;
				int y = (-size/2)+j;
				
				double exp = -((x*x) + (y*y)) / (sqrSigma);
				matrix[i][j] = - (cte) * (1 + exp) * Math.exp(exp);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		Filter f = new LaplaceOfGaussian(1.4);
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
