package maths;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolygonTest {

    Repere repere = Repere.getInstance(0, 0, 0);
    Polygon polygon = null;

    @BeforeEach
    void inti() {
        repere.clearList();
        polygon = new Polygon(repere);
    }

    @Test
    void testSurSegment() {
        Point p = new Point(0, 0, 0);
        Point q = new Point(1, 1, 1);
        Point r = new Point(2, 2, 2);

        assertTrue(polygon.surSegement(p, q, r));
        assertFalse(polygon.surSegement(p, r, q));
    }

    @Test
    void testOrientationTripletCasZero() {
        Point p = new Point(0, 0, 0);
        Point q = new Point(0, 0, 0);
        Point r = new Point(0, 0, 0);
        /**
         * Dans orientation val est égale à 0
         */
        assertEquals(0, polygon.orientation(p, q, r));
    }

    @Test
    void testOrientationTripletCasUn() {
        Point p = new Point(1, 2, 0);
        Point q = new Point(2, 3, 0);
        Point r = new Point(6, 5, 0);
        /**
         * Dans orientation val est positif
         */
        assertEquals(1, polygon.orientation(p, q, r));
    }

    @Test
    void testOrientationTripletCasDeux() {
        Point p = new Point(1, 1, 0);
        Point q = new Point(1, 2, 0);
        Point r = new Point(-2, 1, 0);
        /**
         * Dans orientation val est négatif
         */
        assertEquals(2, polygon.orientation(p, q, r));
    }

    @Test
    void testSeCroise() {
        Point p1 = new Point(3, 4, 0);
        Point q1 = new Point(7, 4, 0);
        Point p2 = new Point(5, 6, 0);
        Point q2 = new Point(5, 2, 0);

        assertTrue(polygon.seCroise(p1, q1, p2, q2));
        assertFalse(polygon.seCroise(p1, q2, p2, q1));
    }

    @Test
    void testEstInterieur() {
        Point[] points = {
                new Point(1, 1, 0),
                new Point(1, 2, 0),
                new Point(-2, 1, 0),
                new Point(-2, 1, 1)
        };

        Point p = new Point(1, 1, 0);
        Point p2 = new Point(1, 1, 1);

        assertFalse(polygon.estInterieur(points, 2, p));
        assertTrue(polygon.estInterieur(points, 42, p));
        assertTrue(polygon.estInterieur(points, 42, p2));
    }

    // TODO by Cyril
    @Test
    void testInitializeColor() {

    }

    @Test
    void testGetColor() {
        Repere repere2 = Repere.getInstance(5, 5, 5);
        Point p = new Point(4, 4, 4);
        Polygon polygon2 = new Polygon(repere2);
        Color c = p.getColor();
        assertNotNull(c);
    }

    @Test
    void testAddPoint() {
        polygon.addPoint(1);
        assertEquals(1, polygon.getPoints().size());
    }

    @Test
    void testRemovePoint() {
        Integer point = 1;
        polygon.addPoint(point);
        assertEquals(1, polygon.getPoints().size());
        polygon.removePoint(point);
        assertEquals(0, polygon.getPoints().size());
    }

    @Test
    void testGetPointX() {
        Polygon pl = new Polygon(repere);
        assertNotNull(pl.getPointsX());
    }

    @Test
    void testGetPointY() {
        Polygon pl = new Polygon(repere);
        assertNotNull(pl.getPointsY());
    }

    @Test
    void testGetPointZ() {
        Polygon pl = new Polygon(repere);
        assertNotNull(pl.getPointsZ());
    }

    @Test
    void testGetPointDisplayX() {
        Polygon pl = new Polygon(repere);
        assertNotNull(pl.getPointsDisplayX());
    }

    @Test
    void testGetPointDisplayY() {
        Polygon pl = new Polygon(repere);
        assertNotNull(pl.getPointsDisplayY());
    }

    @Test
    void testGetTotalX() {
        Polygon pl = new Polygon(repere);
        assertEquals(0, pl.getTotalX());
    }

    @Test
    void testGetTotalY() {
        Polygon pl = new Polygon(repere);
        assertEquals(0, pl.getTotalY());
    }

    @Test
    void testGetTotalZ() {
        Polygon pl = new Polygon(repere);
        assertEquals(0, pl.getTotalZ());
    }

    @Test
    void testSize() {
        assertEquals(0, polygon.size());
    }

    private Polygon createPolygonWithPoint(Repere repere){
        Polygon pl = new Polygon(repere);
        Point p = new Point(1.0,1.0,1.0,0,0,0);
        Point p1 = new Point(2.0,2.0,2.0,0,0,0);
        Point p2 = new Point(3.0,3.0,3.0,0,0,0);
        repere.addPoint(p);
        repere.addPoint(p1);
        repere.addPoint(p2);
        pl.addPoint(0);
        pl.addPoint(1);
        pl.addPoint(2);
        return pl;
    }

    @Test
    void test_initialize_color(){
        Polygon pl = createPolygonWithPoint(repere);
        pl.initializeColor();
        assertEquals(Color.BLACK, pl.getColor());
    }

    @Test
    void test_to_string(){
        Polygon pl = createPolygonWithPoint(repere);
        assertEquals("[0, 1, 2]",pl.toString());
    }

    @Test
    void test_get_total_x(){
        Polygon pl = createPolygonWithPoint(repere);
        assertEquals(6,pl.getTotalX());
    }
    @Test
    void test_get_total_y(){
        Polygon pl = createPolygonWithPoint(repere);
        assertEquals(6,pl.getTotalY());
    }

    @Test
    void test_get_total_z(){
        Polygon pl = createPolygonWithPoint(repere);
        assertEquals(6,pl.getTotalZ());
    }

    @Test
    void test_constructor_with_polygon(){
        Polygon pl = createPolygonWithPoint(repere);
        Polygon polygon1 = new Polygon(pl);
        assertEquals(pl.getPoints(),polygon1.getPoints());
        assertEquals(pl.getColor(),polygon1.getColor());
    }

    @Test
    void test_normale(){
        Polygon pl = new Polygon(repere);
        Point p = new Point(1.0,1.0,1.0,0,0,0);
        Point p1 = new Point(2.0,2.0,2.0,0,0,0);
        Point p2 = new Point(2.0,3.0,4.0,0,0,0);
        repere.addPoint(p);
        repere.addPoint(p1);
        repere.addPoint(p2);
        pl.addPoint(0);
        pl.addPoint(1);
        pl.addPoint(2);
        pl.refreshNormale();
        assertEquals(pl.getNormale(), new Vecteur(1/Math.sqrt(6),-2/Math.sqrt(6), 1/Math.sqrt(6)));
    }
}