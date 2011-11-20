package org.siw.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import org.siw.util.ColorUtils;
import org.siw.util.ComplexNumber;
import org.siw.util.matrixoperations.MatrixShift;

public class ComplexPanel extends JPanel {
	private static final long serialVersionUID = -1019972655365956292L;

	private ComplexNumber[][][] matrix;
	
	private int imgType;
	private int drawType;
	
	public final static int DRAW_ABS      = 0;
	public final static int DRAW_ABS_LOG  = 1;
	public final static int DRAW_REAL     = 2;
	public final static int DRAW_REAL_LOG = 3;
	public final static int DRAW_IMG      = 4;
	public final static int DRAW_IMG_LOG  = 5;
	
	public ComplexPanel (ComplexNumber[][][] matrix, int imgType) {
		this.matrix = matrix;
		this.imgType = imgType;
		this.drawType = DRAW_ABS;
		
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
    			switch (drawType) {
    				default:
    				case DRAW_ABS:
    				case DRAW_ABS_LOG:
    					red[y][x] = (int) matrix[0][y][x].abs();
    					green[y][x] = (int) matrix[1][y][x].abs();
    					blue[y][x] = (int) matrix[2][y][x].abs();
    					break;
    				case DRAW_REAL:
    				case DRAW_REAL_LOG:
    					red[y][x] = (int) matrix[0][y][x].getReal();
    					green[y][x] = (int) matrix[1][y][x].getReal();
    					blue[y][x] = (int) matrix[2][y][x].getReal();
    					break;
    				case DRAW_IMG:
    				case DRAW_IMG_LOG:
    					red[y][x] = (int) matrix[0][y][x].getImaginary();
    					green[y][x] = (int) matrix[1][y][x].getImaginary();
    					blue[y][x] = (int) matrix[2][y][x].getImaginary();
    					break;
    			}
    			
    			if (oldmin > red[y][x])   oldmin = red[y][x];
    			if (oldmin > green[y][x])   oldmin = green[y][x];
    			if (oldmin > blue[y][x])   oldmin = blue[y][x];
				
				if (oldmax < red[y][x])   oldmax = red[y][x];
				if (oldmax < green[y][x])   oldmax = green[y][x];
				if (oldmax < blue[y][x])   oldmax = blue[y][x];
    		}
    	}
    	
    	MatrixShift.execute(red);
    	MatrixShift.execute(green);
    	MatrixShift.execute(blue);
    	
    	double c = 300;
    	
    	for (int y = 0; y < img.getHeight(); y++)
			for (int x = 0; x < img.getWidth(); x++) {
				red[y][x] = (((red[y][x] - oldmin) * (255)) / (oldmax - oldmin));
				green[y][x] = (((green[y][x] - oldmin) * (255)) / (oldmax - oldmin));
				blue[y][x] = (((blue[y][x] - oldmin) * (255)) / (oldmax - oldmin));
				
				switch (drawType) {
					case DRAW_ABS_LOG:
					case DRAW_REAL_LOG:
					case DRAW_IMG_LOG:
						red[y][x] = c * Math.log(1.0 + red[y][x]);
						green[y][x] = c * Math.log(1.0 + green[y][x]);
						blue[y][x] = c * Math.log(1.0 + blue[y][x]);
					break;
				}
				
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

	public ComplexNumber[][][] getMatrix() {
		return matrix;
	}

	public int getDrawType() {
		return drawType;
	}

	public int getImageType () {
		return imgType;
	}
	
	public void setImageType(int imgType) {
		this.imgType = imgType; 
	}
	
	public void setMatrix(ComplexNumber[][][] matrix) {
		this.matrix = matrix;
	}

	public void setDrawType(int drawType) {
		this.drawType = drawType;
		repaint();
	}
	
}
