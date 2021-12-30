package views;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import java.io.File;

import static views.GraphicUtils.getResourceImage;

public class Preview extends HBox implements Comparable<Preview> {

	protected Text fileName;
	protected ImageView image;
	public static final double SPACING = 12.5;
	private static ImageView defaultPreview = GraphicUtils.getResourceImageView("vide.png");
	
	public Preview(String fileName) {
		this(fileName, defaultPreview);
	}

	public Preview(String fileName, ImageView image) {
		super(SPACING);
		this.fileName = new Text(fileName);
		this.image = image;
		this.setAlignment(Pos.CENTER_LEFT);
		super.getChildren().addAll(this.image, this.fileName);
	}

	@Override
	public String toString() {
		return fileName + " " + image;
	}

	public String getFileName() {
		return fileName.getText();
	}

	public ImageView getImage() {
		return image;
	}

	@Override
	public int compareTo(Preview o) {
		return this.fileName.getText().compareTo(o.fileName.getText());
	}
}
