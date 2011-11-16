package org.siw.gui;

import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileFilter;

public class ImageFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) return true;
		
		String filename = f.getName();
		int dot = filename.lastIndexOf(".");

		if (dot < 0) return false;
		
		String suffix = filename.substring(dot + 1);
		return (ImageIO.getImageReadersBySuffix(suffix).hasNext());
	}

	@Override
	public String getDescription() {
		return "Supported images";
	}

}
