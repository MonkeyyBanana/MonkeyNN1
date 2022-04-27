package com.stinkymonkey;
import java.util.Random;

import Monkey.util.*;

public class NeuralNetwork {
	private double alpha;
	private Random gen;
	
	private Matrix[] weights;
	private Matrix layers;
	private Matrix trainingData;
	private Matrix trainingGrader;
	
	private boolean printTrainMessage;
	private boolean printTrainMessage2;
	
	
	public NeuralNetwork(int[] layer, int randSeed, double alpha, Matrix data, Matrix grader) {
		gen = new Random(randSeed);
		this.alpha = alpha;
		trainingData = data;
		trainingGrader = grader;
		weights = new Matrix[layer.length - 1];
		
		printTrainMessage = false;
		printTrainMessage2 = true;
		
		double[][] lTemp = new double[layer.length][];
		for (int i = 0; i < layer.length; i++) {
			lTemp[i] = new double[layer[i]];
		}
		layers = new Matrix(lTemp);
		
		int index = 0;	
		for (int i = 0; i < layer.length - 1; i++) {
			double[][] temp = new double[layer[i]][layer[i + 1]];
			for (int j = 0; j < temp.length; j++)
				for (int o = 0; o < temp[j].length; o++)
					temp[j][o] = 2 * gen.nextDouble() - 1;
			weights[index++] = new Matrix(temp);
		}
	}
	
	public NeuralNetwork(int[] layer, int randSeed, double alpha) {
		gen = new Random(randSeed);
		this.alpha = alpha;
		weights = new Matrix[layer.length - 1];
		
		printTrainMessage = false;
		printTrainMessage2 = true;
		
		double[][] lTemp = new double[layer.length][];
		for (int i = 0; i < layer.length; i++) {
			lTemp[i] = new double[layer[i]];
		}
		layers = new Matrix(lTemp);
		
		int index = 0;
		for (int i = 0; i < layer.length - 1; i++) {
			double[][] temp = new double[layer[i]][layer[i + 1]];
			for (int j = 0; j < temp.length; j++)
				for (int o = 0; o < temp[j].length; o++)
					temp[j][o] = 2 * gen.nextDouble() - 1;
			weights[index++] = new Matrix(temp);
		}
	}
	
	private Matrix relu(Matrix x) {
		double[][] mat = x.getArray();
		for (int i = 0; i < mat.length; i++)
			for (int j = 0; j < mat[i].length; j++)
				if (!(mat[i][j] > 0))
					mat[i][j] = 0;
		return new Matrix(mat);
	}
	
	private Matrix relu2Deriv(Matrix x) {
		double[][] mat = x.getArray();
		for (int i = 0; i < mat.length; i++)
			for (int j = 0; j < mat[i].length; j++)
				if (!(mat[i][j] > 0))
					mat[i][j] = 0;
				else
					mat[i][j] = 1;
		return new Matrix(mat);
	}
	
	public Matrix[] runTraining(int iterations) {
		for (int i = 1; i <= iterations; i++) {
			
			System.out.println("\n\n");
			
			double error = 0;
			for (int j = 0; j < trainingData.size(); j++) {
				layers.setArray(0, trainingData.getArray(j));
				for (int f = 1; f < layers.size(); f++) {
					layers.setVector(f, relu(layers.getVector(f - 1).dot(weights[f - 1])));
				}
				System.out.println("\n" + layers.getVector(2) + "  " + trainingData.getVector(j));
				error += layers.getVector(layers.size() - 1).subtract(trainingGrader.getVector(j).power(2)).sum();
				
				Matrix layerDelta = new Matrix(new double[weights.length][]);
				layerDelta.setVector(weights.length - 1, layers.getVector(layers.size() - 1).subtract(trainingGrader.getVector(j)));
				//System.out.println("\n" + layers.getVector(layers.size() - 1));
				
				for (int f = weights.length - 1; f > 0; f--) {
					layerDelta.setVector(f - 1, layerDelta.getVector(f).dot(weights[f].T()).multiply(relu2Deriv(layers.getVector(f))));
				}
				
				//System.out.println(layerDelta.getVector(0));
				for (int f = 0; f < weights.length; f++) {
					//System.out.println("\n" + weights[f]);
					//System.out.println(weights[f].subtract(layers.getVector(f).T().dot(layerDelta.getVector(f))).multiply(alpha));
					weights[f] = weights[f].subtract(layers.getVector(f).T().dot(layerDelta.getVector(f))).multiply(alpha);
				}
				
				if (printTrainMessage) {
					System.out.println("\n//////////////////// Iteration: " + (i) + " / Training: " + j + "\nWeights:");
					printDMatrix(weights);
					System.out.println("\nError: " + error);
					
					System.out.println("//////////////////// Iteration: " + (i) + " / Training: " + j + "\n");
				}
			}
			if (printTrainMessage2 && i % 1 == 0) {
				System.out.println("\n//////////////////// Iteration: " + (i) + "\nWeights:");
				printDMatrix(weights);
				System.out.println("\nError: " + error);
				
				System.out.println("//////////////////// Iteration: " + (i) + "\n");
			}
		}
		return weights;
	}
	
	public Matrix runNeuralNetwork(Matrix data) {
		Matrix out = new Matrix(data.size(), data.size(0));
		
		System.out.println("\n\n\n\n//////");
			
			System.out.println("\n\n\n\n\n\n\n\n");
			
			double error = 0;
			for (int j = 0; j < trainingData.size(); j++) {
				layers.setArray(0, trainingData.getArray(j));
				for (int f = 1; f < layers.size(); f++) {
					layers.setVector(f, relu(layers.getVector(f - 1).dot(weights[f - 1])));
				}
				System.out.println("\n" + layers.getVector(2) + "  " + trainingData.getVector(j));
				printDMatrix(weights);
				
				error += layers.getVector(layers.size() - 1).subtract(trainingGrader.getVector(j).power(2)).sum();
				
				Matrix layerDelta = new Matrix(new double[weights.length][]);
				layerDelta.setVector(weights.length - 1, layers.getVector(layers.size() - 1).subtract(trainingGrader.getVector(j)));
				//System.out.println("\n" + layers.getVector(layers.size() - 1));
				
				for (int f = weights.length - 1; f > 0; f--) {
					layerDelta.setVector(f - 1, layerDelta.getVector(f).dot(weights[f].T()).multiply(relu2Deriv(layers.getVector(f))));
				}
				
				//System.out.println(layerDelta.getVector(0));
				for (int f = 0; f < weights.length; f++) {
					//System.out.println("\n" + weights[f]);
					//System.out.println(weights[f].subtract(layers.getVector(f).T().dot(layerDelta.getVector(f))).multiply(alpha));
					weights[f] = weights[f].subtract(layers.getVector(f).T().dot(layerDelta.getVector(f))).multiply(alpha);
				}
			}
		return out;
	}
	
	private static void printDMatrix(Matrix[] x) {
		for (int i = 0; i < x.length; i++)
			System.out.println(x[i]);
	}
	
	public Matrix[] getWeights() {return weights;};
	public void setTrainingMsg1(boolean x) {printTrainMessage = x;};
	public void setTrainingMsg2(boolean x) {printTrainMessage2 = x;};
}
