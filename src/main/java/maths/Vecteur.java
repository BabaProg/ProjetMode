package maths;

public class Vecteur extends Coordonnees {

    /**
     * On créer un nouveau Vecteur
     * @param pointX (double)
     * @param pointY (double)
     * @param pointZ (double)
     */
    public Vecteur(double pointX, double pointY, double pointZ) {
        super(pointX, pointY, pointZ);
    }

    /**
     * On créer un nouveau Vecteur grâce à 2 points
     * @param pointA (Point)
     * @param pointB (Point)
     */
    public Vecteur(Point pointA, Point pointB) {
        super(pointB.getX() - pointA.getX(), pointB.getY() - pointA.getY(), pointB.getZ() - pointA.getZ());
    }

    /**
     *
     * @return Affichage du vecteur
     */
    public String toString() {
        return super.toString();
    }

    /**
     * On multiplie les valeur x, y et z par value
     * @param value (double)
     */
    public void multiplicate(double value) {
        xCoordonnee *= value;
        yCoordonnee *= value;
        zCoordonnee = value;
    }

    /**
     * On divise les coordonnée de nore vecteur (this) par value
     * @param value (double)
     * @return un nouveau vecteur
     */
    public Vecteur divide(double value) {
        return new Vecteur(xCoordonnee / value, yCoordonnee / value, zCoordonnee / value);
    }

    /**
     *
     * @return On récupère la norme du vecteur
     */
    public double getNorme() {
        return Math.sqrt(Math.pow(xCoordonnee, 2) + Math.pow(yCoordonnee, 2) + Math.pow(zCoordonnee, 2));
    }

    /**
     *
     * @param vecteur1 (Vecteur)
     * @param vecteur2 (Vecteur)
     * @return Produit scalaire entre 2 vecteurs
     */
    public static double produitScalaire(Vecteur vecteur1, Vecteur vecteur2) {
        return vecteur1.xCoordonnee * vecteur2.xCoordonnee + vecteur1.yCoordonnee * vecteur2.yCoordonnee + vecteur1.zCoordonnee * vecteur2.zCoordonnee;
    }

    /**
     *
     * @param vecteur1 (Vecteur)
     * @param vecteur2 (Vecteur)
     * @param angle (double)
     * @return Produit scalaire entre 2 vecteurs et un angle
     */
    public static double produitScalaire(Vecteur vecteur1, Vecteur vecteur2, double angle) {
        return vecteur1.getNorme() * vecteur2.getNorme() * Math.cos(angle);
    }

    /**
     *
     * @param vecteur1 (Vecteur)
     * @param vecteur2 (Vecteur)
     * @return Le produit vectoriel entre 2 vecteurs
     */
    public static Vecteur produitVectoriel(Vecteur vecteur1, Vecteur vecteur2) {
       /* System.out.println((vecteur1.yCoordonnee * vecteur2.zCoordonnee));
        System.out.println((vecteur1.zCoordonnee * vecteur2.yCoordonnee));*/
        double pointX = (vecteur1.yCoordonnee * vecteur2.zCoordonnee) - (vecteur1.zCoordonnee * vecteur2.yCoordonnee);
        //System.out.println(pointX);
        double pointY = (vecteur1.zCoordonnee * vecteur2.xCoordonnee) - (vecteur1.xCoordonnee * vecteur2.zCoordonnee);
        double pointZ = (vecteur1.xCoordonnee * vecteur2.yCoordonnee) - (vecteur1.yCoordonnee * vecteur2.xCoordonnee);
        return new Vecteur(pointX, pointY, pointZ);
    }

    /**
     *
     * @param vecteur1 (Vecteur)
     * @param vecteur2 (Vecteur)
     * @return le cosinus entre 2 vecteurs
     */
    public static double getCosinusBetweenTwoVector(Vecteur vecteur1, Vecteur vecteur2) {
        return (produitScalaire(vecteur1, vecteur2) / (vecteur1.getNorme() * vecteur2.getNorme()));
    }

    /**
     *
     * @param other (Object) cast vers (Vecteur)
     * @return On regarde si 2 vecteurs sont égaux
     */
    public boolean equals(Object other){
        if(other == null) return  false;
        if(other == this)return true;
        Vecteur vecteur = (Vecteur) other;
        return (vecteur.xCoordonnee==xCoordonnee && vecteur.yCoordonnee==yCoordonnee && vecteur.zCoordonnee==zCoordonnee);
    }
}