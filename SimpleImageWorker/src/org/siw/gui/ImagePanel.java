package org.siw.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	public BufferedImage img;
	
	public ImagePanel (String filename) throws Exception {
		load(filename);
	}
	
	public void setImage (BufferedImage img) { this.img = img; }
	public BufferedImage getImage () { return this.img; }
	
	public void load(String filename) throws Exception {
		img = ImageIO.read(new File(filename));
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
	}
	
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(img, 0, 0, null);
    }
}
