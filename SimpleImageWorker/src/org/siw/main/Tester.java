package org.siw.main;

import java.io.File;

/**
 * Classe para testar o programa principal
 * */
public class Tester {
	private String input;
	private String output;
	
	public Tester(String input, String outputDir) {
		super();
		this.input = input;
		this.output = outputDir;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public void runTestCase(String[] args) throws Exception {
		File inputfile = new File(this.input);
		String input = inputfile.getName();
		
		String extension = input.substring(input.lastIndexOf(".") + 1).toLowerCase();
		String output = this.output + "/" + input.substring(0, input.lastIndexOf("."));
		String[] newargs = new String[args.length + 2];
		
		File outdir = new File(this.output);
		if (!outdir.exists()) outdir.mkdir();
		else if (!outdir.isDirectory()) throw new Exception(this.output + " nao eh um diretorio.");
		
		newargs[0] = this.input;
		for (int i = 0; i < args.length; i++) {
			newargs[i+1] = args[i];
			output += args[i];
		}
		newargs[newargs.length - 1] = output + "." + extension;
		
		try {
			System.out.print("Running: "); for (String arg : newargs) System.out.print(arg + " ");
			long startTime = System.currentTimeMillis();
			Main.main(newargs);
			long endTime = System.currentTimeMillis();
			System.out.println("(Time: " + ((double) (endTime - startTime) / 1000) + " seconds)");
		} catch (Exception e) {
			System.out.println();
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Tester t = new Tester("testes/lena.big.pgm", "testes_out");
		
		t.runTestCase(new String[] {});
		t.runTestCase(new String[] {"-brilho", "30"});
		t.runTestCase(new String[] {"-contraste", "0", "255"});
		t.runTestCase(new String[] {"-log", "20"});
		t.runTestCase(new String[] {"-exp", "20"});
		t.runTestCase(new String[] {"-binarizacao", "128"});
		t.runTestCase(new String[] {"-media", "5"});
		t.runTestCase(new String[] {"-median", "10"});
		t.runTestCase(new String[] {"-gauss", "10"});
		t.runTestCase(new String[] {"-border"});
		t.runTestCase(new String[] {"-LoG", "1"});
		t.runTestCase(new String[] {"-LoG", "1", "-zero"});
		t.runTestCase(new String[] {"-LoG", "2"});
		t.runTestCase(new String[] {"-LoG", "2", "-zero"});
		t.runTestCase(new String[] {"-LoG", "3"});
		t.runTestCase(new String[] {"-LoG", "3", "-zero"});
//		t.runTestCase(new String[] {"-dft"});
//		t.runTestCase(new String[] {"-dft", "-idft"});
		t.runTestCase(new String[] {"-fft"});
		t.runTestCase(new String[] {"-fft", "-ifft"});
		t.runTestCase(new String[] {"-fft", "-fgauss", "2", "-ifft"});
		t.runTestCase(new String[] {"-fft", "-fgauss", "20", "-ifft"});
		t.runTestCase(new String[] {"-fft", "-fgauss", "50", "-ifft"});
	}

}
