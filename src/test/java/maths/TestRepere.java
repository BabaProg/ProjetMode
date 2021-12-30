package maths;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

public class TestRepere {
    static Repere repere;

    @BeforeAll
    static void creation_repere() {
        repere = Repere.getInstance(1.0, 1.0, 1.0);
    }

    @Test
    void test_zoom() {
        if (repere != null) {
            repere.zoom();
            assertEquals(0.75, repere.getPointX());
            assertEquals(0.75, repere.getPointY());
            assertEquals(0.75, repere.getPointZ());
        }
    }

    @Test
    void test_dezoom() {
        if(repere != null) {
            repere.dezoom();
            assertEquals(1.00, repere.getPointX());
            assertEquals(1.00, repere.getPointY());
            assertEquals(1.00, repere.getPointZ());
        }
    }

    @Test
    void test_add_point() {
        if(repere != null) {
            repere.addPoint(new Point(1.0, 1.0, 1.0));
            assertEquals(1, repere.getPoints().size());
            assertTrue(repere.getPoints().get(0).equals(new Point(1.0, 1.0, 1.0)));

            repere.addPoint(new Point(2.0, 2.0, 2.0));
            assertEquals(2, repere.getPoints().size());
            assertTrue(repere.getPoints().get(1).equals(new Point(2.0, 2.0, 2.0)));
        }
    }

    @Test
    void test_get_singleton(){
        assertEquals(repere,Repere.getSingleton());
    }

    @Test
    void test_get_x(){
        assertEquals(1.0,repere.getPointX());
    }

    @Test
    void test_get_y(){
        assertEquals(1.0,repere.getPointY());
    }

    @Test
    void test_get_z(){
        assertEquals(1.0,repere.getPointZ());
    }

    @Test
    void test_set_and_get_name(){
        repere.setName("name");
        assertEquals("name",repere.getName());
    }

    @Test
    void test_set_and_get_description(){
        repere.setDescription("description");
        assertEquals("description",repere.getDescription());
    }

    @Test
    void test_set_and_get_date(){
        repere.setDate(LocalDate.now());
        assertEquals(LocalDate.now(),repere.getDate());
    }

    @Test
    void test_set_and_get_auteur(){
        repere.setAuteur("Auteur");
        assertEquals("Auteur", repere.getAuteur());
    }

    @Test
    void test_get_points_transformes(){
        assertEquals(repere.getPoints(),repere.getPointsTransformes());
    }
    @Test
    void test_reset_repere(){
        repere.resetRepere();
        assertEquals(new ArrayPoint(),repere.getPoints());
        assertEquals(new ArrayPoint(),repere.getPointsTransformes());
        assertEquals(new ArrayList<Polygon>(),repere.getPolygones());
    }

    @Test
    void test_clear_list(){
        repere.clearList();
        assertEquals(new ArrayPoint(),repere.getPoints());
        assertEquals(new ArrayPoint(),repere.getPointsTransformes());
        assertEquals(new ArrayList<Polygon>(),repere.getPolygones());
    }

    @Test
    void test_to_string(){
        Polygon pl = new Polygon(repere);
        pl.addPoint(0);
        pl.addPoint(1);
        pl.addPoint(2);
        repere.addPolygon(pl);
        assertEquals("[[0, 1, 2]]",repere.toString());
    }

    @Test
    void test_get_polygones(){
        repere.clearList();
        assertEquals(new ArrayList<Polygon>(),repere.getPolygones());
    }
    @Test
    void test_get_polygonessize(){
        repere.clearList();
        assertEquals(0,repere.getPolygonesSize());
    }

}
