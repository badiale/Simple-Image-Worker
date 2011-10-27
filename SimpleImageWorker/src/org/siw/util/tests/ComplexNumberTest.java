package org.siw.util.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.siw.util.ComplexNumber;

public class ComplexNumberTest {

	@Test
	public void testAbs() {
		double[] expecteds = new double[6];
		double[] actuals = new double[6];
		
		ComplexNumber[] numbers = new ComplexNumber[6];
		numbers[0] = new ComplexNumber(0, 0);
		numbers[1] = new ComplexNumber(1, 0);
		numbers[2] = new ComplexNumber(0, 1);
		numbers[3] = new ComplexNumber(-1, 0);
		numbers[4] = new ComplexNumber(0, -1);
		numbers[5] = new ComplexNumber(1, 1);
		
		expecteds[0] = 0;
		expecteds[1] = 1;
		expecteds[2] = 1;
		expecteds[3] = 1;
		expecteds[4] = 1;
		expecteds[5] = 1.4142135623731;
		
		for (int i = 0; i < numbers.length; i++)
			actuals[i] = numbers[i].abs();
		
		assertArrayEquals(expecteds, actuals, 0.000001);
	}

	@Test
	public void testAddComplexNumber() {
		ComplexNumber[] expecteds = new ComplexNumber[6];
		ComplexNumber[] actuals = new ComplexNumber[6];
		
		ComplexNumber[] numbers = new ComplexNumber[7];
		numbers[0] = new ComplexNumber(0, 0);
		numbers[1] = new ComplexNumber(1, 0);
		numbers[2] = new ComplexNumber(0, 1);
		numbers[3] = new ComplexNumber(-1, 0);
		numbers[4] = new ComplexNumber(0, -1);
		numbers[5] = new ComplexNumber(1, 1);
		numbers[6] = new ComplexNumber(-1, -1);
		
		expecteds[0] = new ComplexNumber(1, 0);
		expecteds[1] = new ComplexNumber(1, 1);
		expecteds[2] = new ComplexNumber(-1, 1);
		expecteds[3] = new ComplexNumber(-1, -1);
		expecteds[4] = new ComplexNumber(1, 0);
		expecteds[5] = new ComplexNumber(0, 0);
		
		for (int i = 0; i < numbers.length - 1; i++)
			actuals[i] = ComplexNumber.add(numbers[i], numbers[i+1]);
		
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testSubtractComplexNumber() {
		ComplexNumber[] expecteds = new ComplexNumber[6];
		ComplexNumber[] actuals = new ComplexNumber[6];
		
		ComplexNumber[] numbers = new ComplexNumber[7];
		numbers[0] = new ComplexNumber(0, 0);
		numbers[1] = new ComplexNumber(1, 0);
		numbers[2] = new ComplexNumber(0, 1);
		numbers[3] = new ComplexNumber(-1, 0);
		numbers[4] = new ComplexNumber(0, -1);
		numbers[5] = new ComplexNumber(1, 1);
		numbers[6] = new ComplexNumber(-1, -1);
		
		expecteds[0] = new ComplexNumber(-1, 0);
		expecteds[1] = new ComplexNumber(1, -1);
		expecteds[2] = new ComplexNumber(1, 1);
		expecteds[3] = new ComplexNumber(-1, 1);
		expecteds[4] = new ComplexNumber(-1, -2);
		expecteds[5] = new ComplexNumber(2, 2);
		
		for (int i = 0; i < numbers.length - 1; i++)
			actuals[i] = ComplexNumber.subtract(numbers[i], numbers[i+1]);
		
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testDivideComplexNumber() {
		ComplexNumber[] expecteds = new ComplexNumber[6];
		ComplexNumber[] actuals = new ComplexNumber[6];
		
		ComplexNumber[] numbers = new ComplexNumber[7];
		numbers[0] = new ComplexNumber(0, 0);
		numbers[1] = new ComplexNumber(1, 0);
		numbers[2] = new ComplexNumber(0, 1);
		numbers[3] = new ComplexNumber(-1, 0);
		numbers[4] = new ComplexNumber(0, -1);
		numbers[5] = new ComplexNumber(1, 1);
		numbers[6] = new ComplexNumber(-1, -1);
		
		expecteds[0] = new ComplexNumber(0, 0);
		expecteds[1] = new ComplexNumber(0, -1);
		expecteds[2] = new ComplexNumber(0, -1);
		expecteds[3] = new ComplexNumber(0, -1);
		expecteds[4] = new ComplexNumber(-0.5, -0.5);
		expecteds[5] = new ComplexNumber(-1, 0);
		
		for (int i = 0; i < numbers.length - 1; i++)
			actuals[i] = ComplexNumber.divide(numbers[i], numbers[i+1]);
		
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testDivideDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testMultiplyComplexNumber() {
		ComplexNumber[] expecteds = new ComplexNumber[6];
		ComplexNumber[] actuals = new ComplexNumber[6];
		
		ComplexNumber[] numbers = new ComplexNumber[7];
		numbers[0] = new ComplexNumber(0, 0);
		numbers[1] = new ComplexNumber(1, 0);
		numbers[2] = new ComplexNumber(0, 1);
		numbers[3] = new ComplexNumber(-1, 0);
		numbers[4] = new ComplexNumber(0, -1);
		numbers[5] = new ComplexNumber(1, 1);
		numbers[6] = new ComplexNumber(-1, -1);
		
		expecteds[0] = new ComplexNumber(0, 0);
		expecteds[1] = new ComplexNumber(0, 1);
		expecteds[2] = new ComplexNumber(0, -1);
		expecteds[3] = new ComplexNumber(0, 1);
		expecteds[4] = new ComplexNumber(1, -1);
		expecteds[5] = new ComplexNumber(0, -2);
		
		for (int i = 0; i < numbers.length - 1; i++)
			actuals[i] = ComplexNumber.multiply(numbers[i], numbers[i+1]);
		
		assertArrayEquals(expecteds, actuals);
	}

	@Test
	public void testMultiplyDouble() {
		fail("Not yet implemented");
	}

	@Test
	public void testExp() {
		ComplexNumber[] expecteds = new ComplexNumber[6];
		ComplexNumber[] actuals = new ComplexNumber[6];
		
		ComplexNumber[] numbers = new ComplexNumber[7];
		numbers[0] = new ComplexNumber(0, 0);
		numbers[1] = new ComplexNumber(1, 0);
		numbers[2] = new ComplexNumber(0, 1);
		numbers[3] = new ComplexNumber(-1, 0);
		numbers[4] = new ComplexNumber(0, -1);
		numbers[5] = new ComplexNumber(1, 1);
		numbers[6] = new ComplexNumber(-1, -1);
		
		expecteds[0] = new ComplexNumber(1, 0);
		expecteds[1] = new ComplexNumber(Math.E, 0);
		expecteds[2] = new ComplexNumber(0.540302305868139717400936607442976603732310420617922227670, 0.841470984807896506652502321630298999622563060798371065672);
		expecteds[3] = new ComplexNumber(1 / Math.E, 0);
		expecteds[4] = new ComplexNumber(0.540302305868139717400936607442976603732310420617922227670, -0.841470984807896506652502321630298999622563060798371065672);
		expecteds[5] = new ComplexNumber(1.468693939915885157138967597326604261326956736629008722797, 2.287355287178842391208171906700501808955586256668355680938);
		
		for (int i = 0; i < numbers.length - 1; i++)
			actuals[i] = ComplexNumber.exp(numbers[i]);
		
		assertArrayEquals(expecteds, actuals);
	}

}
