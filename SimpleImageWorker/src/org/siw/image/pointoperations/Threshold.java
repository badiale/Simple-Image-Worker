package org.siw.image.pointoperations;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.siw.util.ColorUtils;

public class Threshold implements PointOperation {
	private int threshold;
	
	public Threshold (int threshold) {
		super();
		this.threshold = threshold;
	}
	
	@Override
	public void execute(BufferedImage img) {
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int[] colors = ColorUtils.toArray(img.getRGB(x, y));
				
				for (int i = 0 ; i < colors.length; i++) 
					if(colors[i] > threshold) 
						colors[i] = 255;
					else 
						colors[i] = 0;
				
				img.setRGB(x, y, ColorUtils.toInteger(colors));
			}
		}
	}

	public static void main (String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		PointOperation op = new Threshold(175);
		op.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
