package org.siw.image.geometric;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Expand implements GeometricOperation {
	private int width;
	private int height;
	
	public Expand(int width, int height) {
		super();
		this.width = width;
		this.height = height;
	}

	@Override
	public BufferedImage execute(BufferedImage img) {
		BufferedImage expanded = new BufferedImage(width, height, img.getType());
		Graphics2D g = expanded.createGraphics();
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.drawImage(img, (width - img.getWidth())/2, (height - img.getHeight())/2, null);
		
		return expanded;
	}

	public static void main(String[] args) throws Exception {
		BufferedImage lena = ImageIO.read(new File("testes/lena.big.png"));
		
		GeometricOperation op = new Expand(700, 700);
		lena = op.execute(lena);
		
		ImageIO.write(lena, "png", new File("testes_out/teste.png"));
	}

}
