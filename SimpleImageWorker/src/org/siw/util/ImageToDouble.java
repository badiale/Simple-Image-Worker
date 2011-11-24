package org.siw.util;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImageToDouble {
	public static double[][][] convert (BufferedImage img) {
		double[][][] matrix = new double[3][img.getHeight()][img.getWidth()];
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				Color c =  new Color(img.getRGB(j, i));
				matrix[0][i][j] = c.getRed();
				matrix[1][i][j] = c.getGreen();
				matrix[2][i][j] = c.getBlue();
			}
		}
		return matrix;
	}
	
	public static BufferedImage convert (double[][][] matrix) {
		int height = matrix[0].length;
		int width = matrix[0][0].length;
		
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				img.setRGB(j, i, ColorUtils.toInteger((int) matrix[0][i][j], (int) matrix[1][i][j], (int) matrix[2][i][j]));
			}
		}
		
		return img;
	}
}
