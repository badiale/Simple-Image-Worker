package org.siw.image.descriptors.local;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.siw.gui.DoublePanel;
import org.siw.gui.ImagePanel;
import org.siw.image.filter.DerivateX;
import org.siw.image.filter.DerivateY;
import org.siw.image.filter.Filter;
import org.siw.image.filter.Gaussian;
import org.siw.image.filter.Mean;
import org.siw.image.geometric.Crop;
import org.siw.util.ImageToDouble;
import org.siw.util.matrixoperations.Convolute;
import org.siw.util.matrixoperations.Subtract;
import org.siw.util.matrixoperations.Sum;

public class Harris {

    private double sigma;
    private double alfa;
    private int size;
    private int imagesSize;
    private boolean showResults;

    public Harris(double sigma, double alfa, int size, int imagesSize) {
        this.sigma = sigma;
        this.alfa = alfa;
        this.size = size;
        this.imagesSize = imagesSize;
        this.showResults = false;
    }

    public Harris(double sigma, double alfa, int size, int imagesSize, boolean showResults) {
        this(sigma, alfa, size, imagesSize);
        this.showResults = showResults;
    }

    public void showResult(String description, double[][][] matrix) {
        if (this.showResults) {
            JFrame jf = new JFrame(description);
            jf.add(new DoublePanel(matrix, 2));
            jf.setResizable(false);
            jf.pack();
            jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jf.setVisible(true);
        }
    }

    // Compute as respostas passa-altas X = I2x(x), Y = I2y (x) e Z = IxIy(x)
    // Gere um filtro gaussiano 5 x 5 com sigma = 1.44 e obtenha as versões filtradas de X, Y e Z, chamadas A, B e C.
    // Calcule Harris = [ AB - C^2] - alfa (A + B) ^ 2
    public double[][][] getHarris(double[][][] matriximg) {
        int width = matriximg[0][0].length;
        int height = matriximg[0].length;

        double[][][] Ix = new DerivateX().process(matriximg);
        double[][][] Iy = new DerivateY().process(matriximg);
        showResult("Ix", Ix);
        showResult("Iy", Iy);

        // Compute as respostas passa-altas X = I2x(x), Y = I2y (x) e Z = IxIy(x)
        double[][][] Ix2 = new double[3][height][width];
        double[][][] Iy2 = new double[3][height][width];
        double[][][] IxIy = new double[3][height][width];
        for (int i = 0; i < 3; i++) {
            Ix2[i] = new Convolute().execute(Ix[i], Ix[i]);
            Iy2[i] = new Convolute().execute(Iy[i], Iy[i]);
            IxIy[i] = new Convolute().execute(Ix[i], Iy[i]);
        }

        showResult("Ix2", Ix2);
        showResult("Iy2", Iy2);
        showResult("IxIy", IxIy);

        // Gere um filtro gaussiano 5 x 5 com sigma = 1.44 e obtenha as versões filtradas de X, Y e Z, chamadas A, B e C.
        Filter gaussian = new Gaussian(5, 5, sigma);
        double[][][] A = gaussian.process(Ix2);
        double[][][] B = gaussian.process(Iy2);
        double[][][] C = gaussian.process(IxIy);
        showResult("A", A);
        showResult("B", B);
        showResult("C", C);

        //Harris = (A * B - C * C) - alfa * (A + B) * (A + B);
        double[][][] Harris = new double[3][height][width];
        for (int i = 0; i < 3; i++) {
            double[][] AB = new Convolute().execute(A[i], B[i]);
            double[][] CC = new Convolute().execute(C[i], C[i]);
            double[][] AB_CC = new Subtract().execute(AB, CC);

            double[][] AplusB = new Sum().execute(A[i], B[i]);
            double[][] AplusB2 = new Convolute().execute(AplusB, AplusB);
            double[][] alfaAB2 = new Convolute().execute(AplusB2, alfa);

            Harris[i] = new Subtract().execute(AB_CC, alfaAB2);
        }
        showResult("Harris", Harris);

        return Harris;
    }

    // Encontre os máximos locais de resposta alta, para isso:
    //   a) Remova as respostas próximas a 5 pixels da borda da imagem.
    //   b) Encontre os máximos locais, pontos cuja valor seja máximo numa vizinhança k x k: esses passarão a ser os pontos candidatos, e armazene esses pontos numa matriz PC.
    //   c) Filtre a resposta Harris usando um filtro de média de tamanho kxk, obtendo Hmean.
    //   d) Calcule o desvio padrão de Harris ou seja: sigmaH = std(Harris) e o desvio padrão local em cada ponto por meio de Hstd = Harris - Hmean
    //   e) Remova de PC(x; y) os pontos (x; y) para os quais Hstd(x; y) < (sigmaH/2).
    public double[][][] getLocal(double[][][] Harris) {
        int width = Harris[0][0].length;
        int height = Harris[0].length;

        // a) Remova as respostas próximas a 5 pixels da borda da imagem.
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (!(j >= 5 && j < width - 5) && (i >= 5 && i < height - 5)) {
                        Harris[k][i][j] = 0.0;
                    }
                }
            }
        }
        showResult("Harris - Sem bordas", Harris);

        // b) Encontre os máximos locais, pontos cuja valor seja máximo numa vizinhança k x k: esses passarão a ser os pontos candidatos, e armazene esses pontos numa matriz PC.
        double[][][] PC = new double[3][height][width];
        for (int k = 0; k < 3; k++) {
            for (int i = 5; i < height - 5; i++) {
                for (int j = 5; j < width - 5; j++) {
                    double max = Harris[k][i][j];
                    for (int filterY = 0; filterY < size; filterY++) {
                        for (int filterX = 0; filterX < size; filterX++) {
                            int imgX = j + filterX - size / 2;
                            int imgY = i + filterY - size / 2;

                            if (((imgX >= 0 && imgX < width) && (imgY >= 0 && imgY < height)) && max < Harris[k][imgY][imgX]) {
                                max = Harris[k][imgY][imgX];
                            }
                        }
                    }
                    if (max == Harris[k][i][j]) {
                        PC[k][i][j] = 255;
                    } else {
                        PC[k][i][j] = 0;
                    }
                }
            }
        }
        showResult("PC", PC);

        // c) Filtre a resposta Harris usando um filtro de média de tamanho kxk, obtendo Hmean.
        double[][][] Hmean = new Mean(size).process(Harris);
        showResult("Hmean", Hmean);

        // d) Calcule o desvio padrão de Harris ou seja: sigmaH = std(Harris)
        double[] sigmaH = new double[]{0, 0, 0};
        for (int k = 0; k < 3; k++) {
            double sum = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    sum += Harris[k][i][j];
                }
            }
            double mean = sum / (width * height);

            sum = 0;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    sum += Math.pow(Harris[k][i][j] - mean, 2.0);
                }
            }
            sigmaH[k] = Math.sqrt(sum / (width * height - 1));
        }
        //System.out.println("sigmaH: " + sigmaH[0] + " " + sigmaH[1] + " " + sigmaH[2]);

        // e o desvio padrão local em cada ponto por meio de Hstd = Harris - Hmean
        double[][][] Hstd = new double[3][height][width];
        for (int i = 0; i < 3; i++) {
            Hstd[i] = new Subtract().execute(Harris[i], Hmean[i]);
        }
        showResult("Hstd", Hstd);

        // e) Remova de PC(x, y) os pontos (x, y) para os quais Hstd(x, y) < (sigmaH/2).
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (Hstd[k][i][j] < (sigmaH[k] / 2)) {
                        PC[k][i][j] = 0.0;
                    }
                }
            }
        }
        showResult("PC - pontos removidos", PC);

        return PC;
    }

    // Procedimento para executar o método de Harris
    public List<BufferedImage> execute(BufferedImage img) {
        double[][][] matriximg = ImageToDouble.convert(img);
        showResult("matriximg", matriximg);

        double[][][] Harris = getHarris(matriximg);
        double[][][] PC = getLocal(Harris);

        // Grave no disco uma nova imagem utilizando a imagem original como base, mas que possua cor vermelha nos pontos chave detectados.
        // fiz um quadrado, pois pixels vermelhos sao dificeis de enxergar
        List<BufferedImage> list = new LinkedList<BufferedImage>();
        Crop c = new Crop(0, 0, imagesSize, imagesSize);
        for (int k = 0; k < 3; k++) {
            for (int i = 0; i < img.getHeight(); i++) {
                for (int j = 0; j < img.getWidth(); j++) {
                    if (PC[k][i][j] > 0) {
                        c.setX(j - imagesSize);
                        c.setY(i - imagesSize);
                        list.add(c.execute(img));
                    }
                }
            }
        }
        return list;
    }

    public static void main(String[] args) throws Exception {
        BufferedImage cameraman = ImageIO.read(new File("testes/cameraman.png"));
        
        Harris h = new Harris(1.44, 0.04, 5, 30, true);
        List<BufferedImage> list = h.execute(cameraman);
        
        JFrame jf = new JFrame();
        jf.setLayout(new GridLayout(10, 10, 10, 10));
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        for (BufferedImage img : list) {
            jf.add(new ImagePanel(img));
        }
        jf.pack();
        jf.setVisible(true);
    }
}
