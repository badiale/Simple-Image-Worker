package org.siw.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 2379940048568437976L;
	public File file;
	public BufferedImage img;
	
	public ImagePanel (File filename) throws Exception {
		load(filename);
	}
	
	public ImagePanel (BufferedImage img) throws Exception {
		this.file = null;
		this.img = img;
		setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		repaint();
	}
	
	public void setImage (BufferedImage img) { this.img = img; }
	public BufferedImage getImage () { return this.img; }
	
	public void load(File filename) throws Exception {
		if (filename != null) {
			this.file = filename;
			img = ImageIO.read(filename);
			setPreferredSize(new Dimension(img.getWidth(), img.getHeight()));
		}
	}
	
	public void reload() throws Exception {
		load(this.file);
		repaint();
	}
	
    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(img, 0, 0, null);
    }
}
