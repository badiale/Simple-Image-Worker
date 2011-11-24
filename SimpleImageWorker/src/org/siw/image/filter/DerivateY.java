package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DerivateY extends Filter {
	
	public DerivateY () {
		matrix = new double[][] { {-1}, {2}, { -1} };
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		Filter f = new DerivateY();
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
