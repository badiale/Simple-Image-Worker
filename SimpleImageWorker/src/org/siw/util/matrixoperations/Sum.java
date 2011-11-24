package org.siw.util.matrixoperations;

import org.siw.util.ComplexNumber;


public class Sum implements BinaryMatrixOperations {

	@Override
	public double[][] execute(double[][] a, double[][] b) {
		if (a.length != b.length || a[0].length != b[0].length)
			throw new RuntimeException("Matrix dimensions must be the same");
		
		int height = a.length;
		int width = a[0].length;
		
		double[][] c = new double[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				c[y][x] = a[y][x] + b[y][x];
		
		return c;
	}

	@Override
	public ComplexNumber[][] execute(ComplexNumber[][] a, ComplexNumber[][] b) {
		if (a.length != b.length || a[0].length != b[0].length)
			throw new RuntimeException("Matrix dimensions must be the same");
		
		int height = a.length;
		int width = a[0].length;
		
		ComplexNumber[][] c = new ComplexNumber[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				c[y][x] = new ComplexNumber(a[y][x]);
				c[y][x].add(b[y][x]);
			}
		
		return c;
	}
	@Override
	public double[][] execute(double[][] a, double scalar) {
		int height = a.length;
		int width = a[0].length;
		
		double[][] c = new double[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				c[y][x] = a[y][x] + scalar;
		
		return c;
	}

	@Override
	public ComplexNumber[][] execute(ComplexNumber[][] a, double scalar) {
		int height = a.length;
		int width = a[0].length;
		
		ComplexNumber[][] c = new ComplexNumber[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++) {
				c[y][x] = new ComplexNumber(a[y][x]);
				c[y][x].add(new ComplexNumber(scalar, 0));
			}
		
		return c;
	}
	
	public static void main (String[] args) {
		double[][] a = new double[2][2];
		double[][] b = new double[2][2];
		
		for (int y = 0; y < 2; y++)
			for (int x = 0; x < 2; x++) {
				a[y][x] = x + y;
				b[y][x] = 3;
			}
		
		BinaryMatrixOperations op = new Sum();
		double[][] c = op.execute(a, b);
		
		for (int y = 0; y < 2; y++) {
			for (int x = 0; x < 2; x++) {
				System.out.print(c[y][x] + " ");
			}
			System.out.println();
		}
	}
}
