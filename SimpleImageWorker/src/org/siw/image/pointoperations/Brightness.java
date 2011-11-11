package org.siw.image.pointoperations;

import java.io.File;

import org.siw.containers.ImageContainer;
import org.siw.containers.PGM;
import org.siw.image.Image;
import org.siw.image.Pixel;

public class Brightness extends PointOperation {
	private int offset;

	public Brightness(int offset) {
		super();
		this.offset = offset;
	}

	@Override
	public void execute(Image img) {
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				Pixel pixel = img.getPixel(x, y);
				pixel.setColor(pixel.getColor() + offset);
			}
	}
	
	public static void main (String[] args) throws Exception {
		ImageContainer cont = new PGM(); 
		Image lena = cont.load(new File("testes/lena.pgm"));
		
		PointOperation op = new Brightness(50);
		op.execute(lena);
		
		cont.save(new File("testes_out/teste.pgm"), lena);
	}
}
