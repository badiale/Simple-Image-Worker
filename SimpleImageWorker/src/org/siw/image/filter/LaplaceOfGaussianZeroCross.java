package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class LaplaceOfGaussianZeroCross extends LaplaceOfGaussian {

	public LaplaceOfGaussianZeroCross (double sigma) {
		super(sigma);
	}
	
	@Override
	public int[][][] process (BufferedImage img) {
		// processa normalmente o filtro
		int [][][] data = super.process(img);
		
		// faz o pos processamento para encontrar zero crossings
		int [][][] newdata = new int[img.getHeight()][img.getWidth()][3]; 
		
		// procura por zero crossing
		for (int i = 1; i < img.getHeight() - 1; i++) {
			for (int j = 1; j < img.getWidth() - 1; j++) {
				for (int k = 0; k < 3; k++) {
					if ((data[i - 1][j - 1][k] < 0 && data[i + 1][j + 1][k] > 0) || (data[i - 1][j - 1][k] > 0 && data[i + 1][j + 1][k] < 0)) { newdata[i][j][k] = 255; }
					else if ((data[i - 1][j + 1][k] < 0 && data[i + 1][j - 1][k] > 0) || (data[i - 1][j + 1][k] > 0 && data[i + 1][j - 1][k] < 0)) { newdata[i][j][k] = 255; }
					else if ((data[i - 1][j + 0][k] < 0 && data[i + 1][j - 0][k] > 0) || (data[i - 1][j + 0][k] > 0 && data[i + 1][j - 0][k] < 0)) { newdata[i][j][k] = 255; }
					else if ((data[i - 0][j - 1][k] < 0 && data[i + 0][j + 1][k] > 0) || (data[i - 0][j - 1][k] > 0 && data[i + 0][j + 1][k] < 0)) { newdata[i][j][k] = 255; }
				}
			}
		}
		
		return newdata;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		Filter f = new LaplaceOfGaussianZeroCross(3);
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
