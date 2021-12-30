package transformation;

public enum Rotation {
	RX(new double[][] { { 1, 0, 0, 0 }, { 0, -1, -1, 0 }, { 0, -1, -1, 0 }, { 0, 0, 0, 1 } }),
	RY(new double[][] { { -1, 0, -1, 0 }, { 0, 1, 0, 0 }, { -1, 0, -1, 0 }, { 0, 0, 0, 1 } }),
	RZ(new double[][] { { -1, -1, 0, 0 }, { -1, -1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });

	private double[][] matrix;
	
	/**
	 * Créé une matrice de rotation
	 * @param un double[][] matrix
	 */
	
	Rotation(double[][] matrix) {
		this.matrix = matrix;
	}
	
	/**
	 * Getter de la matrice de rotation
	 * @return un double[][] de la matrice
	 */
	
	public double[][] getRotation() {
		return matrix;
	}
}
