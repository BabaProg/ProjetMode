package maths;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import observer.Observable;

public class Repere extends Observable {

	private double pointX;
	private double pointY;
	private double pointZ;

	private String name;
	private String description;
	private LocalDate date;
	private String auteur;

	private List<Polygon> polygones;
	private ArrayPoint points;
	private ArrayPoint pointsTranformes;

	private final Vecteur LIGHT_SOURCE = new Vecteur(0,0,1);

	private static Repere singleton = null;

	/**
	 * Singleton de la classe Repere
	 *
	 * @param pointX
	 * @param pointY
	 * @param pointZ
	 * @return une instance de la classe
	 */
	public static Repere getInstance(double pointX, double pointY, double pointZ) {
		if (singleton == null)
			singleton = new Repere(pointX, pointY, pointZ);
		return singleton;
	}

	/**
	 * Getter du singleton
	 * @return le singleton en Repere
	 */
	
	public static Repere getSingleton() {
		return singleton;
	}
	
	/**
	 * Getter de coord X
	 * @return un double de X
	 */
	
	public double getPointX() {
		return pointX;
	}

	/**
	 * Getter de coord Y
	 * @return un double de Y
	 */
	
	public double getPointY() {
		return pointY;
	}
	

	/**
	 * Getter de coord Z
	 * @return un double de Z
	 */

	public double getPointZ() {
		return pointZ;
	}

	/**
	 * Setter du nom
	 * @param un nom en String
	 */
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter de la description
	 * @param une description en String
	 */

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Setter de la date
	 * @param une date en LocalDate
	 */

	public void setDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * Setter de l'auteur
	 * @param un auteur en String
	 */

	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}

	/**
	 * Getter du nom
	 * @return un String du nom
	 */

	public String getName() {
		return name;
	}
	
	/**
	 * Getter de la description
	 * @return un String de la description
	 */


	public String getDescription() {
		return description;
	}
	
	/**
	 * Getter de la date
	 * @return une LocalDate de la date
	 */


	public LocalDate getDate() {
		return date;
	}
	
	/**
	 * Getter de l'auteur
	 * @return un String de l'auteur
	 */

	public String getAuteur() {
		return auteur;
	}

	public ArrayPoint getPointsTransformes() {
		ArrayPoint copyMapPoints = new ArrayPoint();
		for (Point p : pointsTranformes)
			copyMapPoints.add(new Point(p));
		return copyMapPoints;
	}

	/**
	 * On initialise notre rep�re
	 *  @param pointX
	 * @param pointY
	 * @param pointZ
	 */
	private Repere(double pointX, double pointY, double pointZ) {
		this.pointX = pointX;
		this.pointY = pointY;
		this.pointZ = pointZ;
		this.name = name;
		this.description = description;
		this.auteur = auteur;
		this.date = date;
		polygones = new ArrayList<>();
		points = new ArrayPoint();
		pointsTranformes = new ArrayPoint();
	}

	/**
	 * On refait notre rep�re � neuf
	 */
	public void resetRepere() {
		ArrayPoint.mtxTransformation.clear();
		polygones.clear();
		points.clear();
		pointsTranformes.clear();
	}

	public void clearList(){
		polygones = new ArrayList<Polygon>();
		points = new ArrayPoint();
		pointsTranformes = new ArrayPoint();
	}
	/**
	 * On affiche notre rep�re
	 */
	public String toString() {
		return polygones.toString();
	}

	/**
	 * On r�cup�re les polygons de notre rep�re
	 *
	 * @return Les polygons de notre rep�re
	 */
	public List<Polygon> getPolygones() {
		return polygones;
	}

	/**
	 * On calcul la taille de notre liste de polygon
	 * (Pour régler les problèmes de loi de déméter
	 * dans la classe DetailsController.java)
	 * @return la taille de la liste de polygons
	 */
	public int getPolygonesSize() {
		return getPolygones().size();
	}

	/**
	 * On centre notre rep�re
	 */
	public void center() {
		 // long befTime = System.nanoTime();

		Vecteur vecteur = getVector();

		vecteur.multiplicate(-1);

		translate(vecteur);

		// long aftTime =
		// System.nanoTime();
		//System.out.println("Centrage du repère en "
		// + (aftTime - befTime) / 1000000 + " ms.");
	}

	public Vecteur getVector() {
		List<Point> pointsList = getPoints();
		Collections.sort(pointsList, new Point.ComparatorPointX());
		Point middlePointX = pointsList.get(pointsList.size() / 2);

		pointsList = getPoints();
		Collections.sort(pointsList, new Point.ComparatorPointY());
		Point middlePointY = pointsList.get(pointsList.size() / 2);

		pointsList = getPoints();
		Collections.sort(pointsList, new Point.ComparatorPointZ());
		Point middlePointZ = pointsList.get(pointsList.size() / 2);

		return new Vecteur(middlePointX.getX(), middlePointY.getY(), middlePointZ.getZ());
	}

	/**
	 * Permet de faire une translation sur notre
	 * rep�re gr�ce � un vecteur
	 *
	 * @param vecteur (Vecteur)
	 */
	public void translate(Vecteur vecteur) {
		// long befTime = System.nanoTime();
		pointsTranformes = getPoints();
		pointsTranformes.translation(vecteur);

		// long aftTime = System.nanoTime();
		//System.out.println("Translation
		// du repère en " + (aftTime - befTime)
		// / 1000000 + " ms.");
		notifyObservers(this);
	}

	public void homothetie(double value) {
		// long befTime = System.nanoTime();
		pointsTranformes = getPoints();
		pointsTranformes.homothetie(value);

		// long aftTime = System.nanoTime();
		//System.out.println("Homothétie
		// du repère en " + (aftTime - befTime)
		// / 1000000 + " ms.");
		notifyObservers(this);
	}

	/**
	 * Permet de faire une rotation sur l'axe x gr�ce � un angle angle
	 *
	 * @param angle (double)
	 */
	public void rotationX(double angle) {
		// long befTime = System.nanoTime();
		pointsTranformes = getPoints();
		pointsTranformes.rotationXDegree(angle);
		// long aftTime = System.nanoTime();
		//System.out.println("Rotation X du
		// repère en " + (aftTime - befTime) /
		// 1000000 + " ms.");
		/*for(Polygon p:polygones){
			System.out.println(p.getColor());
			p.refreshNormale();*/
			/*System.out.println(p.getColor());
			p.setLuminosity(lightSource);
			System.out.println(p.getColor());
		}*/
		notifyObservers(this);
	}

	/**
	 * Permet de faire une rotation sur l'axe y gr�ce � un angle angle
	 *
	 * @param angle (double)
	 */
	public void rotationY(double angle) {
		// long befTime = System.nanoTime();
		pointsTranformes = getPoints();
		pointsTranformes.rotationYDegree(angle);
		// long aftTime = System.nanoTime();
		//System.out.println("Rotation Y
		// du repère en " + (aftTime -
		// befTime) / 1000000 + " ms.");
		notifyObservers(this);
	}

	/**
	 * Permet de faire une rotation sur l'axe z gr�ce � un angle angle
	 *
	 * @param angle (double)
	 */
	public void rotationZ(double angle) {
		// long befTime = System.nanoTime();
		pointsTranformes = getPoints();
		pointsTranformes.rotationZDegree(angle);
		// long aftTime = System.nanoTime();
		//System.out.println("Rotation Z du
		// repère en " + (aftTime - befTime)
		// / 1000000 + " ms.");
		notifyObservers(this);
	}

	public void initCanvas() {
		notifyObservers(this);
	}

	/**
	 * On r�up�re un point gr�ce � un indice (idx)
	 *
	 * @param idx (int)
	 * @return Un point
	 */
	public Point getPoint(int idx) {
		return points.get(idx);
	}

	/**
	 * On r�cup�re le point transform� gr�ce � un indice (idx)
	 *
	 * @param idx (int)
	 * @return le point transform�
	 */
	public Point getPointTransforme(int idx) {
		return pointsTranformes.get(idx);
	}

	/**
	 * On r�cup�re une copie des points
	 *
	 * @return Une copie des points
	 */
	public ArrayPoint getPoints() {
		ArrayPoint copyMapPoints = new ArrayPoint();
		for (Point p : points)
			copyMapPoints.add(new Point(p));
		// System.out.println("copy :"+copyMapPoints.size());
		return copyMapPoints;
	}

	/**
	 * On r�cup�re la largeur entre le point le plus � gauche et celui le plus
	 * � droite
	 *
	 * @return La largeur
	 */
	public double width() {
		Point min = Collections.min(points, new Point.ComparatorPointX());
		Point max = Collections.max(points, new Point.ComparatorPointY());
		return Math.abs(min.getDisplayX()) + Math.abs(max.getDisplayX());
	}

	/**
	 * On r�cup�re la hauteur entre le point le plus � gauche et celui le plus
	 * � droite
	 *
	 * @return La hauteur
	 */
	public double height() {
		Point min = Collections.min(points, new Point.ComparatorPointY());
		Point max = Collections.max(points, new Point.ComparatorPointY());
		return Math.abs(min.getDisplayY()) + Math.abs(max.getDisplayY());
	}

	public double depth() {
		Point min = Collections.min(points, new Point.ComparatorPointZ());
		Point max = Collections.max(points, new Point.ComparatorPointZ());
		return Math.abs(min.getZ()) + Math.abs(max.getZ());
	}

	/**
	 * Permet de d�zoom
	 */
	public void dezoom() {
		pointX += 0.25;
		pointY += 0.25;
		pointZ += 0.25;
	}

	/**
	 * Permet de zoom
	 */
	public void zoom() {
		pointX -= 0.25;
		pointY -= 0.25;
		pointZ -= 0.25;
	}

	/**
	 * On ajoute un point � notre liste de polygon
	 *
	 * @param point
	 */
	public void addPoint(Point point) {
		points.add(point);
		pointsTranformes.add(point);
	}

	/**
	 * Permet d'ajouter plusieur points
	 *
	 * @param points (List<Point>)
	 */
	public void addPoints(List<Point> points) {
		this.points.addAll(points);
		pointsTranformes.addAll(points);
	}

	/**
	 * Ajout d'un polygon � la liste
	 *
	 * @param polygon (Polygon)
	 */
	public void addPolygon(Polygon polygon) {
		polygones.add(polygon);
	}

	/*
	 * public void addPoint(double x, double y, double z) { points.add(new Point(x,
	 * y, z)); }
	 */

	/**
	 * On retire un polygon de la liste gr�ce � sa position (index)
	 *
	 * @param index (int)
	 */
	public void removePolygon(int index) {
		polygones.remove(index);
	}

	/**
	 * On retire un polygon de la liste
	 *
	 * @param polygon (Polygon)
	 */
	public void removePolygon(Polygon polygon) {
		polygones.remove(polygon);
	}

	/**
	 * Retourne la taille de notre liste de points
	 * (pour régler la loi de déméter de la classe
	 * DetailsController.java)
	 * @return la taille de la liste de points
	 */
	public int getPointsSize() {
		return getPoints().size();
	}

}