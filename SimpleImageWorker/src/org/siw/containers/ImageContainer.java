package org.siw.containers;

import java.io.File;
import java.io.FileNotFoundException;

import org.siw.image.Image;

public interface ImageContainer {
	// save the image on a file
	public void save (File file, Image img) throws FileNotFoundException ;

	// load a image from a file
	public Image load (File file) throws Exception ;
}
