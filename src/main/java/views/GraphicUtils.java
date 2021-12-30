package views;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import maths.Point;
import maths.Polygon;
import maths.Repere;
import maths.Vecteur;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GraphicUtils {

    public static Color face = Color.WHITE;
    public static Color edge = Color.BLACK;
    public static Color point = Color.BLACK;
    private static Vecteur lumiere = new Vecteur(0,1,0);

    public static void drawRepere(Repere repere, Canvas canvas, double centerX, double centerY, DrawMethods method, boolean colorDisplay, boolean onlyVisible, boolean aretesVisibles, boolean pointsVisibles) {
        /*Thread thread = new Thread() {
            public void run() {*/
        boolean lissage = true;
        MainController.cons.println("Dessin de la vue " + method + " en cours...");
        long befTime = System.nanoTime();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(face);
        gc.setStroke(edge);
        List<Polygon> polygones = repere.getPolygones();
        switch(method) {
            case DESSUS:
                Collections.sort(polygones, new Polygon.ComparatorPolygonY());
                break;
            case DROITE:
                Collections.sort(polygones, new Polygon.ComparatorPolygonX());
                break;
            case FACE:
                Collections.sort(polygones, new Polygon.ComparatorPolygonZ());
                break;
        }
        for (Polygon p : polygones) {
            Vecteur vue = new Vecteur(0, 0, 0);
            if(!p.isHidden()) {
                double[] xs = new double[0];
                double[] ys = new double[0];
                switch(method) {
                    case DESSUS:
                        xs = p.getPointsX();
                        ys = p.getPointsZ();
                        vue.setyCoordonnee(1);
                        break;
                    case DROITE:
                        xs = p.getPointsZ();
                        ys = p.getPointsY();
                        vue.setxCoordonnee(1);
                        break;
                    case FACE:
                        xs = p.getPointsX();
                        ys = p.getPointsY();
                        vue.setzCoordonnee(1);
                        break;
                }
                for (int i = 0; i < xs.length; i++) {
                    xs[i] += centerX;
                    ys[i] *= -1.0;
                    ys[i] += centerY;
                }
                p.refreshNormale();
                if(Vecteur.produitScalaire(vue, p.getNormale())>0) {
                    p.setLuminosity(lumiere);
                    if (lissage){
                        lissage(gc,xs,ys,repere,p);
                    }else{
                        Color color = p.getColor();
                        if(colorDisplay && !color.equals(Color.BLACK)) {
                            gc.setFill(color);
                            gc.setStroke(color);
                        } else {
                            gc.setFill(face);
                            gc.setStroke(edge);
                        }
                        gc.fillPolygon(xs, ys, p.size());
                        if(aretesVisibles) {
                            gc.setStroke(edge);
                        } else if(!colorDisplay) {
                            gc.setStroke(face);
                        }
                        gc.strokePolygon(xs, ys, p.size());
                        if(pointsVisibles) {
                            gc.setFill(point);
                            for(int i=0;i<xs.length;i++) {
                                gc.fillOval(xs[i] - 1.25, ys[i] - 1.25, 2.5, 2.5);
                            }
                        }
                    }
                }
            }
        }
        long aftTime = System.nanoTime();
        MainController.cons.println("Dessin de la figure réalisé en " + (aftTime-befTime)/1000000 + " ms.");
                /*currentThread().interrupt();
            }
        };
        thread.start();*/
    }

    public static void showMessage(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setHeaderText(title);
        alert.setContentText(message);

        //alert.showAndWait();
    }

    public static Image getResourceImage(String str) {
        return new Image(GraphicUtils.class.getResourceAsStream("/" + str));
    }

    public static Image getResourceImage(String str, double width, double height, boolean ratio, boolean smooth) {
        return new Image(GraphicUtils.class.getResourceAsStream("/" + str), width, height, ratio, smooth);
    }

    public static ImageView getResourceImageView(String str) {
        return new ImageView(getResourceImage(str));
    }

    public static ImageView getResourceImageView(String str, double width, double height, boolean ratio, boolean smooth) {
        return new ImageView(getResourceImage(str, width, height, ratio, smooth));
    }

    private static double[] getWeightsOfPixelInTriangle(int x, int y, double[] xs, double[] ys) {
        double div=(ys[1]-ys[2])*(xs[0]-xs[2]) + (xs[2]-xs[1])*(ys[0]-ys[2]);
        double weight1=( (ys[1]-ys[2])*(x-xs[2]) + (xs[2]-xs[1])*(y-ys[2]) ) / div;
        double weight2=( (ys[2]-ys[0])*(x-xs[2]) + (xs[0]-xs[2])*(y-ys[2]) ) / div;
        double weight3=1-weight1-weight2;

        return new double[] {weight1,weight2,weight3};
    }

    private static boolean isPointInPolygon(int x, int y,double[] xs, double[] ys){
        int i,j;
        boolean isInside = false;

        for(i = 0,j= xs.length-1;i<xs.length;j=i++){
            if ( (ys[i] >= y) != (ys[j] >= y) && x <= (xs[j] - xs[i]) *(y-ys[i]) / (ys[j] - ys[i]) + xs[i]){
                isInside = !isInside;
            }
        }
        return isInside;
    }

    private static double maxValue(double[] chars) {
        double max = chars[0];
        for (int ktr = 0; ktr < chars.length; ktr++) {
            if (chars[ktr] > max) {
                max = chars[ktr];
            }
        }
        return max;
    }

    private static double minValue(double[] chars) {
        double max = chars[0];
        for (int ktr = 0; ktr < chars.length; ktr++) {
            if (chars[ktr] < max) {
                max = chars[ktr];
            }
        }
        return max;
    }

    private static void lissage(GraphicsContext gc,double[]xs , double[]ys, Repere repere, Polygon p){
        for (int i = (int)minValue(xs);i<=(int) maxValue(xs);i++){
            for (int j = (int)minValue(ys);j<=(int)  maxValue(ys);j++){
                Color oldColor = null;
                boolean itsok = true;
                try{
                    double[] weights = getWeightsOfPixelInTriangle(i,j,xs,ys);
                    for (double d: weights){
                        if (d<0){
                            itsok = false;
                        }
                    }
                    if (itsok){
                        Point point1 = repere.getPoint(p.getPoints().get(0));
                        Point point2 = repere.getPoint(p.getPoints().get(1));
                        Point point3 = repere.getPoint(p.getPoints().get(2));
                        double red = point1.getColorWithPolygon().getRed()*weights[0] + point2.getColorWithPolygon().getRed()*weights[1] + point3.getColorWithPolygon().getRed()*weights[2];
                        double green = point1.getColorWithPolygon().getGreen()*weights[0] + point2.getColorWithPolygon().getGreen()*weights[1] + point3.getColorWithPolygon().getGreen()*weights[2];
                        double blue = point1.getColorWithPolygon().getBlue()*weights[0] + point2.getColorWithPolygon().getBlue()*weights[1] + point3.getColorWithPolygon().getBlue()*weights[2];
                        oldColor = new Color(red,green,blue,1);
                        gc.getPixelWriter().setColor(i,j,oldColor);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}

