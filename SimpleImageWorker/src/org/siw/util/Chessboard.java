package org.siw.util;

public class Chessboard implements Distance {

	@Override
	public double calculate(double[] descriptorA, double[] descriptorB) {
		double dist = -1;
		
		if (descriptorA.length != descriptorB.length) throw new RuntimeException("Dimensoes dos descritores diferentes.");
		
		for (int i = 0; i < descriptorA.length; i++) {
			dist = Math.max(dist, Math.abs(descriptorA[i] - descriptorB[i]));
		}
		
		return dist;
	}

}
