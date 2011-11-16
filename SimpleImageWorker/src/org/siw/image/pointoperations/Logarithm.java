package org.siw.image.pointoperations;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.siw.util.ColorUtils;

public class Logarithm implements PointOperation {
	private double c;
	
	public Logarithm () {
		super();
		c = 255 / (Math.log(255));
	}
	
	public Logarithm(double c) {
		super();
		this.c = c;
	}

	@Override
	public void execute(BufferedImage img) {
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				int[] colors = ColorUtils.toArray(img.getRGB(x, y));
				
				for (int i = 0 ; i < colors.length; i++) 
					colors[i] = (int) (c * Math.log(1.0 + colors[i]));
				
				img.setRGB(x, y, ColorUtils.toInteger(colors));
			}
	}
	
	public static void main (String[] args) throws Exception {
		BufferedImage fusca = ImageIO.read(new File("testes/fusca.png"));
		
		PointOperation op = new Logarithm();
		op.execute(fusca);
		
		ImageIO.write(fusca, "png", new File("testes_out/teste.png"));
	}
}
