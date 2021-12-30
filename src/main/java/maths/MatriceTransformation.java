package maths;

public class MatriceTransformation extends Matrice {

    /**
     * On retourne à la matrice de base
     */
    public void clear(){
        this.matrice = new double[][]{{1, 0, 0, 0},{0, 1, 0, 0},{0, 0, 1, 0},{0, 0, 0, 1}};
    }
}