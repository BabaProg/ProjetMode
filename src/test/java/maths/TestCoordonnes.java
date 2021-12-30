package maths;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCoordonnes {

    Coordonnees c;

    @BeforeEach
    void creation_point(){
        c = new Coordonnees(1.0,2.0,3.0);
    }

    @Test
    void test_getter(){
        assertEquals(1.0,c.getxCoordonnee());
        assertEquals(2.0,c.getyCoordonnee());
        assertEquals(3.0,c.getzCoordonnee());
    }

    @Test
    void test_setter(){
        c.setxCoordonnee(11.0);
        assertEquals(11.0,c.getxCoordonnee());
        c.setyCoordonnee(12.0);
        assertEquals(12.0,c.getyCoordonnee());
        c.setzCoordonnee(13.0);
        assertEquals(13.0,c.getzCoordonnee());
    }

    @Test
    void test_to_string(){
        assertEquals("1.0, 2.0, 3.0", c.toString());
    }
}
