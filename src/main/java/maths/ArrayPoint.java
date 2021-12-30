package maths;

import java.util.ArrayList;

import transformation.Rotation;
import transformation.Symetrie;
import transformation.Translation;

public class ArrayPoint extends ArrayList<Point> {
    //matrices de symétrie
    private final Symetrie SYMETRIE_X = Symetrie.SX;
    public Matrice symetrieX = new Matrice(SYMETRIE_X.getSymetrie());
    private final Symetrie SYMETRIE_Y = Symetrie.SY;
    public Matrice symetrieY = new Matrice(SYMETRIE_Y.getSymetrie());
    private final Symetrie SYMETRIE_Z = Symetrie.SZ;
    public Matrice symetrieZ = new Matrice(SYMETRIE_Z.getSymetrie());

    //matrices de translation
    private static Translation eTranslation = Translation.t;
    public static Matrice mtxtranslation = new Matrice(eTranslation.getTranslation());

    //matrices de rotation
    private final Rotation ROTATION_X = Rotation.RX;
    public Matrice rotationX = new Matrice(ROTATION_X.getRotation());
    private final Rotation ROTATION_Y = Rotation.RY;
    public Matrice rotationY = new Matrice(ROTATION_Y.getRotation());
    private final Rotation ROTATION_Z = Rotation.RZ;
    public Matrice rotationZ = new Matrice(ROTATION_Z.getRotation());

    /**
     * MatriceTransformation garde en mémoire les transformations de la matrice
     */

    public static MatriceTransformation mtxTransformation = new MatriceTransformation();

    /**
     * Applique une symetrie sur X à tout les points de l'arrayPoint
     */
    public void symetrieSurX() {
        mtxTransformation.matrice = mtxTransformation.multiplication(symetrieX.matrice).matrice;
        this.multiplicate();
    }

    /**
     * Applique une symetrie sur Y à tout les points de l'arrayPoint
     */
    public void symetrieSurY() {
        mtxTransformation.matrice = mtxTransformation.multiplication(symetrieY.matrice).matrice;
        this.multiplicate();
    }

    /**
     * Applique une symetrie sur Z à tout les points de l'arrayPoint
     */
    public void symetrieSurZ() {
        mtxTransformation.matrice = mtxTransformation.multiplication(symetrieZ.matrice).matrice;
        this.multiplicate();
    }

    /**
     * Applique une homothétie de valeur value à tout les points de l'arrayPoint
     *
     * @param value
     */
    public void homothetie(double value) {
        mtxTransformation.multiplication(value);
        this.multiplicate();
    }

    /**
     * Applique une translation de vecteur vector à tout les points de l'arrayPoint
     *
     * @param vector vecteur directeur
     */
    public void translation(Vecteur vector) {
        mtxtranslation.matrice[0][3] = vector.getxCoordonnee();
        mtxtranslation.matrice[1][3] = vector.getyCoordonnee();
        mtxtranslation.matrice[2][3] = vector.getzCoordonnee();
        mtxTransformation.matrice = mtxTransformation.multiplication(mtxtranslation.matrice).matrice;
        this.multiplicate();
    }

    /**
     * Applique une rotation sur l'axe X d'angle angle en degrée
     *
     * @param angle angle en degrée
     */
    public void rotationXDegree(double angle) {
        rotationXRadiant(Math.toRadians(angle));
    }

    /**
     * Applique une rotation sur l'axe X d'angle angle en radiant
     *
     * @param angle angle en radiant
     */
    public void rotationXRadiant(double angle) {
        rotationX.matrice[1][1] = Math.cos(angle);
        rotationX.matrice[1][2] = Math.sin(angle) * -1;
        rotationX.matrice[2][1] = Math.sin(angle);
        rotationX.matrice[2][2] = Math.cos(angle);
        mtxTransformation.matrice = mtxTransformation.multiplication(rotationX.matrice).matrice;
        this.multiplicate();
    }


    /**
     * Applique une rotation sur l'axe Y d'angle angle en degrée
     *
     * @param angle angle en degree
     */
    public void rotationYDegree(double angle) {
        rotationYRadiant(Math.toRadians(angle));
    }

    /**
     * Applique une rotation sur l'axe Y d'angle angle en radiant
     *
     * @param angle angle en radiant
     */
    public void rotationYRadiant(double angle) {
        rotationY.matrice[0][0] = Math.cos(angle);
        rotationY.matrice[0][2] = Math.sin(angle) * -1;
        rotationY.matrice[2][0] = Math.sin(angle);
        rotationY.matrice[2][2] = Math.cos(angle);
        mtxTransformation.matrice = mtxTransformation.multiplication(rotationY.matrice).matrice;
        this.multiplicate();
    }

    /**
     * Applique une rotation sur l'axe Z d'angle angle en degrée
     *
     * @param angle angle en degrée
     */
    public void rotationZDegree(double angle) {
        rotationZRadiant(Math.toRadians(angle));
    }

    /**
     * Applique une rotation sur l'axe Z d'angle angle en radiant
     *
     * @param angle angle en radiant
     */
    public void rotationZRadiant(double angle) {
        rotationZ.matrice[0][0] = Math.cos(angle);
        rotationZ.matrice[0][1] = Math.sin(angle) * -1;
        rotationZ.matrice[1][0] = Math.sin(angle);
        rotationZ.matrice[1][1] = Math.cos(angle);
        mtxTransformation.matrice = mtxTransformation.multiplication(rotationZ.matrice).matrice;
        this.multiplicate();
    }

    /**
     * Multiplie les points de la liste par la matrice de transformation
     */
    private void multiplicate() {
        for (Point p : this) {
            p.mutiplicate(mtxTransformation);
        }
    }
}