package org.siw.image;

import java.util.Arrays;

import javax.swing.text.html.MinimalHTMLWriter;

import org.siw.util.ComplexNumber;

public class Image {
	private int height;
	private int width;
	private Pixel[][] data;

	public Image() {
		setHeight(0);
		setWidth(0);
		data = null;
	}

	public Image (int width, int height) {
		setWidth(width);
		setHeight(height);
		
		data = new Pixel[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				data[i][j] = new Pixel();
			}
		}
	}

	public void setWidth  (int  width ) { this.width  = width ; }
	public void setHeight (int  height) { this.height = height; }
	public void setPixel (int x, int y, Pixel p) { data[x][y] = p; }
	public void setData (Pixel[][] data) {
		// apply the result
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.data[i][j].setAbsoluteColor(data[i][j].getColor());
			}
		}
	}
	
	public int  getWidth  () { return this.width ; }
	public int  getHeight () { return this.height; }
	public Pixel getPixel (int x, int y) { return data[y][x]; }
	public Pixel[][] getData () { return this.data; }
	
	
	// salva as cores absolutas
	public void absoluteColors() {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				data[i][j].setAbsoluteColor(data[i][j].getColor());
			}
		}
	}
	
	// operators
	
	// muda o brilho
	public void brilho (int brilho) {
		for (int y = 0; y < this.height; y++)
			for (int x = 0; x < this.width; x++)
				this.getPixel(x, y).add(brilho);
	}

	// muda o contraste
	public void contrast (int min, int max) {
		int oldmin, oldmax;
		oldmin = oldmax = this.getPixel(0,0).getColor();

		for (int y = 0; y < this.height; y++)
			for (int x = 0; x < this.width; x++) {
				int color = getPixel(x,y).getColor();
				if (oldmin > color) oldmin = color;
				if (oldmax < color) oldmax = color;
			}

		for (int y = 0; y < this.height; y++)
			for (int x = 0; x < this.width; x++)
				this.getPixel(x, y).setAbsoluteColor(min + (((getPixel(x,y).getColor() - oldmin) * (max - min)) / (oldmax - oldmin)));
	}

	// escala log
	public void log (double c) {
		for (int y = 0; y < this.height; y++)
			for (int x = 0; x < this.width; x++)
				this.getPixel(x, y).log(c);
	}
	
	// escala exp
	public void exp (double gamma) {
		for (int y = 0; y < this.height; y++)
			for (int x = 0; x < this.width; x++)
				this.getPixel(x, y).exp(gamma);
	}

	// usa o filtro, retornando o resultado
	public Pixel[][] processFilter (double[][] matrix) {
		int matrixH = matrix.length;
		int matrixW = matrix[0].length;
		
		// padd the original image
		int paddYMin = matrixH / 2;
		int paddXMin = matrixW / 2;
		int paddH = this.height + matrixH;
		int paddW = this.width + matrixW;
		double[][] padd = new double[paddH][paddW];
		
		// copies the original image into the padded area
		for (int y = 0; y < this.height; y++)
			for (int x = 0; x < this.width; x++)
				padd[paddYMin + y][paddXMin + x] = data[y][x].getColor();
		
		// create a new data set
		int[][] newdata = new int[this.height][this.width];
		
		// apply the filter
		for (int y = paddYMin; y < paddYMin + this.height; y++) {
			for (int x = paddXMin; x < paddXMin + this.width; x++) {
				double newValue = 0.0;
				for (int filterY = 0; filterY < matrixH; filterY++) {
					for (int filterX = 0; filterX < matrixW; filterX++) {
						int paddY = y + filterY - matrixH / 2;
						int paddX = x + filterX - matrixW / 2;
						newValue += matrix[filterY][filterX] * padd[paddY][paddX];
					}
				}
				newdata[y - paddYMin][x - paddXMin] = (int) newValue;
			}
		}
		
		Pixel[][] retorno = new Pixel[this.height][this.width];
		for (int y = 0; y < this.height; y++)
			for (int x = 0; x < this.width; x++) {
				retorno[y][x] = new Pixel();
				retorno[y][x].setColor(newdata[y][x]);
			}
				
		return retorno;
	}
	
	// usa um filtro, modificando a imagem
	public void applyFilter (double[][] matrix) {
		Pixel[][] newdata = processFilter(matrix);
		setData(newdata);
	}

	// binariza a imagem
	public void binarizacao (int treshold) {
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				if (this.data[i][j].getColor() > treshold) this.data[i][j].setAbsoluteColor(256);
				else this.data[i][j].setAbsoluteColor(0);
			}
		}
	}
	
	// aplica o filtro gaussiano
	public void gaussian (double sigma) {
		int size = (int) (6 * sigma);
		if (size % 2 == 0) size++;

		double[][] matrix = new double[size][size];
		
		double sqrSigma = 2 * sigma*sigma;
		
		double sum = 0.0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				int x = (-size/2)+i;
				int y = (-size/2)+j;
				matrix[i][j] = Math.exp(-((x*x) + (y*y)) / (sqrSigma));
				
				sum += matrix[i][j];
			}
		}
		
		// normaliza o filtro
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] /= sum;
			}
		}
		
		applyFilter(matrix);
	}
	
	// aplica o filtro "laplace of gaussian"
	// formula encontrada em " http://homepages.inf.ed.ac.uk/rbf/HIPR2/log.htm "
	public void laplaceOfGaussian (double sigma) {
		int size = (int) (6 * sigma);
		if (size % 2 == 0) size++;

		double[][] matrix = new double[size][size];
		
		double sqrSigma = 2 * sigma*sigma;
		double cte = (1 / (Math.PI * Math.pow(sigma, 4)));
		
		for (int j = 0; j < size; j++) {
			for (int i = 0; i < size; i++) {
				// LoG
				int x = (-size/2)+i;
				int y = (-size/2)+j;
				
				double exp = -((x*x) + (y*y)) / (sqrSigma);
				matrix[i][j] = - (cte) * (1 + exp) * Math.exp(exp);
			}
		}
		
		Pixel[][] data = processFilter(matrix);
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.data[i][j].setColor(data[i][j].getColor());
			}
		}
	}
	
	// efetua o zero crossing
	public void zeroCrossing() {
		Pixel[][] newdata = new Pixel[this.height][this.width];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				newdata[i][j] = new Pixel();
			}
		}
		
		// procura por zero crossing
		for (int i = 1; i < this.height - 1; i++) {
			for (int j = 1; j < this.width - 1; j++) {
				if ((data[i - 1][j - 1].getColor() < 0 && data[i + 1][j + 1].getColor() > 0) || (data[i - 1][j - 1].getColor() > 0 && data[i + 1][j + 1].getColor() < 0)) { newdata[i][j].setAbsoluteColor(255); }
				else if ((data[i - 1][j + 1].getColor() < 0 && data[i + 1][j - 1].getColor() > 0) || (data[i - 1][j + 1].getColor() > 0 && data[i + 1][j - 1].getColor() < 0)) { newdata[i][j].setAbsoluteColor(255); }
				else if ((data[i - 1][j + 0].getColor() < 0 && data[i + 1][j - 0].getColor() > 0) || (data[i - 1][j + 0].getColor() > 0 && data[i + 1][j - 0].getColor() < 0)) { newdata[i][j].setAbsoluteColor(255); }
				else if ((data[i - 0][j - 1].getColor() < 0 && data[i + 0][j + 1].getColor() > 0) || (data[i - 0][j - 1].getColor() > 0 && data[i + 0][j + 1].getColor() < 0)) { newdata[i][j].setAbsoluteColor(255); }
			}
		}
		
		setData(newdata);
	}
	
	// aplica o filtro de media
	public void media (int size) {
		double[][] matrix = new double[size][size];
		double sqrSize = size*size;

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = 1 / sqrSize;
			}
		}
		
		applyFilter(matrix);
	}

	// utiliza a mediana para suavizar a imagem
	public void median (int size) {
		// create a new data set
		Pixel[][] newdata = new Pixel[this.height][this.width];
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				newdata[i][j] = new Pixel();
			}
		}
		
		// aplica a mediana
		int[] vetor = new int[size*size];
		int pivot = vetor.length / 2;
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				for (int m = 0; m < size; m++) {
					for (int n = 0; n < size; n++) {
						int r = i + m - size/2;
						int c = j + n - size/2;
						int index = m * size + n;
						if (c < 0 || c > this.width - 1 || r < 0 || r > this.height - 1) { vetor[index] = 127; }
						else vetor[index] = this.data[r][c].getColor();
					}
				}
				Arrays.sort(vetor);
				newdata[i][j].setAbsoluteColor(vetor[pivot]);
			}
		}
		
		// copy the result
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.data[i][j].setAbsoluteColor(newdata[i][j].getColor());
			}
		}
	}
	
	// calcula a transformada discreta de fourier no ponto (u,v)
	public ComplexNumber dft(int u, int v) {
		double real = 0;
		double imaginary = 0;
		
		double uW = (double) u / this.width;
		double vH = (double) v / this.height;
		
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				double uxW = (double) x * uW;
				double vyH = (double) y * vH;
				
				double color = (double) this.data[y][x].getColor();
				double exp = -2 * Math.PI * (uxW + vyH);
				
				real += color * Math.cos(exp);
				imaginary += color * Math.sin(exp);
			}
		}
		
		return new ComplexNumber(real, imaginary);
	}
	
	// calcula a inversa da transformada discreta de fourier no ponto (x, y)
	public double idft(ComplexNumber[][] F, int x, int y) {
		ComplexNumber result = new ComplexNumber();
		
		double xW = (double) x / this.width;
		double yH = (double) y / this.height;
		
		for (int v = 0; v < this.height; v++) {
			for (int u = 0; u < this.width; u++) {
				double uxW = (double) u * xW;
				double vyH = (double) v * yH;
				
				ComplexNumber exp = new ComplexNumber(0, 2 * Math.PI * (uxW + vyH));
				exp.exp();
				exp.multiply(F[v][u]);
				result.add(exp);
			}
		}
		double division = this.width * this.height;
		result.divide(division);

		return result.abs();
	}
	
	// calcula o DFT da imagem toda
	public void dft() {
		ComplexNumber[][] result = new ComplexNumber[this.height][this.width];
		
		System.out.println();
		for (int v = 0; v < this.height; v++) {
			System.out.printf("[%3d]\n", v);
			for (int u = 0; u < this.width; u++) {
				result[v][u] = dft(u,v);
			}
		}
		
		for (int y = 0; y < this.height; y++) {
			System.out.printf("[%3d]\n", y);
			for (int x = 0; x < this.width; x++) {
				this.data[y][x].setAbsoluteColor((int) idft(result, x,y));
			}
		}
		
		/*// copy the result
		for (int i = 0; i < this.height; i++) {
			for (int j = 0; j < this.width; j++) {
				this.data[i][j].setAbsoluteColor((int) result[i][j].abs());
			}
		}*/
	}
	
	// realiza a fft recursiva
	// saiba mais em http://en.wikipedia.org/wiki/Cooley%E2%80%93Tukey_FFT_algorithm#Pseudocode
	//	X0,...,N−1 ← ditfft2(x, N, s):             DFT of (x0, xs, x2s, ..., x(N-1)s):
	//    if N = 1 then
	//        X0 ← x0                                      trivial size-1 DFT base case
	//    else
	//        X0,...,N/2−1 ← ditfft2(x, N/2, 2s)             DFT of (x0, x2s, x4s, ...)
	//        XN/2,...,N−1 ← ditfft2(x+s, N/2, 2s)           DFT of (xs, xs+2s, xs+4s, ...)
	//        for k = 0 to N/2−1                           combine DFTs of two halves into full DFT:
	//            t ← Xk
	//            Xk ← t + exp(−2πi k/N) Xk+N/2
	//            Xk+N/2 ← t − exp(−2πi k/N) Xk+N/2
	//        endfor
	//    endif
	public ComplexNumber[] fft(ComplexNumber[] x) {
		int N = x.length;
		ComplexNumber[] result = new ComplexNumber[N];
		
		if (N == 1) {
			result[0] = new ComplexNumber(x[0]);
		} else {
			ComplexNumber[] array = new ComplexNumber[N / 2];
			
			for (int i = 0; i < N/2; i++) array[i] = new ComplexNumber(x[2*i]);
			ComplexNumber[] par = fft(array);
			
			for (int i = 0; i < N/2; i++) array[i] = new ComplexNumber(x[2*i + 1]);
			ComplexNumber[] impar = fft(array);
			
			for (int k = 0; k < N / 2; k++) {
				ComplexNumber twiddle = new ComplexNumber(0, -(2 * Math.PI * k) / N);
				twiddle.exp();
				twiddle.multiply(impar[k]);
				result[k] = ComplexNumber.add(par[k], twiddle);
				result[k+N/2] = ComplexNumber.subtract(par[k], twiddle);
			}
		}
		
		return result;
		
		/*ComplexNumber z1, z2, z3, z4, tmp, cTwo;
		int n = x.length;
		int m = n / 2;
		ComplexNumber[] result = new ComplexNumber[n];
		ComplexNumber[] even = new ComplexNumber[m];
		ComplexNumber[] odd = new ComplexNumber[m];
		ComplexNumber[] sum = new ComplexNumber[m];
		ComplexNumber[] diff = new ComplexNumber[m];
		cTwo = new ComplexNumber(2, 0);
		if (n == 1) {
			result[0] = x[0];
		} else {
			z1 = new ComplexNumber(0.0, -2 * (Math.PI) / n);
			tmp = ComplexNumber.exp(z1);
			z1 = new ComplexNumber(1.0, 0.0);
			for (int i = 0; i < m; ++i) {
				z3 = ComplexNumber.add(x[i], x[i + m]);
				sum[i] = ComplexNumber.divide(z3, cTwo);

				z3 = ComplexNumber.subtract(x[i], x[i + m]);
				z4 = ComplexNumber.multiply(z3, z1);
				diff[i] = ComplexNumber.divide(z4, cTwo);

				z2 = ComplexNumber.multiply(z1, tmp);
				z1 = new ComplexNumber(z2);
			}
			even = fft(sum);
			odd = fft(diff);

			for (int i = 0; i < m; ++i) {
				result[i * 2] = new ComplexNumber(even[i]);
				result[i * 2 + 1] = new ComplexNumber(odd[i]);
			}
		}
		return result;*/
	}
	
	// aplica o fft recursivo
	public void fft () {
		ComplexNumber[][] result = new ComplexNumber[height][width];
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				result[y][x] = new ComplexNumber(this.data[y][x].getColor(), 0);
		
		ComplexNumber[] array = null;
		
		// primeiro aplica a fft nas colunas
		for (int x = 0; x < width; x++) {
			array = new ComplexNumber[height];
			for (int y = 0; y < height; y++) {
				array[y] = result[y][x];
			}
			
			array = fft(array);
			
			for (int y = 0; y < height; y++) {
				result[y][x] = array[y];
			}
		}
		
		// segundo aplica a fft nas linhas
		for (int y = 0; y < height; y++) {
			array = new ComplexNumber[width];
			for (int x = 0; x < width; x++) {
				array[x] = result[y][x];
			}
			
			array = fft(array);
			
			for (int x = 0; x < width; x++) {
				result[y][x] = array[x];
			}
		}
		
		for (int y = 0; y < this.height; y++) {
			System.out.printf("[%3d]\n", y);
			for (int x = 0; x < this.width; x++) {
				this.data[y][x].setAbsoluteColor((int) idft(result, x,y));
			}
		}
		
//		// por fim, salva o resultado
//		for (int i = 0; i < this.height; i++) {
//			for (int j = 0; j < this.width; j++) {
//				this.data[i][j].setAbsoluteColor((int) result[i][j].abs());
//			}
//		}
	}
}
