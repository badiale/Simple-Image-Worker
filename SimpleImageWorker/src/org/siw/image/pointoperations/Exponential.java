package org.siw.image.pointoperations;

import java.io.File;

import org.siw.containers.ImageContainer;
import org.siw.containers.PGM;
import org.siw.image.Image;
import org.siw.image.Pixel;

public class Exponential extends PointOperation {
	private double gamma;

	public Exponential(double gamma) {
		super();
		this.gamma = gamma;
	}

	@Override
	public void execute(Image img) {
		double c = Math.pow(256, - (gamma - 1));
		
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Pixel pixel = img.getPixel(x, y);
				pixel.setColor(c * Math.pow(pixel.getColor(), gamma));
			}
	}
	
	public static void main (String[] args) throws Exception {
		ImageContainer cont = new PGM(); 
		Image lena = cont.load(new File("testes/microbios.pgm"));
		
		PointOperation op = new Exponential(10);
		op.execute(lena);
		
		cont.save(new File("testes_out/teste.pgm"), lena);
	}
}
