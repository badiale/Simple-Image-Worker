package org.siw.image.filter;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.siw.geometric.GeometricOperation;
import org.siw.geometric.Padd;
import org.siw.util.ColorUtils;

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
	
	public void save (BufferedImage img, int[][][] colors) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				img.setRGB(x, y, ColorUtils.toInteger(colors[y][x]));
			}
		}
	}
	
	public void execute (BufferedImage img) {
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

	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		double[][] matrix = {
				{-1,-1,-1},
				{-1, 8,-1},
				{-1,-1,-1}
		};
		
		Filter f = new Filter(matrix);
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}

}
