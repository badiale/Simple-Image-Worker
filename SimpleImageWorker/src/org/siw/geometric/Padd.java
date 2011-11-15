package org.siw.geometric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Padd implements GeometricOperation {
	private int size;
	
	public Padd(int size) {
		super();
		this.size = size;
	}

	@Override
	public BufferedImage execute(BufferedImage img) {
		BufferedImage expanded = new BufferedImage(img.getWidth() + size*2, img.getHeight() + size*2, img.getType());
		Graphics2D g = expanded.createGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, expanded.getWidth(), expanded.getHeight());
		g.drawImage(img, size, size, null);
		
		return expanded;
	}

	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		GeometricOperation op = new Padd(20);
		lena = op.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}

}
