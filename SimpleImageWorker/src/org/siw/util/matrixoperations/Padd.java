package org.siw.util.matrixoperations;

public class Padd implements MatrixOperations {
	
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
	
	public static void main (String[] args) throws Exception {
		double[][] matrix = new double[][] {
				{1, 2, 3},
				{1, 2, 3},
				{1, 2, 3}
		}; 
		
		MatrixOperations op = new Padd(3);
		matrix = op.execute(matrix);
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
