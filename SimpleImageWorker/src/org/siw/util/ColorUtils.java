package org.siw.util;

import java.awt.Color;

public class ColorUtils {
	public static int[] toArray (int color) {
		Color c = new Color(color);
		return new int[] {c.getRed(), c.getGreen(), c.getBlue()};
	}
	
	public static int toInteger (int[] colors) {
		return toInteger(colors[0], colors[1], colors[2]);
	}
	public static int toInteger (int r, int g, int b) {
		if (r > 255) r = 255;
		else if (r < 0) r = 0;
		
		if (g > 255) g = 255;
		else if (g < 0) g = 0;
		
		if (b > 255) b = 255;
		else if (b < 0) b = 0;
		
		return new Color(r, g, b).getRGB();
	}
}
