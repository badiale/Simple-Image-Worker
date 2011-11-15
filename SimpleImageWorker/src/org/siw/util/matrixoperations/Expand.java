package org.siw.util.matrixoperations;


public class Expand implements MatrixOperations {
	
	private int width;
	private int height;
	
	public Expand(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}
	
	@Override
	public double[][] execute(double[][] matrix) {
		int matrixH = matrix.length;
		int matrixW = matrix[0].length;
		
		double[][] resize = new double[height][width];
		
		// copies the original image into the padded area
		for (int y = 0; y < matrixH; y++)
			for (int x = 0; x < matrixW; x++) {
				int centerY = (height - matrixH) / 2;
				int centerX = (width - matrixW) / 2;
				resize[centerY + y][centerX + x] = matrix[y][x];
			}
		
		return resize;
	}

	public static void main (String[] args) throws Exception {
		double[][] matrix = new double[][] {
				{1, 2, 3},
				{1, 2, 3},
				{1, 2, 3}
		}; 
		
		MatrixOperations op = new Expand(9, 9);
		matrix = op.execute(matrix);
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++){
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
