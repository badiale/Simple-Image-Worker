package org.siw.image.pointoperations;

import java.io.File;

import org.siw.containers.ImageContainer;
import org.siw.containers.PGM;
import org.siw.image.Image;

public class Contrast extends PointOperation {
	private int min;
	private int max;
	
	public Contrast(int min, int max) {
		super();
		this.min = min;
		this.max = max;
	}

	@Override
	public void execute(Image img) {
		int oldmin, oldmax;
		oldmin = oldmax = img.getPixel(0,0).getColor();

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				int color = img.getPixel(x,y).getColor();
				if (oldmin > color) oldmin = color;
				if (oldmax < color) oldmax = color;
			}

		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++)
				img.getPixel(x, y).setColor(min + (((img.getPixel(x,y).getColor() - oldmin) * (max - min)) / (oldmax - oldmin)));
	}
	
	public static void main (String[] args) throws Exception {
		ImageContainer cont = new PGM(); 
		Image lena = cont.load(new File("testes/lena.big.pgm"));
		
		PointOperation op = new Contrast(0, 255);
		op.execute(lena);
		
		cont.save(new File("testes_out/teste.pgm"), lena);
	}
}
