package maths;

import java.util.Arrays;

public class Matrice {

	/**
	 * Notre matrice (tableau de double à 2 dimensions)
	 */
	public double[][] matrice;
	/**
	 * La longueur de la première dimension (int)
	 */
	protected int longueurX;
	/**
	 * La longueur de la deuxième dimension (int)
	 */
	protected int longueurY;

	/**
	 * La matrice d'origine
	 */
	public Matrice() {
		this(new double[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
	}

	/**
	 * On initialise une autre matrice
	 * @param other (double[][])
	 */
	public Matrice(double[][] other) {
		longueurX = other.length;
		longueurY = other[0].length;
		matrice = new double[longueurX][longueurY];
		System.arraycopy(other, 0, matrice, 0, other.length);
	}

	/**
	 * On initialise une autre matrice
	 * @param other (double[])
	 */
	public Matrice(double[] other) {
		longueurX = other.length;
		longueurY = 1;
		matrice = new double[longueurX][longueurY];
		for (int x = 0; x < other.length; ++x) {
			matrice[x][0] = other[x];
		}
	}

	/**
	 * Addition d'une matrice avec une constante
	 * @param constane
	 */
	public void addition(int constane) {
		for (int x = 0; x < this.longueurX; ++x) {
			for (int y = 0; y < this.longueurY; ++y) {
				this.matrice[x][y] += constane;
			}
		}
	}

	/**
	 * Addition d'une matrice avec une autre (via tableau)
	 * @param tableau
	 */
	public void addition(double[][] tableau) {
		if (this.longueurX != tableau.length && this.longueurY != tableau[0].length) {
			return;
		}
		for (int x = 0; x < this.longueurX; ++x) {
			for (int y = 0; y < this.longueurY; ++y) {
				this.matrice[x][y] += tableau[x][y];
			}
		}
	}

	/**
	 * On additionne 2 matrices entre elles (via Matrice)
	 * @param other (Matrice)
	 */
	public void addition(Matrice other) {
		this.addition(other.matrice);
	}

	/**
	 * Soustraction d'une matrice avec une constante
	 * @param constante
	 */
	public void soustraction(int constante) {
		for (int x = 0; x < this.longueurX; ++x) {
			for (int y = 0; y < this.longueurY; ++y) {
				this.matrice[x][y] -= constante;
			}
		}
	}

	/**
	 * Soustraction d'une matrice avec une autre
	 * @param tableau (double[][])
	 */
	public void soustraction(double[][] tableau) {
		if (this.longueurX != tableau.length && this.longueurY != tableau[0].length) {
			return;
		}
		for (int x = 0; x < this.longueurX; ++x) {
			for (int y = 0; y < this.longueurY; ++y) {
				this.matrice[x][y] -= tableau[x][y];
			}
		}
	}

	/**
	 * On soustrait 2 matrices entre elles (via Matrice)
	 * @param other (Matrice)
	 */
	public void soustraction(Matrice other) {
		this.soustraction(other.matrice);
	}

	/**
	 * Multiplication d'une matrice avec une constante
	 * @param constante (double)
	 */
	public void multiplication(double constante) {
		for (int x = 0; x < this.longueurX; ++x) {
			for (int y = 0; y < this.longueurY; ++y) {
				this.matrice[x][y] *= constante;
			}
		}
	}

	/**
	 * Mulitplication d'une matrice avec une autre
	 * @param tableau2 (double[][])
	 * @return le r�sultat de la multiplication entre la matrice A et la matrice B
	 */
	public Matrice multiplication(double[][] tableau2) {
		if (this.longueurX != tableau2[0].length && this.longueurY != tableau2.length) {
			return null;
		}
		double[][] tableautmp = new double[this.longueurX][this.longueurY];
		for (int x = 0; x < this.longueurX; ++x) {
			for (int y = 0; y < this.longueurY; ++y) {
				tableautmp[x][y] = somme(tableau2, x, y);
			}
		}
		return new Matrice(tableautmp);
	}

	/**
	 * Calcul de la somme des valeurs de la m�me ligne / colonne
	 * @param other (double[][])
	 * @param ligne (int)
	 * @param colonne (int)
	 * @return la somme des valeurs de la m�me ligne / colonne
	 */
	public double somme(double[][] other, int ligne, int colonne) {
		double somme = 0;
		for (int i = 0; i < this.longueurX; ++i) {
			somme += this.matrice[i][colonne] * other[ligne][i];
		}
		return somme;
	}

	/**
	 * On multiplie 2 matrices entre elles (via Matrice)
	 * @param other (Matrice)
	 */
	public Matrice multiplication(Matrice other) {
		return this.multiplication(other.matrice);
	}

	/*
	 * Affiche les matrices
	 */
	@Override
	public String toString() {
		StringBuilder arrays = new StringBuilder();
		for (double[] d : matrice) {
			if (matrice[matrice.length-1]==d){
				arrays.append(Arrays.toString(d)+"\n");
			}else{
				arrays.append(Arrays.toString(d)+"\n        ");
			}
		}
		return "matrice=" + arrays;
	}

}