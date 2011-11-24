package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class DerivateX extends Filter {
	
	public DerivateX () {
		matrix = new double[][] { {-1, 2, -1} };
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		Filter f = new DerivateX();
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
