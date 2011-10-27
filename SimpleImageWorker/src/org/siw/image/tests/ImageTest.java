package org.siw.image.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.siw.image.Image;
import org.siw.image.Pixel;
import org.siw.util.ComplexNumber;

public class ImageTest {

	@Test
	public void testDftIntInt() {
		Image img = new Image(10, 1);
		Pixel[][] data = img.getData();
		
		for (int i = 0; i < 10; i++) data[0][i].setColor(i);

		ComplexNumber[] actuals = new ComplexNumber[10];
		for (int i = 0; i < 10; i++) actuals[i] = img.dft(i, 0);
		
		ComplexNumber[] expecteds = new ComplexNumber[10];
		expecteds[0] = new ComplexNumber();
		expecteds[1] = new ComplexNumber();
		expecteds[2] = new ComplexNumber();
		expecteds[3] = new ComplexNumber();
		expecteds[4] = new ComplexNumber();
		expecteds[5] = new ComplexNumber();
		expecteds[6] = new ComplexNumber();
		expecteds[7] = new ComplexNumber();
		expecteds[8] = new ComplexNumber();
		expecteds[9] = new ComplexNumber();
		
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testIdft() {
		fail("Not yet implemented");
	}

}
