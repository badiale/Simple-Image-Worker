package org.siw.image.pointoperations;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.siw.util.ColorUtils;

public class Exponential implements PointOperation {
	private double gamma;

	public Exponential(double gamma) {
		super();
		this.gamma = gamma;
	}

	@Override
	public void execute(BufferedImage img) {
		double c = Math.pow(256, - (gamma - 1));
		
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				int[] colors = ColorUtils.toArray(img.getRGB(x, y));
				
				for (int i = 0 ; i < colors.length; i++) 
					colors[i] = (int) (c * Math.pow(colors[i], gamma));
				
				img.setRGB(x, y, ColorUtils.toInteger(colors));
			}
	}
	
	public static void main (String[] args) throws Exception {
		BufferedImage microbios = ImageIO.read(new File("testes/microbios.png"));
		
		PointOperation op = new Exponential(10);
		op.execute(microbios);
		
		ImageIO.write(microbios, "png", new File("testes_out/teste.png"));
	}
}
