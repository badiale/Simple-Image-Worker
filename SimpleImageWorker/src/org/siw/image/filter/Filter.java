package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.siw.image.geometric.GeometricOperation;
import org.siw.image.geometric.Padd;
import org.siw.util.ColorUtils;
import org.siw.util.matrixoperations.MatrixOperations;

public class Filter {

	protected double[][] matrix;
	
	public Filter() {}
	public Filter (double[][] matrix) { setMatrix(matrix); }
	
	public double[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(double[][] matrix) {
		this.matrix = matrix;
	}
	
	public int[][][] process (BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		int matrixH = matrix.length;
		int matrixW = matrix[0].length;
		
		int paddSize = 0;
		if (matrixH > matrixW) paddSize = matrixH / 2;
		else paddSize = matrixW / 2;
		
		GeometricOperation mo = new Padd(paddSize);
		BufferedImage padd = mo.execute(img);
		
		// create a new data set
		int[][][] newdata = new int[height][width][3];
		
		// apply the filter
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				newdata[y][x] = applyAtPoint(padd, x + paddSize, y + paddSize);
			}
		}
		
		return newdata;
	}
	
	public double[][][] process (double[][][] img) {
		int width = img[0][0].length;
		int height = img[0].length;
		
		int matrixH = matrix.length;
		int matrixW = matrix[0].length;
		
		int paddSize = 0;
		if (matrixH > matrixW) paddSize = matrixH / 2;
		else paddSize = matrixW / 2;
		
		MatrixOperations mo = new org.siw.util.matrixoperations.Padd(paddSize);
		double[][][] padd = new double[][][] { mo.execute(img[0]), mo.execute(img[1]), mo.execute(img[2]) };
		
		// create a new data set
		double[][][] newdata = new double[3][height][width];
		
		// apply the filter
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double[] colors = applyAtPoint(padd, x + paddSize, y + paddSize);
				for (int i = 0; i < 3; i++) {
					newdata[i][y][x] = colors[i];
				}
			}
		}
		
		return newdata;
	}
	
	public void save (BufferedImage img, int[][][] colors) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				img.setRGB(x, y, ColorUtils.toInteger(colors[y][x]));
			}
		}
	}
	
	public void save (double[][][] img, double[][][] colors) {
		int width = img[0][0].length;
		int height = img[0].length;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				for (int i = 0; i < 3; i++)
					img[i][y][x] = colors[i][y][x];
			}
		}
	}
	
	public void execute (BufferedImage img) {
		save(img, process(img));
	}
	
	public void execute (double[][][] img) {
		save(img, process(img));
	}
	
	public int[] applyAtPoint(BufferedImage img, int x, int y) {
		double[] ret = new double[] {0, 0, 0};
		
		int matrixH = matrix.length;
		int matrixW = matrix[0].length;
		
		
		for (int filterY = 0; filterY < matrixH; filterY++) {
			for (int filterX = 0; filterX < matrixW; filterX++) {
				int imgX = x + filterX - matrixW / 2;
				int imgY = y + filterY - matrixH / 2;
				
				int[] colors = ColorUtils.toArray(img.getRGB(imgX, imgY));
				for (int i = 0; i < ret.length; i++)
					ret[i] += colors[i] * matrix[filterY][filterX];
			}
		}
		
		return new int[] { (int) ret[0], (int) ret[1], (int) ret[2]};
	}
	
	public double[] applyAtPoint(double img[][][], int x, int y) {
		double[] ret = new double[] {0, 0, 0};
		
		int matrixH = matrix.length;
		int matrixW = matrix[0].length;
		
		
		for (int filterY = 0; filterY < matrixH; filterY++) {
			for (int filterX = 0; filterX < matrixW; filterX++) {
				int imgX = x + filterX - matrixW / 2;
				int imgY = y + filterY - matrixH / 2;
				
				for (int i = 0; i < ret.length; i++)
					ret[i] += img[i][imgY][imgX] * matrix[filterY][filterX];
			}
		}
		
		return new double[] { ret[0], ret[1], ret[2]};
	}

	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		double[][] matrix = {
				{0, 0, 0, 0, 0},
				{0,-1,-1,-1, 0},
				{0,-1, 8,-1, 0},
				{0,-1,-1,-1, 0},
				{0, 0, 0, 0, 0}
		};
		
		Filter f = new Filter(matrix);
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}

}
