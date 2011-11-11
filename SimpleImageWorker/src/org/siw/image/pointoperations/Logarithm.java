package org.siw.image.pointoperations;

import java.io.File;

import org.siw.containers.ImageContainer;
import org.siw.containers.PGM;
import org.siw.image.Image;

public class Logarithm extends PointOperation {
	private double c;
	private boolean cWasGiven;
	
	public Logarithm () {
		super();
		c = 0;
		cWasGiven = false;
	}
	
	public Logarithm(double c) {
		super();
		this.c = c;
		this.cWasGiven = true;
	}

	@Override
	public void execute(Image img) {
		if (!cWasGiven)
			c = 255 / (Math.log(255)); 
		
		for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++)
				img.getPixel(x, y).setColor(c * Math.log(1 + img.getPixel(x, y).getColor()));
	}
	
	public static void main (String[] args) throws Exception {
		ImageContainer cont = new PGM(); 
		Image lena = cont.load(new File("testes/fusca.pgm"));
		
		PointOperation op = new Logarithm();
		op.execute(lena);
		
		cont.save(new File("testes_out/teste.pgm"), lena);
	}
}
