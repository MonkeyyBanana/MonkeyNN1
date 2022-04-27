package com.stinkymonkey;

public class Runner {
	private static double[] weights = {0.51231223, 0.15125341, -0.1532463256};
	private static double alpha = 0.1;
	private static int hidden = 4;
	private static double[][] streetLights = {{0, 0, 1},
										      {0, 1, 1},
							                  {0, 0, 1},
							                  {1, 1, 1},
							                  {0, 1, 1},
							                  {1, 0, 1}};
	
	private static double[] streetData = {0, 1, 0, 1, 1, 0}; 
	
	public static void main(String[] args) {
		double err = 0;
		for (int i = 0; i < 80000; i++) {
			double error = 0;
			for (int j = 0; j < streetData.length; j++) {
				double[] input = streetLights[j];
				double goalPred = streetData[j];
				double prediction = neuralNetwork(input, weights);
				
				err = Math.pow(prediction - goalPred, 2);
				error += err;
				double delta = prediction - goalPred;
				weights = arraySub(weights, arrayMult((arrayMult(input, delta)), alpha));
				
				System.out.println("Prediction: " + prediction);
			}
			System.out.print("Weights: ");
			for (double a : weights)
				System.out.print(a + " ");
			System.out.println("\nError: " + err + "\n");
		}
	}
	
	private static double neuralNetwork(double[] input, double[] weight) {
		double output = 0;
		for (int i = 0; i < input.length; i++)
			output += input[i] * weight[i];
		return output;
	}
	
	private static double[] neuralNetwork(double[][] input, double[] weight) {
		return vectMatrix(input, weights);
	}
	
	private static double[] vectMatrix(double[][] matrix, double[] vect) {
		double[] output = new double[matrix.length];
		/*
		for (int i = 0; i < matrix.length; i++) {
			double sum = 0;
			for (int j = 0; j < matrix[0].length; j++)
				sum += matrix[i][j] * vect[j];
			output[i] = sum;
		}
		*/
		for (int i = 0; i < vect.length; i++)
			output[i] = w_sum(vect, matrix[i]);
		return output;	
	}
	
	private static double w_sum(double[] a, double[] b) {
		double output = 0;
		for (int i = 0; i < a.length; i++)
			output += (a[i] * b[i]);
		return output;
	}
	
	private static double[] arrayMult(double[] in, double mult) {
		double[] output = new double[in.length];
		for (int i = 0; i < in.length; i++)
			output[i] = in[i] * mult;
		return output;
	}
	
	private static double[] arraySub(double[] a, double[] b) {
		double[] output = new double[a.length];
		for (int i = 0; i < a.length; i++)
			output[i] = a[i] - b[i];
		return output;
	}
}
