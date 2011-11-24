package org.siw.util;

public class Manhattan implements Distance {

	@Override
	public double calculate(double[] descriptorA, double[] descriptorB) {
		double dist = 0;
		
		if (descriptorA.length != descriptorB.length) throw new RuntimeException("Dimensoes dos descritores diferentes.");
		
		for (int i = 0; i < descriptorA.length; i++) {
			dist += Math.abs(descriptorA[i] - descriptorB[i]);
		}
		
		return dist;
	}

}
