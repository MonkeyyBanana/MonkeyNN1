package Monkey.util;

public class Matrix {
	
	private double[][] matrix;
	
	public Matrix(double[][] mat) {
		matrix = mat;
	}
	
	public Matrix(int a, int b) {
		matrix = new double[a][b];
	}
	
	public Matrix(double[] vector) {
		matrix = new double[1][vector.length];
		matrix[0] = vector;
	}
	
	public Matrix(int a) {
		matrix = new double[1][a];
	}
	
	
	public Matrix add(Object other) {
		double[][] out = matrix;
		double[][] newInstance = ((Matrix) other).getArray();
		
		for (int i = 0; i < out.length; i++) 
			for (int j = 0; j < out[i].length; j++) 
				out[i][j] += newInstance[i][j];
		
		return new Matrix(out);
	}
	
	public double sum(Object other) {
		double out = 0;
		double[][] newInstance = ((Matrix) other).getArray();
		
		for (int i = 0; i < newInstance.length; i++) 
			for (int j = 0; j < newInstance[i].length; j++) 
				out += newInstance[i][j] + matrix[i][j];
		
		return out;
	}
	
	public double sum() {
		double out = 0;
		
		for (int i = 0; i < matrix.length; i++)
			for (int j = 0; j < matrix[i].length; j++)
				out += matrix[i][j];
		
		return out;
	}
	
	public Matrix subtract(Object other) {
		double[][] out = matrix;
		double[][] newInstance = ((Matrix) other).getArray();
		
		for (int i = 0; i < out.length; i++) 
			for (int j = 0; j < out[i].length; j++) 
				out[i][j] -= newInstance[i][j];
		
		return new Matrix(out);
	}
	
	public Matrix multiply(Object other) {
		double[][] out = matrix;
		double[][] newInstance = ((Matrix) other).getArray();
		
		for (int i = 0; i < out.length; i++) 
			for (int j = 0; j < out[i].length; j++) 
				out[i][j] *= newInstance[i][j];
		
		return new Matrix(out);
	}
	
	public Matrix multiply(double multiple) {
		double[][] out = matrix;
		
		for (int i = 0; i < out.length; i++) 
			for (int j = 0; j < out[i].length; j++) 
				out[i][j] *= multiple;
		
		return new Matrix(out);
	}
	
	public Matrix divide(Object other) {
		double[][] out = matrix;
		double[][] newInstance = ((Matrix) other).getArray();
		
		for (int i = 0; i < out.length; i++) 
			for (int j = 0; j < out[i].length; j++) 
				out[i][j] /= newInstance[i][j];
		
		return new Matrix(out);
	}
	
	public Matrix dot(Object other) {
		double[][] newInstance = ((Matrix) other).getArray();
		double[][] out = new double[matrix.length][newInstance[0].length];
		
	    for (int row = 0; row < out.length; row++) {
	        for (int col = 0; col < out[row].length; col++) {
	            out[row][col] = multiplyMatricesCell(matrix, newInstance, row, col);
	        }
	    }

	    return new Matrix(out);
	}
	
	private double multiplyMatricesCell(double[][] firstMatrix, double[][] secondMatrix, int row, int col) {
	    double cell = 0;
	    for (int i = 0; i < secondMatrix.length; i++) {
	        cell += firstMatrix[row][i] * secondMatrix[i][col];
	    }
	    
	    return cell;
	}
	
	public Matrix power(int pow) {
		double[][] out = matrix;
		for (int i = 0; i < out.length; i++) 
			for (int j = 0; j < out[i].length; j++) 
				out[i][j] = Math.pow(out[i][j], pow);
		
		return new Matrix(out);
	}
	
	public Matrix transpose() {
		double[][] temp = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                temp[j][i] = matrix[i][j];
        
        return new Matrix(temp);
	}
	
	public Matrix T() {
		return transpose();
	}
	
	public int size() {
		return matrix.length;
	}
	
	public int size(int index) {
		return matrix[index].length;
	}
	
	public void setVector(int index, Matrix x) {matrix[index] = x.getArray(0);};
	public void setArray(double[][] x) {matrix = x;};
	public void setArray(int index, double[] x) {matrix[index] = x;};
	public void setArray(int indexA, int indexB, double x) {matrix[indexA][indexB] = x;};
	
	public Matrix getVector(int index) {return new Matrix(getArray(index));};
	public double[][] getArray() {return matrix;};
	public double[] getArray(int x) {return matrix[x];};
	public double getArray(int x, int y) {return matrix[x][y];};
	public void toArray(double[][] matrix) {this.matrix = matrix;};
	
	public String toString() {
		String out = "";
		for (int i = 0; i < matrix.length; i++) {
			out += "[";
			for (int j = 0; j < matrix[i].length; j++) {
				out += matrix[i][j] + ", ";
			}
			out = out.substring(0, out.length() - 2) + "]\n";
		}
		
		return out;
	}
}
