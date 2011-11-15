package org.siw.image.transformations;

import java.awt.image.BufferedImage;

import org.siw.util.ComplexNumber;


public interface FourierTransform {
	public ComplexNumber[][][] apply  (BufferedImage img);
	public void revert (BufferedImage img, ComplexNumber[][][] matrix);
}
