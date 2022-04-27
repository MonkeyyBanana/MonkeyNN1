package com.stinkymonkey;

import Monkey.util.Matrix;

public class NDriver {
	public static void main(String[] args) {
		Matrix trainingData = new Matrix(new double[][] {{1, 0, 1},
													 	 {0, 1, 1},
													 	 {0, 0, 1},
													 	 {1, 1, 1}});
		Matrix trainingGrader = new Matrix(new double[][] {{0}, {1}, {0}, {1}});
	 
		NeuralNetwork lol = new NeuralNetwork(new int[] {3, 4, 1}, 1, 0.1, trainingData, trainingGrader);
		lol.setTrainingMsg2(false);
		Matrix[] weights = lol.runTraining(2000);
		
		//System.out.println(trainingData.T());
		System.out.println(lol.runNeuralNetwork(new Matrix(new double[] {1, 1, 1})));
		
	}
	
	private static void printDMatrix(Matrix[] x) {
		for (int i = 0; i < x.length; i++)
			System.out.println(x[i]);
	}
}
