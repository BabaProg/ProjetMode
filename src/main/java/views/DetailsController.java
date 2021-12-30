package views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import maths.Repere;

import java.time.LocalDate;

public class DetailsController {
    @FXML
    protected ListView<Text> details;

    public void setRepere(Repere repere) {
        String descStr = repere.getDescription();
        String auteurStr = repere.getAuteur();
        LocalDate dateLD = repere.getDate();
        Text nomText = new Text("Nom du modèle : " + repere.getName());
        DetailItem nomDetail = new DetailItem("Nom du modèle : ", nomText.getText(), repere);
        Text auteurText = new Text("Auteur : " + (auteurStr.isBlank() ? "Pas d'auteur" : auteurStr));
        Text dateText = new Text("Date : " + (dateLD == null ? "Pas de date" : dateLD));
        Text descriptionText = new Text("Description : " + (descStr.isBlank() ? "Aucune description" : descStr));
        Text nbPointsText = new Text("Nombre de points : " + repere.getPointsSize());
        Text nbFacesText = new Text("Nombre de faces : " + repere.getPolygonesSize());
        details.getItems().addAll(nomText, auteurText, dateText, descriptionText, nbPointsText, nbFacesText);
    }

    public class DetailItem extends HBox {
        Text label;
        Text value;
        TextField field;

        public DetailItem(String label, String value, Repere repere) {
            super();
            this.label = new Text(label);
            this.value = new Text(value);
            this.field = new TextField();
            field.setVisible(false);
            this.value.setOnMouseClicked(eValue -> {
                if (eValue.getClickCount() >= 2) {
                    this.value.setVisible(false);
                    this.field.setText(this.value.getText());
                    this.field.setVisible(true);
                }
            });
            this.field.setOnKeyReleased(key -> {
                if (key.getCode().equals(KeyCode.ENTER)) {
                    this.field.setVisible(false);
                    this.value.setText(this.field.getText());
                    this.value.setVisible(true);

                }
            });
            this.getChildren().addAll(this.label, this.value, this.field);
        }

        public String getValue() {
            return value.getText();
        }
    }


}
