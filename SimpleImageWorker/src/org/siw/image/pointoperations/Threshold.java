package org.siw.image.pointoperations;

import java.io.File;

import org.siw.containers.ImageContainer;
import org.siw.containers.PGM;
import org.siw.image.Image;

public class Threshold extends PointOperation {
	private int threshold;
	
	public Threshold (int threshold) {
		super();
		this.threshold = threshold;
	}
	
	@Override
	public void execute(Image img) {
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				if (img.getPixel(x, y).getColor() > threshold) img.getPixel(x, y).setColor(255);
				else img.getPixel(x, y).setColor(0);
			}
		}
	}

	public static void main (String[] args) throws Exception {
		ImageContainer cont = new PGM(); 
		Image lena = cont.load(new File("testes/lena.big.pgm"));
		
		PointOperation op = new Threshold(125);
		op.execute(lena);
		
		cont.save(new File("testes_out/teste.pgm"), lena);
	}
}
