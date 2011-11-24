package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Gaussian extends Filter {

	public Gaussian (double sigma) {
		int size = (int) (6 * sigma);
		if (size % 2 == 0) size++;

		createMatrix(size, size, sigma);
	}
	
	public Gaussian (int width, int height, double sigma) {
		createMatrix(width, height, sigma);
	}
	
	private void createMatrix (int width, int height, double sigma) {
		matrix = new double[height][width];
		
		double sqrSigma = 2 * sigma*sigma;
		
		double sum = 0.0;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int x = (-width/2)+j;
				int y = (-height/2)+i;
				matrix[i][j] = Math.exp(-((x*x) + (y*y)) / (sqrSigma));
				
				sum += matrix[i][j];
			}
		}
		
		// normaliza o filtro
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
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
