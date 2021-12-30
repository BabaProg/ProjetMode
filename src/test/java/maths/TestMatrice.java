package maths;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestMatrice {
	
	Matrice m1;
	Matrice m2;
	Matrice m3;
	
	@BeforeEach
	void creation_matrice() {
		double [][] tableau1 = new double[3][3];
		double [][] tableau2 = new double[3][3];
		for(int x=0;x<tableau1.length;++x) {
			for(int y=0;y<tableau1[x].length;++y) {
				tableau1[x][y]=2;
				tableau2[x][y]=5;
			}
		}
		double [][] tableau3 = new double[5][5];
		for(int x=0;x<tableau3.length;++x) {
			for(int y=0;y<tableau3[x].length;++y) {
				tableau3[x][y]=2;
			}
		}
		
		m1 = new Matrice(tableau1);
		m2 = new Matrice(tableau2);
		//tableau1 est remplie de 2 [3][3]
		//tableau2 est remplie de 5 [3][3]

		m3 = new Matrice(tableau3);
		//tableau3 est remplie de 2 [5][5]
	}
	
	@Test
	void test_addition_avec_entier() {
		m1.addition(2);
		//tableau1 remplie  de 2 donc après l'addition il ne doit y avoir que des 4
		for(int x=0;x<m1.longueurX;++x) {
			for(int y=0;y<m1.longueurY;++y) {
				assertEquals(m1.matrice[x][y],4);
			}
		}
	}
	@Test
	void test_addition_avec_autre_tableau() {
		m1.addition(m2);
		//tableau1 remplie  de 2, tableau2 remplie de 5 donc tableau 1 remplie de 7
		m1.addition(m3);// cela ne fais rien car ils n'ont pas les même dimension
		for(int x=0;x<m1.longueurX;++x) {
			for(int y=0;y<m1.longueurY;++y) {
				assertEquals( m1.matrice[x][y],7);
			}
		}
	}
	
	@Test
	void test_soustraction_avec_entier() {
		m1.soustraction(2);
		//tableau1 remplie  de 2 donc après l'addition il ne doit y avoir que des 4
		for(int x=0;x<m1.longueurX;++x) {
			for(int y=0;y<m1.longueurY;++y) {
				assertEquals(m1.matrice[x][y],0);
			}
		}
	}
	
	@Test
	void test_soustraction_avec_autre_tableau() {
		m1.soustraction(m2);
		//tableau1 remplie  de 2, tableau2 remplie de 5 donc tableau 1 remplie de 7
		m1.soustraction(m3);// cela ne fais rien car ils n'ont pas les même dimension
		for(int x=0;x<m1.longueurX;++x) {
			for(int y=0;y<m1.longueurY;++y) {
				assertEquals(m1.matrice[x][y],-3);
			}
		}
	}

	
	@Test
	void test_multiplication_avec_autre_tableau() {
		Matrice m = m1.multiplication(m2);
		//tableau1 remplie  de 2, tableau2 remplie de 5 donc tableau 1 remplie de 7
		for(int x=0;x<m.longueurX;++x) {
			for(int y=0;y<m.longueurY;++y) {
				assertEquals(m.matrice[x][y],30);
			}
		}

		m = m1.multiplication(m3);// cela retourne null car ils n'ont pas les même dimension
		assertNull(m);
	}

	@Test
	void test_to_string(){
		String expected = "matrice=[2.0, 2.0, 2.0]\n        [2.0, 2.0, 2.0]\n        [2.0, 2.0, 2.0]\n";
		assertEquals(expected,m1.toString());
	}
}
