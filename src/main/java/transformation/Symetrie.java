package transformation;

public enum Symetrie {

	SX(new double[][] { { -1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }),
	SY(new double[][] { { 1, 0, 0, 0 }, { 0, -1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } }),
	SZ(new double[][] { { 1, 0, 0, 0 }, { 0.0, 1, 0, 0 }, { 0, 0, -1, 0 }, { 0, 0, 0, 1} });

	private double[][] matrix;

	/**
	 * Créé une matrice de Symetrie
	 * @param un double[][] matrix
	 */
	
	Symetrie(double[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * Getter de la matrice de symétrie
	 * @return un double[][] de la matrice
	 */
	
	public double[][] getSymetrie() {
		return matrix;
	}
}
