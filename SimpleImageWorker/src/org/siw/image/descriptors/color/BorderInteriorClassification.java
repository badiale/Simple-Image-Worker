package org.siw.image.descriptors.color;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class BorderInteriorClassification implements Descriptor {

	@Override
	public double[] execute(int[][] quantized) {
		double[] ret = new double[64*2];
		int height = quantized.length;
		int width = quantized[0].length;
		
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// armazena pixel atual
				int aux = quantized[i][j];

				if (i > 0 && j > 0 && i == height && j == width) {
					// se o pixel tiver cor igual a dos 4-vizinhos
					if ((aux == quantized[i - 1][j])
							&& (aux == quantized[i + 1][j])
							&& (aux == quantized[i][j - 1])
							&& (aux == quantized[i][j + 1])) {
						// incrementa histograma interior
						ret[aux + 64]++;
					} else {
						// incrementa histograma borda
						ret[aux]++;
					}
				} else {
					// incrementa histograma borda
					ret[aux]++;
				}
			}
		}
		
		for (int i = 0; i < ret.length; i++) {
			ret[i] = ret[i] / ((double) (width * height));
		}
		
		return ret;
	}

	public static void main(String[] args) throws Exception {
		BufferedImage img = ImageIO.read(new File("testes/lena.big.png"));
		
		Descriptor descriptor = new BorderInteriorClassification();
		double [] colorHistogram = descriptor.execute(Quantization.get(img));
		
		for (double color : colorHistogram)
			System.out.print(color + " ");
	}
}
