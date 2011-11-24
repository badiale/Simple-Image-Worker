package org.siw.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.siw.util.ColorUtils;

public class DoublePanel extends JPanel {
	private static final long serialVersionUID = -1019972655365956292L;

	private double[][][] matrix;
	
	private int imgType;
	
	public DoublePanel (double[][][] matrix, int imgType) {
		this.matrix = matrix;
		this.imgType = imgType;
		
		setPreferredSize(new Dimension(this.getMatrixWidth(), this.getMatrixHeight()));
	}
	
	@Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
    	BufferedImage img = new BufferedImage(getMatrixWidth(), getMatrixHeight(), 2);
    	
    	double[][] red = new double[getMatrixHeight()][getMatrixWidth()];
    	double[][] green = new double[getMatrixHeight()][getMatrixWidth()];
    	double[][] blue = new double[getMatrixHeight()][getMatrixWidth()];
    	double oldmin = 999999999, oldmax = -99999999;
    	for (int y = 0; y < getMatrixHeight(); y++) {
    		for (int x = 0; x < getMatrixWidth(); x++) {
				red[y][x] = (int) matrix[0][y][x];
				green[y][x] = (int) matrix[1][y][x];
				blue[y][x] = (int) matrix[2][y][x];
    			
    			if (oldmin > red[y][x])   oldmin = red[y][x];
    			if (oldmin > green[y][x]) oldmin = green[y][x];
    			if (oldmin > blue[y][x])  oldmin = blue[y][x];
				
				if (oldmax < red[y][x])   oldmax = red[y][x];
				if (oldmax < green[y][x]) oldmax = green[y][x];
				if (oldmax < blue[y][x])  oldmax = blue[y][x];
    		}
    	}
    	
    	for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				red[y][x] = (((red[y][x] - oldmin) * (255)) / (oldmax - oldmin));
				green[y][x] = (((green[y][x] - oldmin) * (255)) / (oldmax - oldmin));
				blue[y][x] = (((blue[y][x] - oldmin) * (255)) / (oldmax - oldmin));
				
				img.setRGB(x, y, ColorUtils.toInteger((int) red[y][x], (int) green[y][x], (int) blue[y][x]));
			}
    	
    	g.drawImage(img, 0, 0, null);
    }

	public int getMatrixWidth() {
		return matrix[0][0].length;
	}

	public int getMatrixHeight() {
		return matrix[0].length;
	}
	
	public int getPixelSize() {
		return matrix.length;
	}

	public double[][][] getMatrix() {
		return matrix;
	}

	public int getImageType () {
		return imgType;
	}
	
	public void setImageType(int imgType) {
		this.imgType = imgType; 
	}
	
	public void setMatrix(double[][][] matrix) {
		this.matrix = matrix;
	}

}
