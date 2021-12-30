package views;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import maths.Repere;
import observer.Observer;

public class ConnectedCanvas extends Canvas implements Observer<Repere> {

    private double centerX;
    private double centerY;
    private DrawMethods method;
    private boolean colorDisplay;
    private GraphicsContext gc;
    private boolean aretesVisibles;
    private boolean pointsVisibles;
	
	public ConnectedCanvas(double width, double height, Repere repere, DrawMethods method, boolean aretesVisibles, boolean pointsVisibles, boolean colors) {
		super(width, height);
        centerX = width / 2;
        centerY = height / 2;

        clearCanvas();

        this.method = method;
        this.aretesVisibles = aretesVisibles;
        this.pointsVisibles = pointsVisibles;
        this.colorDisplay = colors;
        repere.attach(this);
	}

	/**
	 * efface ce qui est affiché sur le canvas
	 */
    private void clearCanvas() {
        gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    }

    public void setAretesVisibles(boolean b) {
        this.aretesVisibles = b;
    }

    public void setPointsVisibles(boolean b) {
        this.pointsVisibles = b;
    }

    /**
     * Dessine le repere sur le canvas
     */
    @Override
    public void update(Repere value) {
        clearCanvas();
        /*
        loadingWindow lw = new loadingWindow(Controller.stage,
        "Dessin de la vue " + method,"Dessin de la vue " + method + " en cours...");
        */
        GraphicUtils.drawRepere(value, this, centerX, centerY, method, colorDisplay, true, aretesVisibles, pointsVisibles);
        Paint fill = gc.getFill();
        gc.setFill(Color.BLACK);
        gc.fillText(method.getName(), 5, 15);
        gc.setFill(fill);
        //lw.close();
    }

    public void setColorDisplay(boolean b) {
        colorDisplay = b;
    }

	public GraphicsContext getGc() {
		return gc;
	}
    
}
