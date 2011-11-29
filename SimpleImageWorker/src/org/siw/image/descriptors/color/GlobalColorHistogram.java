package org.siw.image.descriptors.color;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class GlobalColorHistogram implements Descriptor {

	@Override
	public double[] execute(int[][] quantized) {
		double[] ret = new double[64];
		int height = quantized.length;
		int width = quantized[0].length;
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				ret[quantized[y][x]]++;
			}
		}
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = ret[i] / ((double) (width * height));
		}
		
		return ret;
	}

        @Override
        public double[] execute(BufferedImage img) {
            return execute(Quantization.get(img));
        }
        
	public static void main(String[] args) throws Exception {
		BufferedImage img = ImageIO.read(new File("testes/link.png"));
		
		Descriptor descriptor = new GlobalColorHistogram();
		double [] colorHistogram = descriptor.execute(Quantization.get(img));
		
		for (double color : colorHistogram)
			System.out.print(color + " ");
                System.out.println();
	}
}
