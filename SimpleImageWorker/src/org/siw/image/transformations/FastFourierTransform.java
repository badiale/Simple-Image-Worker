package org.siw.image.transformations;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.siw.util.ColorUtils;
import org.siw.util.ComplexNumber;

public class FastFourierTransform implements FourierTransform {
	// realiza a fft recursiva
	// saiba mais em http://en.wikipedia.org/wiki/Cooley%E2%80%93Tukey_FFT_algorithm#Pseudocode
	private ComplexNumber[] fft(ComplexNumber[] x) {
		int N = x.length;
		ComplexNumber[] result = new ComplexNumber[N];
		
		if (N == 1) {
			result[0] = new ComplexNumber(x[0]);
		} else {
			ComplexNumber[] array = new ComplexNumber[N / 2];
			
			// FFT de numeros pares
			for (int i = 0; i < N/2; i++) array[i] = new ComplexNumber(x[2*i]);
			ComplexNumber[] par = fft(array);
			
			// FFT de numeros impares
			for (int i = 0; i < N/2; i++) array[i] = new ComplexNumber(x[2*i + 1]);
			ComplexNumber[] impar = fft(array);
			
			// soma os pares e impares na forma: PAR + IMPAR * exponencial
			for (int k = 0; k < N / 2; k++) {
				ComplexNumber twiddle = new ComplexNumber(0, -(2 * Math.PI * k) / N);
				twiddle.exp();
				twiddle.multiply(impar[k]);
				
				// se menor q N/2, soma
				result[k] = ComplexNumber.add(par[k], twiddle);
				
				// se maior q N/2, subtrai
				result[k+N/2] = ComplexNumber.subtract(par[k], twiddle);
			}
		}
		
		return result;
	}
	
	@Override
	public ComplexNumber[][][] apply(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		ComplexNumber[][][] result = new ComplexNumber[3][height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int[] colors = ColorUtils.toArray(img.getRGB(x, y));
				for (int k = 0; k < 3; k++) {
					result[k][y][x] = new ComplexNumber(colors[k], 0); 
				}
			}
		}
		
		ComplexNumber[] array = null;
		
		for (int k = 0; k < 3; k++) {
			// primeiro aplica a fft nas colunas
			for (int x = 0; x < width; x++) {
				array = new ComplexNumber[height];
				for (int y = 0; y < height; y++) {
					array[y] = new ComplexNumber(result[k][y][x]);
				}
				
				array = fft(array);
				
				for (int y = 0; y < height; y++) {
					result[k][y][x] = new ComplexNumber(array[y]);
				}
			}
			
			// segundo aplica a fft nas linhas
			for (int y = 0; y < height; y++) {
				array = new ComplexNumber[width];
				for (int x = 0; x < width; x++) {
					array[x] = result[k][y][x];
				}
				
				array = fft(array);
				
				for (int x = 0; x < width; x++) {
					result[k][y][x] = new ComplexNumber(array[x]);
				}
			}
		}
		
		return result;
	}

	// calcula a inversa da dft, de forma rapida
	// http://www.eetimes.com/design/embedded/4210789/DSP-Tricks--Computing-inverse-FFTs-using-the-forward-FFT
	private ComplexNumber[] ifft(ComplexNumber[] x) {
		ComplexNumber[] result = new ComplexNumber[x.length];
		
		// conjuga todos os numeros da entrada
		for (int i = 0; i < result.length; i++) {
			result[i] = new ComplexNumber(x[i]);
			result[i].conjugate();
		}

		// aplica o fft
		result = fft(result);
		
		// conjuga novamente e divide por N
		for (ComplexNumber r : result) {
			r.conjugate();
			r.divide(x.length);
		}
				
		return result;
	}
	
	@Override
	public void revert(BufferedImage img, ComplexNumber[][][] matrix) {
		int width = img.getWidth();
		int height = img.getHeight();
		
		ComplexNumber[] array = null;
		
		ComplexNumber[][][] entrada = new ComplexNumber[3][height][width];
		for (int k = 0; k < 3; k++) {
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					entrada[k][y][x] = new ComplexNumber(matrix[k][y][x]);
				}
			}
		}
		
		for (int k = 0; k < 3; k++) {
			// primeiro aplica a ifft nas colunas
			for (int x = 0; x < width; x++) {
				array = new ComplexNumber[height];
				for (int y = 0; y < height; y++) {
					array[y] = entrada[k][y][x];
				}
				
				array = ifft(array);
				
				for (int y = 0; y < height; y++) {
					entrada[k][y][x] = new ComplexNumber(array[y]);
				}
			}
			
			// segundo aplica a ifft nas linhas
			for (int y = 0; y < height; y++) {
				array = new ComplexNumber[width];
				for (int x = 0; x < width; x++) {
					array[x] = entrada[k][y][x];
				}
				
				array = ifft(array);
				
				for (int x = 0; x < width; x++) {
					entrada[k][y][x] = new ComplexNumber(array[x]);
				}
			}
		}
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int r = (int) entrada[0][y][x].abs();
				int g = (int) entrada[1][y][x].abs();
				int b = (int) entrada[2][y][x].abs();
				img.setRGB(x, y, ColorUtils.toInteger(r, g, b)); 
			}
		}
	}
	
	public static void main (String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		FourierTransform ft = new FastFourierTransform();
		ComplexNumber[][][] matrix = ft.apply(lena);
		
		ConvolutionFilter f = new ConvolutionGaussian(10, lena.getWidth(), lena.getHeight());
		f.execute(matrix);
		
		ft.revert(lena, matrix);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}
}
