package org.siw.main;


import java.io.File;
import java.util.HashMap;

import org.siw.containers.ImageContainer;
import org.siw.containers.PGM;
import org.siw.image.Image;

public class Main {
	public static void main (String[] args) throws Exception {
		if (args.length < 2) {
			System.err.println("Numero incorreto de argumentos.");
			System.err.println("Uso: java -jar ProcImg.jar ENTRADA [ OPCOES ] SAIDA");
			System.err.println();
			System.err.println("Onde as opcoes podem ser:");
			System.err.println("	-brilho NUMERO		Para mudar o brilho");
			System.err.println("	-contraste MIN MAX	Para mudar o contraste para MIN e MAX");
			System.err.println("	-log C				Para mudar o contraste usando a formula: c * log(1 + corPixel). Use C = 0 para usar o valor default, isto eh, o maximo sera 255");
			System.err.println("	-exp GAMMA			Para mudar o contraste usando a formula: corPixel ^ gamma.");
			System.err.println("	-binarizacao COR	Para binarizar a imagem, dado uma cor.");
			System.err.println("	-media  SIZE		Para suavizar a imagem, usando uma mascara de tamanho SIZE.");
			System.err.println("	-median SIZE		Para remover ruidos utilizando a mediana e uma mascara de tamanho SIZE.");
			System.err.println("	-gauss SIGMA		Para suavizar uma imagem utilizando Gauss, dado um sigma.");
			System.err.println("	-border				Para achar a borda, via laplace.");
			System.err.println("	-LoG SIGMA			Para achar a borda, usando \"Laplacian of Gaussian\".");
			System.err.println("	-zero				Efetua o zero crossing. Apenas util se aplicar o LoG primeiro.");
			System.err.println("	-dft				Efetua o DFT.");
			System.exit(1);
		}

		// containers suportados
		HashMap<String, ImageContainer> containers = new HashMap<String, ImageContainer>();
		containers.put("pgm", new PGM());

		String inputName = args[0];
		String outputName = args[args.length - 1];
		
		ImageContainer inputContainer = containers.get(inputName.substring(inputName.lastIndexOf(".") + 1).toLowerCase());
		ImageContainer outputContainer = containers.get(outputName.substring(outputName.lastIndexOf(".") + 1).toLowerCase());
		
		if (inputContainer == null) throw new Exception("Container de input desconhecido");
		if (outputContainer == null) throw new Exception("Container de output desconhecido");

		Image img = inputContainer.load(new File(inputName));

		// aplica as opcoes
		int i = 1; 
		while (i < args.length - 1) {
			String opcao = args[i];
			if (opcao.equals("-brilho")) {
				int numero = Integer.parseInt(args[++i]);
				img.brilho(numero);
			} else if (opcao.equals("-contraste")) {
				int min = Integer.parseInt(args[++i]);
				int max = Integer.parseInt(args[++i]);
				img.contrast(min, max);
			} else if (opcao.equals("-log")) {
				double c = Double.parseDouble(args[++i]);
				img.log(c);
			} else if (opcao.equals("-exp")) {
				double gamma = Double.parseDouble(args[++i]);
				img.exp(gamma);
			} else if (opcao.equals("-border")) {
				double[][] matrix = {
					{ 0, -1,  0},
					{-1,  4, -1},
					{ 0, -1,  0},
				};
				img.applyFilter(matrix);
			} else if (opcao.equals("-binarizacao")) {
				int treshold = Integer.parseInt(args[++i]);
				img.binarizacao(treshold);
			} else if (opcao.equals("-gauss")) {
				double sigma = Double.parseDouble(args[++i]);
				img.gaussian(sigma);
			} else if (opcao.equals("-median")) {
				int size = Integer.parseInt(args[++i]);
				img.median(size);
			} else if (opcao.equals("-media")) {
				int size = Integer.parseInt(args[++i]);
				img.media(size);
			} else if (opcao.equals("-LoG")) {
				double sigma = Double.parseDouble(args[++i]);
				img.laplaceOfGaussian(sigma);
			} else if (opcao.equals("-zero")) {
				img.zeroCrossing();
			} else if (opcao.equals("-dft")) {
				//img.dft();
				img.fft();
			} else {
				throw new Exception ("Opção inválida");
			}
			i++;
		}
		
		// elimina cores negativas, ou maiores q 255
		img.absoluteColors();
		
		outputContainer.save(new File(outputName), img);
	}
}
