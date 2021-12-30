package views;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;
import maths.Repere;

public class ColorsController {

    @FXML protected ColorPicker face;
    @FXML protected ColorPicker edge;
    @FXML protected ColorPicker point;

    private Repere repere;

    @FXML
    public void initialize() {
        face.setValue(GraphicUtils.face);
        edge.setValue(GraphicUtils.edge);
        point.setValue(GraphicUtils.point);
    }

    public void faceModified() {
        GraphicUtils.face = face.getValue();
    }

    public void edgeModified() {
        GraphicUtils.edge = edge.getValue();
    }

    public void pointModified() {
        GraphicUtils.point = point.getValue();
    }

    public void setRepere(Repere repere) {
        this.repere = repere;
    }

    public void close() {
        Repere r = Repere.getSingleton();
        if(r != null) r.initCanvas();
        ((Stage) face.getScene().getWindow()).close();
    }
}
