package org.siw.containers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;

import org.siw.image.Image;

public class PGM implements ImageContainer {
	// save the image on a file
	public void save (File file, Image img) throws FileNotFoundException {
		PrintStream out = new PrintStream (file);
		out.println("P5");
		out.println("#criado por mim :)");
		out.println(img.getWidth() + " " + img.getHeight());
		out.println("255");

		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				int color = img.getPixel(x,y).getColor();
				if (color > 255) out.write(255);
				else if (color < 0) out.write(0);
				else out.write(color);
			}
		}

		out.close();
	}

	// load a image from a file
	public Image load (File file) throws Exception {
		Image img = null;

		FileInputStream in = new FileInputStream(file);
		
		char[] data = new char[20];
		Arrays.fill(data, (char) 0);
		data[0] = (char) in.read();
		data[1] = (char) in.read();
		if (!(data[0] == 'P' && data[1] == '5')) {
			throw new Exception("File not supported");
		}
		
		// ignoring blank
		in.read();

		String blanks = "\n";
		char read = (char) in.read();
		if (read == '#') while (blanks.indexOf((char) in.read()) == -1) ;
		
		blanks = "\n\t ";
		Arrays.fill(data, (char) 0);
		int count = 0;
		do {
			data[count] = (char) in.read();
		} while (blanks.indexOf(data[count++]) == -1);
		int width = Integer.parseInt(new String(data).trim());

		Arrays.fill(data, (char) 0);
		count = 0;
		do {
			data[count] = (char)in.read();
		} while (blanks.indexOf(data[count++]) == -1);
		int height = Integer.parseInt(new String(data).trim());
		
		Arrays.fill(data, (char) 0);
		count = 0;
		do {
			data[count] = (char)in.read();
		} while (blanks.indexOf(data[count++]) == -1);
		/*int colorDepth =*/ Integer.parseInt(new String(data).trim());

		img = new Image(width, height);
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				img.getPixel(x, y).setColor(in.read());
			}
		}

		in.close();
		
		return img;
	}

	public String toString() {
		return "PGM ImageContainer";
	}
}
