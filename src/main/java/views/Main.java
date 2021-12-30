package views;

import javafx.application.Application;

public class Main {

	// java --module-path 'path-to-javafx-binaries/lib' --add-modules
	// javafx.controls,javafx.fxml -jar .\projetmode.jar

	public static void main(String[] args) throws Exception {
		Application.launch(GraphicRepere.class, args);
	}
}
