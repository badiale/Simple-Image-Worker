package org.siw.image.descriptors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Histogram {

	public static int[][] get(BufferedImage img) {
		int [][] ret = new int[3][256];
		
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				ret[0][c.getRed()]++;
				ret[1][c.getGreen()]++;
				ret[2][c.getBlue()]++;
			}
		
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage img = ImageIO.read(new File("testes/lena.big.png"));
		
		int [][] histogram = Histogram.get(img);
		
		for (int i = 0; i < histogram.length; i++) {
			for (int j = 0; j < histogram[0].length; j++) {
				System.out.print(histogram[i][j] + " ");
			}
			System.out.println();
		}
	}

}
