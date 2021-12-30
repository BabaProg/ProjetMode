package views;

import errors.NoAsciiFileException;
import io.PlyReader;
import javafx.animation.AnimationTimer;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import maths.Repere;
import maths.Vecteur;
import observer.Observer;

import javax.imageio.ImageIO;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainController {

    @FXML
    protected BorderPane mainStage; // BorderPane global
    @FXML
    protected ListView<Preview> gestionnaire; // Liste des fichiers ply
    @FXML
    protected GridPane grid; // Grille
    @FXML
    protected TextField fileText; // TextField du gestionnaire de fichiers
    @FXML
    protected TextArea console; // Console des logs - à mettre dans une page spécifique !

    @FXML
    protected RadioMenuItem colorDisplay; // Choix des couleurs par défaut ou couleurs du modèle
    @FXML
    protected RadioMenuItem aretesDisplay; // Choix de l'affichage des arêtes
    @FXML
    protected RadioMenuItem eclairageDisplay; // Choix de l'affichage de l'éclairage
    @FXML
    protected RadioMenuItem ombresDisplay; // Choix de l'affichage des ombres

    @FXML
    protected ImageView transRotImage;
    @FXML
    protected ImageView axisImage;
    @FXML
    protected ImageView lightImage;
    @FXML
    protected Button eclairageButton;
    @FXML
    protected Button tranchesButton;
    @FXML
    protected Button selectionButton;
    @FXML
    protected Button pointsButton;
    @FXML
    protected Button aretesButton;
    @FXML
    protected Button colorsButton;
    @FXML
    protected Button horlogeButton;

    private Repere repere; // Repere actuellement utilisé
    /*
     * private double repereWidth = 0; // Largeur du repère private double
     * repereHeight = 0; // Hauteur du repère private double repereDepth = 0; //
     * Profondeur du repère
     */

    private Stage stage; // Stage de la fenêtre
    private File dir; // Chemin du dossier des PLY
    private File selectedFile; // Fichier actuellement affiché
    public static Console cons; // Console liée au modèle affiché
    private boolean lightMode = true;

    private static final String PNG_EXTENSION = ".png";
    private static final String PLY_EXTENSION = ".ply";
    private static final int HEIGHT_PREVIEW = 50;
    private static final int WIDTH_PREVIEW = 50;

    private final double PAS_ROT = 5.0;
    // private final double PAS_TRANS = 1.0;
    private final double PAS_PLUS_ZOOM = 1.10;
    private final double PAS_MOINS_ZOOM = 0.90;
    private double lastX = -1212;
    private double lastY = -1212;
    private List<Canvas> canvasList = new ArrayList<>();

    private boolean translationMode = true;
    private String transAxis = "x";
    private boolean colorMode = true;
    private boolean eclairageMode = true;
    private boolean tranchesMode = true;
    private boolean selectionMode = false;
    private boolean aretesVisibles = true;
    private boolean pointsVisibles = true;
    private boolean controleurHorloge = false;

    private ConnectedCanvas dessus;
    private ConnectedCanvas droite;
    private ConnectedCanvas face;
    private ConnectedCanvas tranche;
    private boolean aretesVisible = true;

    AnimationTimer timer = new AnimationTimer() {

        @Override
        public void handle(long time) {
            if (repere != null) {
                repere.rotationY(-PAS_ROT);
            }


        }
    };

    /**
     * Fonction appelée lorsque le FXML est chargé, semblable à un main
     */
    @FXML
    public void initialize() {
        cons = new Console(console);
        dir = new File("./");
        refreshGestionnaire();
        initToggleButtons();
        for (Node node : mainStage.getChildren()) {
            if (!node.equals(fileText)) {
                node.setOnMouseClicked(e -> {
                    mainStage.requestFocus();
                });
            }
        }

        // sortedListeView();
    }

    /**
     * Ferme la fenêtre
     */
    public void close() {
        stage.close();
    }

    /**
     * Ouvre un modèle via l'explorateur de fichiers
     */
    public void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("../"));
        fileChooser.setTitle("Ouvrir un modèle 3D");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Fichiers PLY", "*.ply"));
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try {
                loadFile(selectedFile.getCanonicalPath());
            } catch (NoAsciiFileException e) {
                GraphicUtils.showMessage(Alert.AlertType.ERROR, e.getMessage(), null);
            } catch (IOException e) {
                GraphicUtils.showMessage(Alert.AlertType.ERROR, "Fichier impossible à récupérer", null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            GraphicUtils.showMessage(Alert.AlertType.WARNING, "Aucun fichier choisi !", null);
        }

    }

    /**
     * Ouvrir un dossier pour afficher les modèles contenus dedans
     */
    public void openDir() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(dir);
        File selectedDir = directoryChooser.showDialog(stage);
        if (selectedDir != null) {
            dir = selectedDir;
        }
        refreshGestionnaire();
    }

    /**
     * Affiche l'aide
     */
    /*
     * public void afficherAide() {
     *
     * }
     */

    /**
     * Change la coloration du modèle ( soit en utilisant les couleurs par défaut
     * soit en utilisant les couleurs du modèle )
     */
    public void colorDisplayChanged() {
        for (Canvas c : canvasList)
            ((ConnectedCanvas) c).setColorDisplay(colorDisplay.isSelected());
    }

    /**
     * Ouvre la fenêtre d'édition des couleurs d'affichage par défaut
     *
     * @throws Exception
     */
    public void defaultColors() throws Exception {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/choixCouleurs.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setOnCloseRequest(e -> {
            canvasUpdate();
        });
        newStage.setTitle("Configuration des couleurs");
        newStage.show();
        newStage.centerOnScreen();
    }

    public void aretesDisplayChanged() {
        aretesVisible = aretesDisplay.isSelected();
        for (Canvas c : canvasList) {
            ConnectedCanvas cc = (ConnectedCanvas) c;
            cc.setAretesVisibles(aretesVisible);
            cc.update(repere);
        }
    }

    public void eclairageDisplayChanged() {
        /*
         * aretesVisible = aretesDisplay.isSelected(); for (Canvas c : canvasList) {
         * ConnectedCanvas cc = (ConnectedCanvas) c;
         * cc.setAretesVisibles(aretesVisible); cc.update(repere); }
         */
    }

    public void ombresDisplayChanged() {
        /*
         * aretesVisible = aretesDisplay.isSelected(); for (Canvas c : canvasList) {
         * ConnectedCanvas cc = (ConnectedCanvas) c;
         * cc.setAretesVisibles(aretesVisible); cc.update(repere); }
         */
    }

    /**
     * Ouvre les détails du modèle
     *
     * @throws IOException
     */
    public void openDetails() throws IOException {
        if (repere != null) {
            Stage newStage = new Stage();
            newStage.initOwner(stage);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details.fxml"));
            BorderPane root = loader.load();
            DetailsController controller = loader.getController();
            controller.setRepere(repere);
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setTitle("Détails du modèle");
            newStage.show();
            newStage.centerOnScreen();
        } else {
            GraphicUtils.showMessage(Alert.AlertType.WARNING, "Aucun modèle n'est chargé !", null);
        }
    }
    /**
     * Affiche les couleurs
     */
    public void colorsClicked() {
        colorMode = !colorMode;
        if (colorMode) {
            colorsButton.setStyle(null);
            colorsButton.getStyleClass().add("buttonSelected");
        } else {
            colorsButton.getStyleClass().clear();
        }
        canvasUpdate();
    }
    /**
     * Switch entre la propri�t� translation et rotation
     */
    public void transRotClicked() {
        translationMode = !translationMode;
        Image img;
        if (translationMode) {
            img = new Image(getClass().getResourceAsStream("/translation.png"));
        } else {
            img = new Image(getClass().getResourceAsStream("/rotation.png"));
        }
        transRotImage.setImage(img);
    }
    /**
     * D�finit l'axe sur lequel on agit (X, Y ou Z)
     */
    public void axisClicked() {
        Image img = null;
        switch (transAxis) {
            case "x":
                transAxis = "y";
                img = new Image(getClass().getResourceAsStream("/y.png"));
                break;
            case "y":
                transAxis = "z";
                img = new Image(getClass().getResourceAsStream("/z.png"));
                break;
            case "z":
                transAxis = "x";
                img = new Image(getClass().getResourceAsStream("/x.png"));
                break;
        }
        axisImage.setImage(img);
    }
    /**
     * Recentre le repere
     */
    public void centrageClicked() {
        if (repere != null)
            repere.center();
        else {
            GraphicUtils.showMessage(Alert.AlertType.WARNING, "Aucun modèle n'est chargé !", null);
        }
    }
    /**
     * R��value l'�chelle du modele 3D pour mieux le visualiser
     */
    public void echelleClicked() {
        if (repere != null)
            autoEchelle();
        else {
            GraphicUtils.showMessage(Alert.AlertType.WARNING, "Aucun modèle n'est chargé !", null);
        }
    }
    /**
     * Ajoute l'�clairage sur le modele
     */
    public void eclairageClicked() {
        eclairageMode = !eclairageMode;
        if (eclairageMode) {
            eclairageButton.setStyle(null);
            eclairageButton.getStyleClass().add("buttonSelected");
        } else {
            eclairageButton.getStyleClass().clear();
        }
    }
    /**
     * Visualisation de la vue en tranche du modele
     */
    public void tranchesClicked() {
        tranchesMode = !tranchesMode;
        if (tranchesMode) {
            tranchesButton.setStyle(null);
            tranchesButton.getStyleClass().add("buttonSelected");
        } else {
            tranchesButton.getStyleClass().clear();
        }
    }
    /**
     *
     */
    public void selectionClicked() {
        selectionMode = !selectionMode;
        if (selectionMode) {
            selectionButton.setStyle(null);
            selectionButton.getStyleClass().add("buttonSelected");
        } else {
            selectionButton.getStyleClass().clear();
        }
    }
    
    /**
     * 
     */
    public void pointsClicked() {
        pointsVisibles = !pointsVisibles;
        if (pointsVisibles) {
            pointsButton.setStyle(null);
            pointsButton.getStyleClass().add("buttonSelected");
        } else {
            pointsButton.getStyleClass().clear();
        }
        canvasUpdate();
    }
    /**
     * Reset l'horloge
     */
    @FXML
    public void horlogeClicked() {
        controleurHorloge = !controleurHorloge;

        if (controleurHorloge) {
            horlogeButton.setStyle(null);
            horlogeButton.getStyleClass().add("buttonSelected");
            timer.handle(1000);
            timer.start();
        } else {
            horlogeButton.getStyleClass().clear();
            timer.stop();
        }


    }
    /**
     * 
     */
    public void aretesClicked() {
        aretesVisibles = !aretesVisibles;
        if (aretesVisibles) {
            aretesButton.setStyle(null);
            aretesButton.getStyleClass().add("buttonSelected");
        } else {
            aretesButton.getStyleClass().clear();
        }
        canvasUpdate();
    }
    /**
     * Ouvre la fenetre d'informations
     * @throws IOException
     */
    public void openInfos() throws IOException {
        if (repere != null) {
            Stage newStage = new Stage();
            newStage.initOwner(stage);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/details.fxml"));
            BorderPane root = loader.load();
            DetailsController controller = loader.getController();
            controller.setRepere(repere);
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setTitle("Détails du modèle");
            newStage.show();
            newStage.centerOnScreen();
        } else {
            GraphicUtils.showMessage(Alert.AlertType.WARNING, "Aucun modèle n'est chargé !", null);
        }
    }

    /**
     * Fonction appelée quand l'utilisateur fait un drag sur la grille pour
     * translater ou tourner le modèle
     *
     * @param e
     */
    public void gridDrag(MouseEvent e) {
        if (repere != null) {
            double x = e.getX();
            double y = e.getY();
            double transX = (x - lastX) / 100;
            double transY = -1 * ((y - lastY) / 100);
            if (lastX != -1212 && lastY != -1212) {
                if (Math.abs(transX) <= 5 && Math.abs(transY) <= 5) {
                    if (translationMode) {
                        double pasX = Math.signum(transX) * repere.width() / 10;
                        double pasY = Math.signum(transY) * repere.height() / 10;
                        double pasZ = Math.signum(transY) * repere.depth() / 10;
                        if (transAxis.equals("x")) {
                            Vecteur vX = new Vecteur(pasX, 0, 0);
                            repere.translate(vX);
                        } else if (transAxis.equals("y")) {
                            Vecteur vY = new Vecteur(0, pasY, 0);
                            repere.translate(vY);
                        } else if (transAxis.equals("z")) {
                            Vecteur vZ = new Vecteur(0, 0, pasZ);
                            repere.translate(vZ);
                        } else {
                            Vecteur vX = new Vecteur(pasX, 0, 0);
                            repere.translate(vX);
                            Vecteur vY = new Vecteur(0, pasY, 0);
                            repere.translate(vY);
                        }
                    } else {
                        if (transAxis.equals("x")) {
                            repere.rotationX(transY * 10);
                        } else if (transAxis.equals("y")) {
                            repere.rotationY(transX * 10);
                        } else if (transAxis.equals("z")) {
                            repere.rotationZ(transX * -10);
                        } else {
                            transX *= -10;
                            transY *= -10;
                            repere.rotationX(transY);
                            repere.rotationY(transX);
                        }
                    }
                }
            }
            lastX = x;
            lastY = y;
        }
    }

    /**
     * Fonction appelée quand l'utilisateur scroll avec la souris sur la grille pour
     * zoomer
     *
     * @param e
     */
    public void gridScroll(ScrollEvent e) {
        if (repere != null) {
            if (e.getDeltaY() >= 0)
                repere.homothetie(PAS_PLUS_ZOOM);
            else
                repere.homothetie(PAS_MOINS_ZOOM);
        }
    }
    /**
     * Tourne le modele sur X positivement
     */
    @FXML
    void rotXplus() {
        if (repere != null) {
            repere.rotationX(PAS_ROT);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }
    /**
     * Tourne le modele sur X negativement
     */
    @FXML
    void rotXmoins() {
        if (repere != null) {
            repere.rotationX(-1 * PAS_ROT);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }
    /**
     * Tourne le modele sur Y positivement
     */

    @FXML
    void rotYplus() {
        if (repere != null) {
            repere.rotationY(PAS_ROT);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }
    /**
     * Tourne le modele sur Y negativement
     */

    @FXML
    void rotYmoins() {
        if (repere != null) {
            repere.rotationY(-1 * PAS_ROT);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }

    /**
     * Tourne le modele sur Z positivement
     */
    @FXML
    void rotZplus() {
        if (repere != null) {
            repere.rotationZ(PAS_ROT);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }

    /**
     * Tourne le modele sur Z negativement
     */
    @FXML
    void rotZmoins() {
        if (repere != null) {
            repere.rotationZ(-1 * PAS_ROT);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }

    /**
     * Translate le modele sur X positivement
     */
    @FXML
    void transXplus() {
        if (repere != null) {
            double pas = repere.width() / 10;
            Vecteur v = new Vecteur(pas, 0, 0);
            repere.translate(v);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }

    /**
     * Translate le modele sur X negativement
     */
    @FXML
    void transXmoins() {
        if (repere != null) {
            double pas = repere.width() / 10;
            Vecteur v = new Vecteur(-1 * pas, 0, 0);
            repere.translate(v);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }

    /**
     * Translate le modele sur Y positivement
     */
    @FXML
    void transYplus() {
        if (repere != null) {
            double pas = repere.height() / 10;
            Vecteur v = new Vecteur(0, pas, 0);
            repere.translate(v);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }

    /**
     * Translate le modele sur Y negativement
     */
    @FXML
    void transYmoins() {
        if (repere != null) {
            double pas = repere.height() / 10;
            Vecteur v = new Vecteur(0, -1 * pas, 0);
            repere.translate(v);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }

    /**
     * Translate le modele sur Z positivement
     */
    @FXML
    void transZplus() {
        if (repere != null) {
            double pas = repere.depth() / 10;
            Vecteur v = new Vecteur(0, 0, pas);
            repere.translate(v);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }

    /**
     * Translate le modele sur Z negativement
     */
    @FXML
    void transZmoins() {
        if (repere != null) {
            double pas = repere.depth() / 10;
            Vecteur v = new Vecteur(0, 0, -1 * pas);
            repere.translate(v);
        } else
            GraphicUtils.showMessage(Alert.AlertType.WARNING,
                    "Impossible d'appliquer la transformation, aucun repère n'est chargé !", null);
    }
    /**
 	* Ouvre un explorateur de fichier pour .ply
 	*/
    public void openExternal() {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        BorderPane root = new BorderPane();

        Text titre = new Text("Veuillez rentrer un lien internet finissant par .ply");
        TextField texte = new TextField();
        texte.setOnInputMethodTextChanged(event -> {
            String str = texte.getText().trim();
            if (str.endsWith(".ply")) {
                URL url;
                try {
                    url = new URL(str);
                } catch (Exception e) {
                    System.out.println("lien invalide !");
                }
            }
        });
        HBox validation = new HBox(15);
        validation.setAlignment(Pos.CENTER_LEFT);
        Label label = new Label("Enregistrer : ");
        RadioButton enregistrer = new RadioButton();
        Button importer = new Button("Importer");
        importer.setOnAction(event -> {
            String str = texte.getText().trim();
            if (str.endsWith(".ply")) {
                URL url = null;
                File file = null;
                try {
                    url = new URL(str);
                } catch (Exception e) {
                    System.out.println("lien invalide !");
                } finally {
                    try {
                        InputStream is = url.openConnection().getInputStream();
                        String[] elm = str.split("/");
                        String fileName = elm[elm.length - 1];
                        file = new File(dir + "/" + fileName);
                        file.createNewFile();
                        // System.out.println(file.getCanonicalPath());
                        // for(String line : PlyReader.getLines(file.getCanonicalPath()))
                        // System.out.println(line);
                        Files.copy(is, Paths.get(file.getCanonicalPath()), StandardCopyOption.REPLACE_EXISTING);
                        // for(String line : PlyReader.getLines(file.getCanonicalPath()))
                        // System.out.println(line);
                        loadFile(file.getCanonicalPath());
                    } catch (NoAsciiFileException e) {
                        GraphicUtils.showMessage(Alert.AlertType.ERROR, e.getMessage(), null);
                    } catch (IOException e) {
                        GraphicUtils.showMessage(Alert.AlertType.ERROR, "Fichier impossible à récupérer", null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (!enregistrer.isSelected()) {
                            file.delete();
                        } else {
                            refreshGestionnaire();
                        }
                        newStage.close();
                    }
                }
            }
        });
        validation.getChildren().addAll(importer, label, enregistrer);

        root.setTop(titre);
        root.setCenter(texte);
        root.setBottom(validation);

        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setOnCloseRequest(e -> {
            canvasUpdate();
        });
        newStage.setTitle("Importer un fichier en ligne");
        newStage.show();
        newStage.centerOnScreen();
    }
    /**
     * Ouvre la fenetre d'aide
     */
    public void openHelp() {

    }
    
    /**
     * Ouvre le moteur de recherche vers le gitlab du projet
     * @throws IOException
     * @throws URISyntaxException
     */

    public void openGitlab() throws IOException, URISyntaxException {
        Desktop.getDesktop()
                .browse(new URL("https://gitlab.univ-lille.fr/2021-projet-modelisation/projetmodej6").toURI());
    }
    /**
     * 
     * @param e
     */
    public void dragOver(DragEvent e) {
        Dragboard db = e.getDragboard();

        if (db.hasFiles()) {
            List<File> files = db.getFiles();
            files.removeIf(file -> !file.getName().endsWith(PLY_EXTENSION));
            if (files.size() == 1) {
                e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
        }
        e.consume();
    }
    /**
     * 
     * @param event
     */
    public void dragDrop(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles()) {
            List<File> files = db.getFiles();
            for (File file : files) {
                try {
                    loadFile(file.getCanonicalPath());
                } catch (NoAsciiFileException e) {
                    GraphicUtils.showMessage(Alert.AlertType.ERROR, e.getMessage(), null);
                } catch (IOException e) {
                    GraphicUtils.showMessage(Alert.AlertType.ERROR, "Fichier impossible à récupérer", null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * Effectue une action sur le modele en fonction de touches entr�es :
     * UPPER ARROW (Key UP) : Effectue une transformation du modele vers le haut
     * DOWN ARROW (Key DOWN) : Effectue une transformation du modele vers le bas
     * LEFT ARROW (Key LEFT) : Effectue une transformation du modele vers la gauche
     * RIGHT ARROW (Key RIGHT) : Effectue une transformation du modele vers la droite
     * 
     * @param e
     */
    public void keyTyped(KeyEvent e) {
        if (!fileText.isFocused()) {
            if (repere != null) {
                KeyCode code = e.getCode();
                if (translationMode) {
                    double pasWidth = repere.width() / 10;
                    double pasHeight = repere.width() / 10;
                    Vecteur v = new Vecteur(0, 0, 0);
                    switch (code) {
                        case UP:
                            v.setyCoordonnee(pasHeight);
                            break;
                        case DOWN:
                            v.setyCoordonnee(-1 * pasHeight);
                            break;
                        case LEFT:
                            v.setxCoordonnee(-1 * pasWidth);
                            break;
                        case RIGHT:
                            v.setxCoordonnee(pasWidth);
                            break;
                    }
                    repere.translate(v);
                } else {
                    switch (code) {
                        case UP:
                            repere.rotationX(-1 * PAS_ROT);
                            break;
                        case DOWN:
                            repere.rotationX(PAS_ROT);
                            break;
                        case LEFT:
                            repere.rotationY(-1 * PAS_ROT);
                            break;
                        case RIGHT:
                            repere.rotationY(PAS_ROT);
                            break;
                    }
                }
            }
        }
    }
    
    /**
     * Change le mode de l'interface entre Lightmode et Darkmode
     */
    public void lightClicked() {
        lightMode = !lightMode;
        lightImage.setImage(
                new Image(getClass().getResourceAsStream("/" + (lightMode ? "lightmode" : "darkmode") + ".png")));
    }

    /**
     * Rafraichissement de la liste des modèles
     */
    public void refreshFiles() {
        refreshGestionnaire();
    }

    /**
     * Recherche de modèles dans la liste
     */
    @FXML
    void fileTextChanged(KeyEvent e) {
        KeyCode keycode = e.getCode();
        if (keycode.equals(KeyCode.ENTER))
            mainStage.requestFocus();
        else {
            String text = fileText.getText();
            refreshGestionnaireFromText(text);
        }
    }
    /**
     * Raffraichit les �l�ments de la liste et ajoute le parametre
     * @param text
     */
    private void refreshGestionnaireFromText(String text) {
        gestionnaire.getItems().clear();
        List<Preview> previews = getPreviewsFromActualDirectory();
        List<Preview> previewsMatching = new ArrayList<>();
        for (Preview preview : previews) {
            if (preview.getFileName().contains(text)) {
                previewsMatching.add(preview);
            }
        }
        Collections.sort(previews);
        gestionnaire.getItems().setAll(previewsMatching);
    }

    /**
     * La liste des fichiers est cliquée (sélection d'un modèle)
     */
    public void gestionnaireClicked() {
        Preview fileName = gestionnaire.getSelectionModel().getSelectedItem();

        if (fileName == null)
            GraphicUtils.showMessage(Alert.AlertType.WARNING, "Aucun fichier choisi !", null);
        else {
            try {
                loadFile(dir.getName() + "/" + fileName.getFileName());
            } catch (NoAsciiFileException e) {
                GraphicUtils.showMessage(Alert.AlertType.ERROR, e.getMessage(), null);
            } catch (IOException e) {
                GraphicUtils.showMessage(Alert.AlertType.ERROR, e.getMessage(), e.getStackTrace().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * On tri la liste des fichiers par ordre alphabétique
     */
    /*
     * private void sortedListeView() { gestionnaire.getItems().sort((o1,o2)->{
     * if(o1.equals(o2)) return 0; if(o1.getFileName().charAt(0) >
     * o2.getFileName().charAt(0)) return 1; else return 0; }); }
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private List<Preview> getPreviewsFromActualDirectory() {
        List<Preview> previews = new ArrayList<>();
        List<File> listFiles = List.of(dir.listFiles());
        ImageView imgView = null;
        for (File file : listFiles) {
            if (file.isFile()) {
                if (file.getName().endsWith(PLY_EXTENSION)) {
                    imgView = new ImageView();
                    try {
                        imgView.setImage(
                                new Image(new File(dir + "/" + file.getName().replace(PLY_EXTENSION, PNG_EXTENSION))
                                        .toURI().toString()));
                    } catch (Exception e) {
                        Image img = GraphicUtils.getResourceImage("vide.png");
                        imgView.setImage(img);
                        e.printStackTrace();
                    } finally {
                        setupImageView(imgView);
                    }

                    Preview prev = new Preview(file.getName(), imgView);
                    previews.add(prev);
                }
            }
        }

        Collections.sort(previews);

        return previews;
    }

    private void refreshGestionnaire() {
        if (dir != null) {
            refreshGestionnaireFromText(fileText.getText());
        }

    }

    /**
     * On initialise les images de la preview
     *
     * @param imgView
     */
    private void setupImageView(ImageView imgView) {
        imgView.setFitWidth(WIDTH_PREVIEW);
        imgView.setFitHeight(HEIGHT_PREVIEW);
        imgView.setCache(true);
    }

    /**
     * Charge un fichier
     *
     * @param filename Nom du fichier
     * @throws Exception
     */
    private void loadFile(String filename) throws Exception {
        LoadingWindow lw = new LoadingWindow(stage, "Récupération du fichier", "Récupération du fichier en cours...");
        if (repere != null) {
            for (Canvas c : canvasList) {
                grid.getChildren().remove(c);
                repere.detach((Observer<Repere>) c);
            }
            canvasList.clear();
            repere.resetRepere();
            cons.clear();
        }
        try {
            repere = PlyReader.readFile(filename);
        } catch (Exception e) {
            lw.close();
            throw e;
        }

        // Nom du fichier actuellement affiché

        /*
         * repereWidth = w; repereHeight = h; repereDepth = repere.depth();
         */

        double width = grid.getWidth() / 2;
        double height = grid.getHeight() / 2;

        dessus = new ConnectedCanvas(width, height, repere, DrawMethods.DESSUS, aretesVisibles, pointsVisibles,
                colorMode);
        droite = new ConnectedCanvas(width, height, repere, DrawMethods.DROITE, aretesVisibles, pointsVisibles,
                colorMode);
        face = new ConnectedCanvas(width, height, repere, DrawMethods.FACE, aretesVisibles, pointsVisibles, colorMode);
        tranche = new ConnectedCanvas(width, height, repere, DrawMethods.TRANCHE, aretesVisibles, pointsVisibles, colorMode);

        canvasList.add(dessus);
        canvasList.add(droite);
        canvasList.add(face);
        canvasList.add(tranche);

        grid.add(dessus, 0, 0);
        grid.add(droite, 1, 0);
        grid.add(face, 0, 1);
        grid.add(tranche, 1, 1);

        lw.close();

        repere.initCanvas();

        autoEchelle();
        repere.center();

        String fileToPng = filename;
        String removeDotPly = "";
        if (fileToPng.contains(PLY_EXTENSION)) {
            removeDotPly = fileToPng.replace(PLY_EXTENSION, "");
        }

        fileToPng = removeDotPly;

        exportCanvasToPNG(dir + "/" + new File(fileToPng).getName() + PNG_EXTENSION);
    }
    /**
     * Prend une capture du canvas et en ressort une image png
     * @param fileName
     */
    public void exportCanvasToPNG(String fileName) {
        WritableImage img = face.snapshot(new SnapshotParameters(), null);
        try {
            File outputFile = new File(fileName);
            ImageIO.write(SwingFXUtils.fromFXImage(img, null), "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * R�adapte le canvas pour optimiser la visualisation
     */
    private void autoEchelle() {
        double width = grid.getWidth() / 2;
        double height = grid.getHeight() / 2;
        double w = repere.width();
        double h = repere.height();

        double zoom = w >= h ? (width / w) * 0.75 : (height / h) * 0.75;
        // double zoom = w >= width ? (height / h) : (width / w);

        System.out.println("grid width : " + width + " - grid height : " + height + " | repere width : " + w
                + " - repere height : " + h + " | zoom : " + zoom);

        repere.homothetie(zoom);
    }
    /**
     * Initialise les boutons de l'interface
     */
    private void initToggleButtons() {
        initToggleButton(colorsButton, colorMode);
        initToggleButton(eclairageButton, eclairageMode);
        initToggleButton(tranchesButton, tranchesMode);
        initToggleButton(selectionButton, selectionMode);
        initToggleButton(pointsButton, pointsVisibles);
        initToggleButton(aretesButton, aretesVisibles);
        initToggleButton(horlogeButton, controleurHorloge);
    }
    /**
     * Initialise un bouton de l'interface
     */
    
    public void initToggleButton(Button button, boolean bool) {
        if (bool) {
            button.getStyleClass().add("buttonSelected");
        } else {
            button.setStyle("-fx-background-color: none;");
        }
    }
    /**
     * Met a jour le canvas
     */
    public void canvasUpdate() {
        for (Canvas c : canvasList) {
            ConnectedCanvas cc = (ConnectedCanvas) c;
            cc.setAretesVisibles(aretesVisibles);
            cc.setPointsVisibles(pointsVisibles);
            cc.setColorDisplay(colorMode);
            cc.update(repere);
        }
    }

}