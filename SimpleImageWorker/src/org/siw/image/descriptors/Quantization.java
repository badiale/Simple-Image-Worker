package org.siw.image.descriptors;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Quantization {

	public static int[][] get(BufferedImage img) {
		int[][] ret = new int[img.getHeight()][img.getWidth()];
		
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color c = new Color(img.getRGB(x, y));
				int red = (c.getRed() & 0xC0) >> 2;
				int green = (c.getGreen() & 0xC0) >> 4;
				int blue = (c.getBlue() & 0xC0) >> 6;
				ret[y][x] = red | green | blue;
			}
		
		return ret;
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage img = ImageIO.read(new File("testes/lena.big.png"));
		
		int [][] quantization = Quantization.get(img);
		
		BufferedImage newimg = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		for (int i = 0; i < quantization.length; i++) {
			for (int j = 0; j < quantization[0].length; j++) {
				int color = quantization[i][j];
				int red = (color & 0x30) << 2;
				int green = (color & 0xC) << 4;
				int blue = (color & 0x3) << 6;
				newimg.setRGB(j, i, new Color(red, green, blue).getRGB());
			}
		}
		
		ImageIO.write(newimg, "png", new File("testes_out/teste.png"));
	}

}
