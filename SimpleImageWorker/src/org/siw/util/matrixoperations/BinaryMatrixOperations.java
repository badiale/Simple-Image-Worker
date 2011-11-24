package org.siw.util.matrixoperations;

import org.siw.util.ComplexNumber;


public interface BinaryMatrixOperations {
	public double[][] execute(double[][] a, double[][] b);
	public ComplexNumber[][] execute(ComplexNumber[][] a, ComplexNumber[][] b);
	public double[][] execute(double[][] a, double scalar);
	public ComplexNumber[][] execute(ComplexNumber[][] a, double scalar);
}
