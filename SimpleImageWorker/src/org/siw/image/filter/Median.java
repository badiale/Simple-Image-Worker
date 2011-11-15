package org.siw.image.filter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Median extends Filter {
	private int size;
	
	public Median (int size) {
		this.size = size;
		this.matrix = new double[size][size];
	}
	
	@Override
	public int[] applyAtPoint(BufferedImage img, int x, int y) {
		int r, g, b;
		int[] vetor = new int[size*size];
		int pivot = vetor.length / 2;
		
		for (int m = 0; m < size; m++) {
			for (int n = 0; n < size; n++) {
				int row = y + m - size/2;
				int col = x + n - size/2;
				int index = m * size + n;
				Color color = new Color(img.getRGB(col, row));
				vetor[index] = color.getRed();
			}
		}
		Arrays.sort(vetor);
		r = vetor[pivot];
		
		for (int m = 0; m < size; m++) {
			for (int n = 0; n < size; n++) {
				int row = y + m - size/2;
				int col = x + n - size/2;
				int index = m * size + n;
				Color color = new Color(img.getRGB(col, row));
				vetor[index] = color.getGreen();
			}
		}
		Arrays.sort(vetor);
		g = vetor[pivot];
		
		for (int m = 0; m < size; m++) {
			for (int n = 0; n < size; n++) {
				int row = y + m - size/2;
				int col = x + n - size/2;
				int index = m * size + n;
				Color color = new Color(img.getRGB(col, row));
				vetor[index] = color.getBlue();
			}
		}
		Arrays.sort(vetor);
		b = vetor[pivot];
		
		return new int[] { r, g, b };
	}
	
	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/mediana.png"));
		
		Filter f = new Median(9);
		f.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
