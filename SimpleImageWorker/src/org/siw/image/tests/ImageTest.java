package org.siw.image.tests;

import static org.junit.Assert.assertArrayEquals;

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
		img.setData(data);

		ComplexNumber[] actuals = new ComplexNumber[10];
		for (int i = 0; i < 10; i++) actuals[i] = img.dft(i, 0);
		
		ComplexNumber[] expecteds = new ComplexNumber[10];
		expecteds[0] = new ComplexNumber(45, 0);
		expecteds[1] = new ComplexNumber(-5, 15.388417685876267012851452880184549120033510717688962135195);
		expecteds[2] = new ComplexNumber(-5, 6.8819096023558676910360479095544383976294966800407933168283);
		expecteds[3] = new ComplexNumber(-5, 3.6327126400268044294773337874030937480804619648260423137503);
		expecteds[4] = new ComplexNumber(-5, 1.6245984811645316307793570610756723247745173576073755015390);
		expecteds[5] = new ComplexNumber(-5, 0);
		expecteds[6] = new ComplexNumber(-5, -1.624598481164531630779357061075672324774517357607375501539);
		expecteds[7] = new ComplexNumber(-5, -3.632712640026804429477333787403093748080461964826042313750);
		expecteds[8] = new ComplexNumber(-5, -6.881909602355867691036047909554438397629496680040793316828);
		expecteds[9] = new ComplexNumber(-5, -15.38841768587626701285145288018454912003351071768896213519);
		
		assertArrayEquals(expecteds, actuals);
	}
	
	@Test
	public void testFftRec() {
		ComplexNumber[] x = new ComplexNumber[8];
		for (int i = 0; i < x.length; i++) x[i] = new ComplexNumber(i, 0);
		
		ComplexNumber[] actuals = new Image().fft(x);
		for (int i = 0; i < actuals.length; i++) System.out.println("testFftRec(): actuals["+i+"]: " + actuals[i]);
		
		ComplexNumber[] expecteds = new ComplexNumber[8];
		expecteds[0] = new ComplexNumber(28, 0);
		expecteds[1] = new ComplexNumber(-4, 9.6568542494923801952067548968387923142786875015077922927);
		expecteds[2] = new ComplexNumber(-4, 4);
		expecteds[3] = new ComplexNumber(-4, 1.6568542494923801952067548968387923142786875015077922927);
		expecteds[4] = new ComplexNumber(-4, 0);
		expecteds[5] = new ComplexNumber(-4, -1.6568542494923801952067548968387923142786875015077922927);
		expecteds[6] = new ComplexNumber(-4, -4);
		expecteds[7] = new ComplexNumber(-4, -9.6568542494923801952067548968387923142786875015077922927);
		
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testIdft() {
		double[] expecteds = new double[10];
		for (int i = 0; i < 10; i++) expecteds[i] = i;
		
		ComplexNumber[][] entrada = new ComplexNumber[1][10];
		entrada[0][0] = new ComplexNumber(45, 0);
		entrada[0][1] = new ComplexNumber(-5, 15.388417685876267012851452880184549120033510717688962135195);
		entrada[0][2] = new ComplexNumber(-5, 6.8819096023558676910360479095544383976294966800407933168283);
		entrada[0][3] = new ComplexNumber(-5, 3.6327126400268044294773337874030937480804619648260423137503);
		entrada[0][4] = new ComplexNumber(-5, 1.6245984811645316307793570610756723247745173576073755015390);
		entrada[0][5] = new ComplexNumber(-5, 0);
		entrada[0][6] = new ComplexNumber(-5, -1.624598481164531630779357061075672324774517357607375501539);
		entrada[0][7] = new ComplexNumber(-5, -3.632712640026804429477333787403093748080461964826042313750);
		entrada[0][8] = new ComplexNumber(-5, -6.881909602355867691036047909554438397629496680040793316828);
		entrada[0][9] = new ComplexNumber(-5, -15.38841768587626701285145288018454912003351071768896213519);
		
		double[] actuals = new double[10];
		Image img = new Image(10, 1);
		for (int i = 0; i < 10; i++) actuals[i] = img.idft(entrada, i, 0);
		
		assertArrayEquals(expecteds, actuals, 0.0000000001);
	}
	
	@Test
	public void testIFftRec() {
		ComplexNumber[] entrada = new ComplexNumber[8]; 
		entrada[0] = new ComplexNumber(28, 0);
		entrada[1] = new ComplexNumber(-4, 9.6568542494923801952067548968387923142786875015077922927);
		entrada[2] = new ComplexNumber(-4, 4);
		entrada[3] = new ComplexNumber(-4, 1.6568542494923801952067548968387923142786875015077922927);
		entrada[4] = new ComplexNumber(-4, 0);
		entrada[5] = new ComplexNumber(-4, -1.6568542494923801952067548968387923142786875015077922927);
		entrada[6] = new ComplexNumber(-4, -4);
		entrada[7] = new ComplexNumber(-4, -9.6568542494923801952067548968387923142786875015077922927);
		
		ComplexNumber[] actuals = new Image().ifft(entrada);
		for (int i = 0; i < actuals.length; i++) System.out.println("testIFftRec(): actuals["+i+"]: " + actuals[i]);
		
		ComplexNumber[] expecteds = new ComplexNumber[8];
		for (int i = 0; i < expecteds.length; i++) expecteds[i] = new ComplexNumber(i, 0);
		
		assertArrayEquals(expecteds, actuals);
	}

}
