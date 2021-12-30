package maths;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Point {

	/**
	 * On récupère notre matrice
	 */
	protected Matrice matrice;
	/**
	 * On récupère notre couleur
	 */
	protected Color color;
	/**
	 * On regarde si le point est caché ou pas (par défaut false)
	 */
	protected boolean hidden = false;
	protected List<Polygon> polygonList;

	public Point(double pointX, double pointY, double pointZ, int red, int green, int blue,Polygon polygon) {
		double[] otherMatrice = { pointX, pointY, pointZ, 1 };
		matrice = new Matrice(otherMatrice);
		color = Color.rgb(red, green, blue);
		polygonList.add(polygon);
	}
	public Point(double pointX, double pointY, double pointZ, int red, int green, int blue,List<Polygon> polygons) {
		double[] otherMatrice = { pointX, pointY, pointZ, 1 };
		matrice = new Matrice(otherMatrice);
		color = Color.rgb(red, green, blue);
		polygonList = polygons;
	}

	public Point(double pointX, double pointY, double pointZ, Polygon polygon) {
		this(pointX, pointY, pointZ, 0, 0, 0,polygon);
	}

	public Point(double pointX, double pointY, double pointZ) {
		this(pointX, pointY, pointZ, 0, 0, 0,new ArrayList<Polygon>());
	}
	public Point(double pointX, double pointY, double pointZ, List<Polygon> polygons) {
		this(pointX, pointY, pointZ, 0, 0, 0,polygons);
	}
	public Point(Point point) {
		this(point.getX(), point.getY(), point.getZ(),point.getPolygonList());
	}

	public Point(double xAxis, double yAxis, double zAxis, int red, int green, int blue) {
		this(xAxis,yAxis,zAxis,red,green,blue,new ArrayList<Polygon>());
	}

	/**
	 * Affichage des points
	 */
	@Override
	public String toString() {
		return "(x :" + getX() + ", y :" + getY() + ", z :" + getZ()+")\n";
	}

	/**
	 * On r�cup�re le x
	 * @return r�cup�re le x depuis la matrice
	 */
	public double getX() {
		return matrice.matrice[0][0];
	}

	/**
	 * On r�cup�re le y
	 * @return r�cup�re le y depuis la matrice
	 */
	public double getY() {
		return matrice.matrice[1][0];
	}

	/**
	 * On r�cup�re le z
	 * @return r�cup�re le z depuis la matrice
	 */
	public double getZ() {
		return matrice.matrice[2][0];
	}

	public List<Polygon> getPolygonList() {
		return polygonList;
	}

	public void addPolygon(Polygon p){
		polygonList.add(p);
	}

	public void setX(double value) {
		matrice.matrice[0][0] = value;
	}
	/**
	 * On met à jour notre Y dans la matrice
	 * @param value (double)
	 */
	public void setY(double value) {
		matrice.matrice[1][0] = value;
	}
	/**
	 * On met à jour notre Z dans la matrice
	 * @param value (double)
	 */
	public void setZ(double value) {
		matrice.matrice[2][0] = value;
	}

	/**
	 * On r�cup�re la couleur d'un point
	 * @return le rgb d'un point
	 */
	public Color getColor() {
		return color;
	}

	public Color getColorWithPolygon(){
		double red = 0;
		double green = 0;
		double blue = 0;
		for (Polygon p:polygonList) {
			red += p.getColor().getRed();
			green += p.getColor().getGreen();
			blue += p.getColor().getBlue();
 		}
		return new Color(red/polygonList.size(),green/ polygonList.size(),blue/ polygonList.size(),1);
	}

	/**
	 * On r�cup�re le x pour pouvoir l'afficher
	 * @return le x
	 */
	public double getDisplayX() {
		return getX();
	}

	/**
	 * On r�cup�re le y pour pouvoir l'afficher
	 * @return le y
	 */
	public double getDisplayY() {
		return -1 * getY();
	}

	/**
	 *
	 * @return true si le point est caché faus sinon
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * On modifie si un point est caché ou pas
	 * @param hidden
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/**
	 * On v�rifie si le point est �gale au point pass� en param�tre
	 * @param other
	 * @return {@code true} si le point en param�tre est �gale � 
	 * this sinon il retourne {@code false}
	 */
	public boolean equals(Point other) {
		return getX() == other.getX() && getY() == other.getY() && getZ() == other.getZ();
	}

	/**
	 * On multiplie la matrice courante avec celle pass� en pram�tre
	 * @param other
	 */
	public void mutiplicate(Matrice other) {
		matrice = matrice.multiplication(other);
	}

	/**
	 * On multiplie la matrice courante avec une constante
	 * @param other
	 */
	public void mutiplicate(double other) {
		matrice.multiplication(other);
	}

	/**
	 * On compare si un point est plus grand que l'autre via les X
	 */
	public static class ComparatorPointX implements Comparator<Point> {
		@Override
		public int compare(Point point1, Point point2) {
			double p1V = point1.getX();
			double p2V = point2.getX();
			if(p1V > p2V) {
				return 1;
			} else if(p1V == p2V) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * On compare si un point est plus grand que l'autre via les Y
	 */
	public static class ComparatorPointY implements Comparator<Point> {
		@Override
		public int compare(Point point1, Point point2) {
			double p1V = point1.getY();
			double p2V = point2.getY();
			if(p1V > p2V) {
				return 1;
			} else if(p1V == p2V) {
				return 0;
			} else {
				return -1;
			}
		}
	}

	/**
	 * On compare si un point est plus grand que l'autre via les Z
	 */
	public static class ComparatorPointZ implements Comparator<Point> {
		@Override
		public int compare(Point point1, Point point2) {
			double p1V = point1.getZ();
			double p2V = point2.getZ();
			if(p1V > p2V) {
				return 1;
			} else if(p1V == p2V) {
				return 0;
			} else {
				return -1;
			}
		}
	}
	
	/**
	 * 
	 * fonction equals avec une marge d'erreur (les arrondis de cos et sin)
	 * 
	 */

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other == null || getClass() != other.getClass()) return false;
		Point point = (Point) other;
		return (Math.abs(this.getX()-point.getX())<0.00001 && Math.abs(this.getY()-point.getY())<0.00001 && Math.abs(this.getZ()-point.getZ())<0.00001 && this.color.equals(point.color));
	}
}