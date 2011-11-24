package org.siw.util.matrixoperations;

import org.siw.util.ComplexNumber;

public class Multiply implements BinaryMatrixOperations {
	
	@Override
	public double[][] execute(double[][] a, double[][] b) {
		if (b.length != a[0].length) throw new RuntimeException("Impossivel multiplicar as matrizes.");
			
		int height = a.length;
		int width = b[0].length;
		
		double[][] c = new double[height][width];
		
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				double sum = 0;
				for (int k = 0; k < b.length; k++) 
					sum += a[i][k] * b[k][j];
				c[i][j] = sum;
			}
		
		return c;
	}
	
	@Override
	public ComplexNumber[][] execute(ComplexNumber[][] a, ComplexNumber[][] b) {
		if (b.length != a[0].length) throw new RuntimeException("Impossivel multiplicar as matrizes.");
		
		int height = a.length;
		int width = b[0].length;
		
		ComplexNumber[][] c = new ComplexNumber[height][width];
		
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				ComplexNumber sum = new ComplexNumber();
				for (int k = 0; k < b.length; k++) 
					sum.add(ComplexNumber.multiply(a[i][k], b[k][j]));
				c[i][j] = sum;
			}
		
		return c;
	}
	
	@Override
	public double[][] execute (double[][] a, double scalar) {
		int height = a.length;
		int width = a[0].length;
		
		double[][] c = new double[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				c[y][x] = a[y][x] * scalar;
		
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
				c[y][x].multiply(scalar);
			}
		
		return c;
	}
	
	public static void main(String[] args) {
		double[][] a = new double[][] {
				{14, 9, 3},
				{2, 11, 15},
				{0, 12, 17},
				{5, 2, 3}
		};
		double[][] b = new double[][] {
				{12, 25},
				{9, 10},
				{8, 5}
		};
		
		BinaryMatrixOperations op = new Multiply();
		double[][] c = op.execute(a, b);
		
		int height = c.length;
		int width = c[0].length;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				System.out.print(c[i][j] + " ");
			}
			System.out.println();
		}
	}

}
