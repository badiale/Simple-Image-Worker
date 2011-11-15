package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Mean extends Filter {

	public Mean (int size) {
		matrix = new double[size][size];
		
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				matrix[y][x] = 1.0 / (size*size);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		Filter f = new Mean(20);
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
