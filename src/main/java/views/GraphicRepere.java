package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphicRepere extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/sceneModelisation.fxml"));
		BorderPane root = loader.load();
		MainController controller = loader.getController();
			controller.setStage(stage);
		Scene scene = new Scene(root, 1280, 720);
		Image icon = new Image(getClass().getResourceAsStream("/logo.png"));
		stage.getIcons().add(icon);
		stage.setScene(scene);
		stage.setTitle("ProjetModeJ6");
		stage.setResizable(false);
		stage.show();
		//stage.centerOnScreen();
	}

}