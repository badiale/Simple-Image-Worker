package org.siw.image.descriptors.color;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ColorAutocorrelagram implements Descriptor {

    public int k;

    public ColorAutocorrelagram(int k) {
        this.k = k;
    }

    @Override
    public double[] execute(int[][] quantized) {
        double[] ret = new double[64];
        int height = quantized.length;
        int width = quantized[0].length;

        double[] GCH = new double[64];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int coreColor = quantized[y][x];
                GCH[coreColor]++;
                for (int j = Math.max(0, y - k); j < Math.min(y + k, height); j++) {
                    for (int i = Math.max(0, x - k); i < Math.min(x + k, width); i++) {
                        int kernelColor = quantized[j][i];
                        // Chebyshev Distance Check
                        if (Math.max(Math.abs(x - i), Math.abs(y - j)) == k && coreColor == kernelColor) {
                            ret[coreColor]++;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < ret.length; i++) {
            if (GCH[i] != 0)
                ret[i] /= 8*k*GCH[i];
                // 8*k = number of analyzed neighbours for one pixel
        }

        return ret;
    }

    @Override
    public double[] execute(BufferedImage img) {
        return execute(Quantization.get(img));
    }
    
    public static void main(String[] args) throws Exception {
        BufferedImage img = ImageIO.read(new File("testes/link.png"));

        Descriptor descriptor = new ColorAutocorrelagram(3);
        double[] colorHistogram = descriptor.execute(Quantization.get(img));

        for (double color : colorHistogram) {
            System.out.print(color + " ");
        }
        System.out.println();
    }
}
