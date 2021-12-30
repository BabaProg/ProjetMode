package maths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

public class TestVecteur {

    Vecteur vecteur1;
    Vecteur vecteur2;
    Vecteur vecteur3;
    Vecteur vecteur4;

    @BeforeEach
    void init_vecteur(){
        vecteur1 = new Vecteur(1,2,3);
        vecteur2 = new Vecteur(3,4,7);
        vecteur3 = new Vecteur(10,20,30);
        vecteur4 = new Vecteur(6,7,8);
    }

    @Test
    void test_produit_scalaire(){
        assertEquals(32,Vecteur.produitScalaire(vecteur1,vecteur2));
        assertEquals(320,Vecteur.produitScalaire(vecteur2,vecteur3));
        assertEquals(140,Vecteur.produitScalaire(vecteur1,vecteur3));
    }

    @Test
    void test_cosinus() throws ParseException {
        assertEquals(32/(Math.sqrt(14)*Math.sqrt(74)), Vecteur.getCosinusBetweenTwoVector(vecteur1,vecteur2));
    }

    @Test
    void test_produit_vectoriel(){
        assertEquals(new Vecteur(-5,10,-5),Vecteur.produitVectoriel(vecteur1,vecteur4));
    }

    @Test
    void test_norme(){
        assertEquals(Math.sqrt(14),vecteur1.getNorme());
    }
}
