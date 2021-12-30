package maths;

public class Coordonnees {
	/**
	 * Notre système de coordonné pour un monde en 3 dimensions
	 */
	protected double xCoordonnee;
	protected double yCoordonnee;
	protected double zCoordonnee;

	/**
	 * Constructeur de la classe Coordonnees
	 * @param xCoordonnee (double)
	 * @param yCoordonnee (double)
	 * @param zCoordonnee (double)
	 */
	public Coordonnees(double xCoordonnee, double yCoordonnee, double zCoordonnee) {
		this.xCoordonnee = xCoordonnee;
		this.yCoordonnee = yCoordonnee;
		this.zCoordonnee = zCoordonnee;
	}

	/**
	 * Affichage des nos coordonnées
	 * @return l'affichage des points x, y et z
	 */
	public String toString() {
		return xCoordonnee + ", " + yCoordonnee + ", " + zCoordonnee;
	}

	/**
	 * On récupère notre x
	 * @return x
	 */
	public double getxCoordonnee() {
		return xCoordonnee;
	}

	/**
	 * On récupère notre y
	 * @return y
	 */
	public double getyCoordonnee() {
		return yCoordonnee;
	}

	/**
	 * On récupère notre z
	 * @return z
	 */
	public double getzCoordonnee() {
		return zCoordonnee;
	}

	/**
	 * On initialise notre x
	 * @param xCoordonnee (double)
	 */
	public void setxCoordonnee(double xCoordonnee) {
		this.xCoordonnee = xCoordonnee;
	}

	/**
	 * On initialise notre y
	 * @param yCoordonnee (double)
	 */
	public void setyCoordonnee(double yCoordonnee) {
		this.yCoordonnee = yCoordonnee;
	}

	/**
	 * On initialise notre z
	 * @param zCoordonnee (double)
	 */
	public void setzCoordonnee(double zCoordonnee) {
		this.zCoordonnee = zCoordonnee;
	}
}
