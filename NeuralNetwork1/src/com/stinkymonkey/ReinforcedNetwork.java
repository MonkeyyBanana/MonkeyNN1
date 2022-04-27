package com.stinkymonkey;

import java.util.Random;

public class ReinforcedNetwork {
	private double[][][] weights;
	private double[][] trainingData;
	private double[][] trainingGrader;
	private double[][] layer;
	
	private double alpha;
	private Random gen;
	
	private boolean printTrainMessage;
	private boolean printTrainMessage2;
	
	public ReinforcedNetwork(double[][] layers, int randSeed, double alpha, double[][] data, double[][] grader) {
		gen = new Random(randSeed);
		this.alpha = alpha;
		trainingData = data;
		trainingGrader = grader;
		layer = layers;
		weights = new double[layers.length - 1][][];
		printTrainMessage = false;
		printTrainMessage2 = true;
		
		int index = 0;
		for (int i = 0; i < layers.length - 1; i++) {
			double[][] temp = new double[layers[i + 1].length][layers[i].length];
			for (int j = 0; j < temp.length; j++)
				for (int o = 0; o < temp[j].length; o++)
					temp[j][o] = 2 * gen.nextDouble() - 1;
			weights[index++] = temp;
		}
	}
	
	public ReinforcedNetwork(double[][] layers, int randSeed, double alpha) {
		gen = new Random(randSeed);
		this.alpha = alpha;
		layer = layers;
		weights = new double[layers.length - 1][][];
		printTrainMessage = false;
		printTrainMessage2 = true;
		
		int index = 0;
		for (int i = 0; i < layers.length - 1; i++) {
			double[][] temp = new double[layers[i + 1].length][layers[i].length];
			for (int j = 0; j < temp.length; j++)
				for (int o = 0; o < temp[j].length; o++)
					temp[j][o] = 2 * gen.nextDouble() - 1;
			weights[index++] = temp;
		}
	}
	
	private double relu(double x) {
		if (x > 0)
			return x;
		return 0;
	}
	
	private double relu2deriv(double x) {
		if (x > 0)
			return 1;
		return 0;
	}
	
	private double[] relu2deriv(double[] x) {
		double[] out = new double[x.length];
		for (int i = 0; i < x.length; i++)
			if (x[i] > 0)
				out[i] = 1;
			else
				out[i] = 0;
		return out;
	}
	
	private double[][] crossNetwork(double[][] a, double[] b) {
		double[][] out = new double[a.length][];
		for (int i = 0; i < a.length; i++) {
			double[] temp = new double[b.length];
			for (int j = 0; j < b.length; j++) {
				temp[j] = a[i][0] * b[j];
			}
			out[i] = temp;
		}
		return out;
	}
	
	private double neuralNetwork(double[] input, double[] weight) {
		double output = 0;
		for (int i = 0; i < input.length; i++)
			output += input[i] * weight[i];
		return output;
	}
	
	private double[] neuralNetwork(double[][] input, double[] weight) {
		return vectMatrix(input, weight);
	}
	
	private double[][] matrixTranspose(double[] x) {
		double[][] out = new double[x.length][];
		for (int i = 0; i < x.length; i++) {
			out[i] = new double[] {x[i]};
		}
		return out;
	}
	
	private double[][] matrixTranspose(double[][] m) {
		double[][] temp = new double[m[0].length][m.length];
        for (int i = 0; i < m.length; i++)
            for (int j = 0; j < m[0].length; j++)
                temp[j][i] = m[i][j];
        return temp;
	}
	
	private double[] vectMatrix(double[][] matrix, double[] vect) {
		double[] output = new double[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			double sum = 0;
			for (int j = 0; j < matrix[0].length; j++)
				sum += matrix[i][j] * vect[j];
			output[i] = sum;
		}
		/*
		for (int i = 0; i < vect.length; i++)
			output[i] = w_sum(vect, matrix[i]);
		 */
		return output;	
	}
	
	private double w_sum(double[] a, double[] b) {
		double output = 0;
		for (int i = 0; i < a.length; i++)
			output += (a[i] * b[i]);
		return output;
	}
	
	private double[] arrayMult(double[] in, double mult) {
		double[] output = new double[in.length];
		for (int i = 0; i < in.length; i++)
			output[i] = in[i] * mult;
		return output;
	}
	
	private double[][] arrayMult(double[][] in, double mult) {
		double[][] out = new double[in.length][];
		for (int i = 0; i < in.length; i++) {
			double[] temp = new double[in[i].length];
			for (int j = 0; j < in[i].length; j++)
				temp[j] = in[i][j] * mult;
			out[i] = temp;
		}
		return out;
	}
	
	private double[] arrayMult(double[] a, double[] b) {
		double[] out = new double[a.length];
		for (int i = 0; i < a.length; i++) {
			out[i] = a[i] * b[i];
		}
		return out;
	}
	
	private double[] arraySub(double[] a, double[] b) {
		double[] output = new double[a.length];
		for (int i = 0; i < a.length; i++)
			output[i] = a[i] - b[i];
		return output;
	}
	
	private double[][] arraySub(double[][] a, double[][] b) {
		double[][] out = new double[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				out[i][j] = a[i][j] - b[i][j];
			}
		}
		return out;
	}
	
	private double arraySum(double[] a, double[] b) {
		double output = 0;
		for (int i = 0; i < a.length; i++)
			output += a[i] + b[i];
		return output;
	}
	
	private double arraySub(double[] a) {
		double output = 0;
		for (int i = 0; i < a.length; i++)
			output += a[i];
		return output;
	}
	
	private double[] arrayCompound(double[][] x) {
		double[] out = new double[x.length * x[0].length];
		int index = 0;
		for (int i = 0; i < x.length; i++)
			for (int j = 0; j < x[i].length; j++) {
				out[index] = x[i][j];
				index++;
			}
		return out;
	}
	
	
	public double[][][] runTraining(int iterations) {
		for (int j = 0; j < iterations; j++) {
			double error = 0;
			for (int i = 0; i < trainingData.length; i++) {
				layer[0] = trainingData[0];
				for (int f = 1; f < layer.length; f++) {
					for (int l = 0; l < layer[f].length; l++) {
						layer[f][l] = relu(neuralNetwork(layer[f - 1], weights[f - 1][l]));
					}
				}
				//System.out.println();
				error += Math.pow(arraySub(arraySub(layer[layer.length - 1], trainingGrader[i])), 2);
				double[][] layerDelta = new double[weights.length][];
				layerDelta[weights.length - 1] = arraySub(layer[layer.length - 1], trainingGrader[i]);
				
				for (int f = weights.length - 1; f > 0; f--) {
					//System.out.println(f);
					//System.out.println(relu2deriv(layer[f]));
					layerDelta[f - 1] = arrayMult(relu2deriv(layer[f]), neuralNetwork(matrixTranspose(arrayCompound(weights[f])), layerDelta[f]));
					//for (int z = 0; z < layerDelta.length; z++)
						//System.out.print(layerDelta[z] + " ");
					//System.out.println("//" + layerDelta[f]);
					//System.out.println(weights[f ]);
				}
				for (int f = 0; f < weights.length; f++) {
					double[][] deltaCalc = matrixTranspose(arrayMult(crossNetwork(matrixTranspose(layer[f]), layerDelta[f]), alpha));
					weights[f] = arraySub(weights[f], deltaCalc);
				}
				
				if (printTrainMessage) {
					System.out.println("\n//////////////////// Iteration: " + (j + 1) + " / Training: " + i + "\nWeights:");
					printMatrices(weights);
					System.out.println("\nError: " + error);
					
					System.out.println("//////////////////// Iteration: " + (j + 1) + " / Training: " + i + "\n");
				}
			}
			if (printTrainMessage2 && j % 10 == 9) {
				System.out.println("\n//////////////////// Iteration: " + (j + 1) + "\nWeights:");
				printMatrices(weights);
				System.out.println("\nError: " + error);
				
				System.out.println("//////////////////// Iteration: " + (j + 1) + "\n");
			}
		}
		return weights;
	}
	
	public double[] neuralNetworkRun(double[] data) {
		layer[0] = data;
		for (int f = 1; f < layer.length; f++) {
			for (int l = 0; l < layer[f].length; l++) {
				layer[f][l] = neuralNetwork(layer[f - 1], weights[f - 1][l]);
			}
		}
		return layer[layer.length - 1];
	}
	
	public double[][][] getWeights() {
		return weights;
	}
	
	public void setWeights(double[][][] weights) {
		this.weights = weights;
	}
	
	private void printMatrices(double[][][] weights) {
		for (int i = 0; i < weights.length; i++) {
			for (int j = 0; j < weights[i].length; j++) {
				for (int f = 0; f < weights[i][j].length; f++)
					System.out.print(weights[i][j][f] + " ");
				System.out.print(" //// ");
			}
			System.out.println();
		}
	}
	
	private void printMatrix(double[][] print) {
		for (double[] a : print) {
			for (double b : a)
				System.out.print(b + " ");
			System.out.println();
		}
	}
	
	public void setTrainMessage(boolean x, boolean y) {
		printTrainMessage = x;
		printTrainMessage2 = y;
	}
	
	
}
