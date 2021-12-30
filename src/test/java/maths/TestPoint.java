package maths;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TestPoint {

	ArrayPoint arrayPoint = new ArrayPoint();
	Point p;

	Point otherAbove = new Point(2.0,2.0,2.0);
	Point otherEquals = new Point(1.0,1.0,1.0);
	Point otherLower = new Point(0.0,0.0,0.0);

	@BeforeEach
	void creer_point() {
		p = new Point(1,1,1);
		Point p1 = new Point(p);
		arrayPoint.add(0,p);
		arrayPoint.add(1,p1);
		ArrayPoint.mtxTransformation.clear();
	}

	@Test
	void test_homothetie() {
		arrayPoint.homothetie(5);
		assertTrue(p.equals(new Point(5.0,5.0,5.0)));
	}

	@Test
	void test_translation() {
		arrayPoint.translation(new Vecteur(1,2,3));
		assertTrue(p.equals(new Point(2.0,3.0,4.0)));
	}

	@Test
	void test_symetrieX() {
		arrayPoint.symetrieSurX();
		assertEquals(new Point(-1.0,1.0,1.0),p);
	}

	@Test
	void test_symetrieY() {
		arrayPoint.symetrieSurY();
		assertTrue(p.equals(new Point(1.0,-1.0,1.0)));
	}

	@Test
	void test_symetrieZ() {
		arrayPoint.symetrieSurZ();
		assertTrue(p.equals(new Point(1.0,1.0,-1.0)));
	}

	@Test
	void test_rotationX() {
		arrayPoint.rotationXDegree(90);
		assertEquals(new Point(1.0,-1.0,1.0),p);
	}

	@Test
	void test_rotationY() {
		arrayPoint.rotationYDegree(90);
		assertEquals(new Point(-1.0,1.0,1.0),p);
	}

	@Test
	void test_rotationZ() {
		arrayPoint.rotationZDegree(90);
		assertEquals(new Point(-1.0,1.0,1.0),p);
	}

	@Test
	void test_to_string(){
		assertEquals("(x :1.0, y :1.0, z :1.0)\n",p.toString());
	}

	@Test
	void test_setter(){
		p.setX(5.0);
		p.setY(10.0);
		p.setZ(15.0);
		assertEquals(new Point(5.0,10.0,15.0),p);
	}

	@Test
	void test_get_display_X(){
		assertEquals(p.getDisplayX(),1.0);
	}

	@Test
	void test_get_display_Y(){
		assertEquals(p.getDisplayY(),-1.0);
	}

	@Test
	void test_is_hidden(){
		assertFalse(p.isHidden());
	}

	@Test
	void test_set_hidden(){
		p.setHidden(true);
		assertTrue(p.isHidden());
	}

	@Test
	void test_multiplacte_with_double(){
		p.mutiplicate(5);
		assertEquals(new Point(5.0,5.0,5.0),p);
	}

	@Test
	void test_comparator_x(){
		Point.ComparatorPointX comparatorPointX = new Point.ComparatorPointX();
		assertEquals(-1, comparatorPointX.compare(p, otherAbove));
		assertEquals(0, comparatorPointX.compare(p, otherEquals));
		assertEquals(1, comparatorPointX.compare(p, otherLower));
	}

	@Test
	void test_comparator_y(){
		Point.ComparatorPointY comparatorPointY = new Point.ComparatorPointY();
		assertEquals(-1, comparatorPointY.compare(p, otherAbove));
		assertEquals(0, comparatorPointY.compare(p, otherEquals));
		assertEquals(1, comparatorPointY.compare(p, otherLower));
	}

	@Test
	void test_comparator_z(){
		Point.ComparatorPointZ comparatorPointZ = new Point.ComparatorPointZ();
		assertEquals(-1, comparatorPointZ.compare(p, otherAbove));
		assertEquals(0, comparatorPointZ.compare(p, otherEquals));
		assertEquals(1, comparatorPointZ.compare(p, otherLower));
	}
}