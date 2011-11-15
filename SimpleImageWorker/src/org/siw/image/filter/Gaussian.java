package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Gaussian extends Filter {

	public Gaussian (double sigma) {
		int size = (int) (6 * sigma);
		if (size % 2 == 0) size++;

		matrix = new double[size][size];
		
		double sqrSigma = 2 * sigma*sigma;
		
		double sum = 0.0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int x = (-size/2)+j;
				int y = (-size/2)+i;
				matrix[i][j] = Math.exp(-((x*x) + (y*y)) / (sqrSigma));
				
				sum += matrix[i][j];
			}
		}
		
		// normaliza o filtro
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] /= sum;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		Filter f = new Gaussian(5);
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
