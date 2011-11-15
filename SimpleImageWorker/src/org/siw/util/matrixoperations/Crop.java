package org.siw.util.matrixoperations;

public class Crop implements MatrixOperations {

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

	public static void main (String[] args) throws Exception {
		double[][] matrix = new double[][] {
				{1, 2, 3, 4, 5, 6},
				{1, 2, 3, 4, 5, 6},
				{1, 2, 3, 4, 5, 6},
				{1, 2, 3, 4, 5, 6},
				{1, 2, 3, 4, 5, 6},
				{1, 2, 3, 4, 5, 6}
		}; 
		
		MatrixOperations op = new Crop(1, 1, 4, 4);
		matrix = op.execute(matrix);
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
