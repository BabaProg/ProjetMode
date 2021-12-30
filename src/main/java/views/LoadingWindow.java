package views;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoadingWindow extends Application {
    protected Stage stage;
    protected String title;
    protected String message;

    public LoadingWindow(Stage originStage, String title, String message) {
        this.title = title;
        this.message = message;
        try {
            start(originStage);
        } catch (Exception e) {
            System.out.println("Impossible");
        }
    }

    /**
     * Affiche la fenêtre javaFx
     */
    @Override
    public void start(Stage originStage) {
        stage = new Stage();
        stage.initOwner(originStage);

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(25);
        Text text = new Text(message);
        text.setFont(Font.font(20));
        ProgressIndicator progressIndicator = new ProgressIndicator();
        root.getChildren().addAll(text, progressIndicator);

        Scene scene = new Scene(root, 300, 150);
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
        stage.setResizable(false);
    }

    /**
     * Ferme la fenêtre de javaFx
     */
    public void close() {
        try {
            stage.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
