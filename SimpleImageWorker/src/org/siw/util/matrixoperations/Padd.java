package org.siw.util.matrixoperations;

import java.io.File;

import org.siw.containers.ImageContainer;
import org.siw.containers.PGM;
import org.siw.image.Image;
import org.siw.image.Pixel;

public class Padd extends MatrixOperations {
	
	private int size;
	
	public Padd(int paddingSize) {
		super();
		this.size = paddingSize;
	}

	@Override
	public double[][] execute(double[][] matrix) {
		int matrixH = matrix.length;
		int matrixW = matrix[0].length;
		
		double[][] padd = new double[matrixH + size*2][matrixW + size*2];
		
		// copies the original image into the padded area
		for (int y = 0; y < matrixH; y++)
			for (int x = 0; x < matrixW; x++)
				padd[size + y][size + x] = matrix[y][x];
		
		return padd;
	}

	@Override
	public Pixel[][] execute(Pixel[][] matrix) {
		int matrixH = matrix.length;
		int matrixW = matrix[0].length;
		
		Pixel[][] padd = new Pixel[matrixH + size*2][matrixW + size*2];
		for (int i = 0; i < matrixH + size*2; i++) {
			for (int j = 0; j < matrixW + size*2; j++) {
				padd[i][j] = new Pixel();
			}
		}
		
		// copies the original image into the padded area
		for (int y = 0; y < matrixH; y++)
			for (int x = 0; x < matrixW; x++)
				padd[size + y][size + x] = new Pixel(matrix[y][x]);
		
		return padd;
	}
	
	public static void main (String[] args) throws Exception {
		ImageContainer cont = new PGM(); 
		Image lena = cont.load(new File("testes/lena.big.pgm"));
		
		MatrixOperations op = new Padd(20);
		Pixel[][] data = op.execute(lena.getData());
		lena.setData(data);
		
		cont.save(new File("testes_out/teste.pgm"), lena);
	}
}
