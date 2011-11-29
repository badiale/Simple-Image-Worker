package org.siw.util;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.siw.image.descriptors.color.ColorAutocorrelagram;
import org.siw.image.descriptors.color.Descriptor;

public class Chessboard implements Distance {

    @Override
    public double calculate(double[] descriptorA, double[] descriptorB) {
        double dist = -1;

        if (descriptorA.length != descriptorB.length) {
            throw new RuntimeException("Dimensoes dos descritores diferentes.");
        }

        for (int i = 0; i < descriptorA.length; i++) {
            dist = Math.max(dist, Math.abs(descriptorA[i] - descriptorB[i]));
        }

        return dist;
    }

    public static void main(String[] args) throws Exception {
        BufferedImage img1 = ImageIO.read(new File("testes/link.png"));
        BufferedImage img2 = ImageIO.read(new File("testes/link2.png"));
        BufferedImage img3 = ImageIO.read(new File("testes/lena.big.png"));
        
        Descriptor desc = new ColorAutocorrelagram(3);
        double[] desc1 = desc.execute(img1);
        double[] desc2 = desc.execute(img2);
        double[] desc3 = desc.execute(img3);
        
        Distance chess = new Chessboard();
        System.out.println(chess.calculate(desc1, desc2));
        System.out.println(chess.calculate(desc2, desc3));
        System.out.println(chess.calculate(desc1, desc3));
    }
}
