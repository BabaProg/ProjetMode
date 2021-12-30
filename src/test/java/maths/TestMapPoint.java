package maths;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestMapPoint {

    // Cette classe sert uniquement à avoir une approximation du temps pour appliquer un calcul sur toute l'arrayPoint

    ArrayPoint mapPoint = new ArrayPoint();
    Vecteur v;

    @BeforeEach
    void creer_point() {
        for (double i = 0; i < 1000000; i++) {
            Point p =new Point(i,i,i);
            //mapPoint.put((int)i,p);
        }
        v=new Vecteur(1,2,3);
    }

    @Test
    void test_homothetie() {
        mapPoint.homothetie(2);
        assertTrue(true);
    }

    @Test
    void test_translation() {
        mapPoint.translation(v);
        assertTrue(true);
    }

    @Test
    void test_symetrieX() {
        mapPoint.symetrieSurX();
        assertTrue(true);
    }

    @Test
    void test_symetrieY() {
        mapPoint.symetrieSurY();
        assertTrue(true);
    }

    @Test
    void test_symetrieZ() {
        mapPoint.symetrieSurX();
        assertTrue(true);
    }

    @Test
    void test_rotationX() {
        mapPoint.rotationXDegree(90);
        assertTrue(true);
    }

    @Test
    void test_rotationY() {
        mapPoint.rotationYDegree(90);
        assertTrue(true);
    }

    @Test
    void test_rotationZ() {
        mapPoint.rotationZDegree(90);
        assertTrue(true);
    }

}
