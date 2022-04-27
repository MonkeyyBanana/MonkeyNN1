package com.stinkymonkey;

public class Driver {
	public static void main(String[] args) {
		double[][] layers = {{1, 1, 1},
				             {1, 1, 1, 1},
				             {1}};
		double[][] trainingData = {{1, 0, 1},
				                   {0, 1, 1},
                                   {0, 0, 1},
								   {1, 1, 1}};
		double[][] trainingGrader = {{1}, {1}, {0}, {0}};
		ReinforcedNetwork lol = new ReinforcedNetwork(layers, 2, 0.2, trainingData, trainingGrader);
		double[][][] weights = lol.getWeights();
		
		lol.runTraining(60);
		
		System.out.println("\n\n\nFINAL RUN: \n");
		double[] data = new double[] {0, 1, 0};
		double asd = lol.neuralNetworkRun(data)[0];
		System.out.println(asd);
	}
	
	private static void printMatrices(double[][][] weights) {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				for (int f = 0; f < weights[i][j].length; f++)
					System.out.print(weights[i][j][f] + " ");
				System.out.print(" //// ");
			}
			System.out.println();
		}
	}
	
	private static void printMatrix(double[][] print) {
		for (double[] a : print) {
			for (double b : a)
				System.out.print(b + " ");
			System.out.println();
		}
	}
	
	private static void printArray(double[] arr) {
		for (double a : arr)
			System.out.print(a + " ");
	}
}
