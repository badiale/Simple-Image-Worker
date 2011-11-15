package org.siw.image.pointoperations;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.siw.util.ColorUtils;

public class Contrast implements PointOperation {
	private int min;
	private int max;
	
	public Contrast(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}

	@Override
	public void execute (BufferedImage img) {
		int oldmin = 255;
		int oldmax = 0;

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Color color = new Color(img.getRGB(x,y));
				
				if (oldmin > color.getRed())   oldmin = color.getRed();
				if (oldmin > color.getGreen()) oldmin = color.getGreen();
				if (oldmin > color.getBlue())  oldmin = color.getBlue();
				
				if (oldmax < color.getRed())   oldmax = color.getRed();
				if (oldmax < color.getGreen()) oldmax = color.getGreen();
				if (oldmax < color.getBlue())  oldmax = color.getBlue();
			}

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				int[] colors = ColorUtils.toArray(img.getRGB(x, y));
				
				for (int i = 0 ; i < colors.length; i++) 
					colors[i] = min + (((colors[i] - oldmin) * (max - min)) / (oldmax - oldmin));
				
				img.setRGB(x, y, ColorUtils.toInteger(colors));
			}
	}
	
	public static void main (String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		PointOperation op = new Contrast(0, 255);
		op.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
