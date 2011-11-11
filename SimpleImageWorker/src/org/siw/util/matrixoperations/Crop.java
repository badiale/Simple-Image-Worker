package org.siw.util.matrixoperations;

import java.io.File;

import org.siw.containers.ImageContainer;
import org.siw.containers.PGM;
import org.siw.image.Image;
import org.siw.image.Pixel;

public class Crop extends MatrixOperations {

	private int x;
	private int y;
	private int width;
	private int height;
	
	public Crop(int x, int y, int width, int height) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public double[][] execute(double[][] matrix) {
		double[][] crop = new double[height][width];
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				crop[y][x] = matrix[y + this.y][x + this.x];
		
		return crop;
	}

	@Override
	public Pixel[][] execute(Pixel[][] matrix) {
		Pixel[][] crop = new Pixel[height][width];
		
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				crop[y][x] = new Pixel(matrix[y + this.y][x + this.x]);
		
		return crop;
	}

	public static void main (String[] args) throws Exception {
		ImageContainer cont = new PGM(); 
		Image lena = cont.load(new File("testes/lena.big.pgm"));
		
		MatrixOperations op = new Crop(200, 200, 200, 200);
		Pixel[][] data = op.execute(lena.getData());
		lena.setData(data);
		
		cont.save(new File("testes_out/teste.pgm"), lena);
	}
}
