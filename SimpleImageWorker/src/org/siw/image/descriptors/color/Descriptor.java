package org.siw.image.descriptors.color;

import java.awt.image.BufferedImage;

public interface Descriptor {
	public double[] execute (int [][] quantized) ;
        public double[] execute(BufferedImage img);
}