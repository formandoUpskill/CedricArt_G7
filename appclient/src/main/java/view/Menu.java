package view;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

/**
 * The Menu class represents the menu interface for the CedricArt application.
 * It provides options for navigating to different sections of the application.
 * The menu includes buttons for Artworks, Artists, Exhibitions, and Genes.
 * Each button triggers a rotation transition effect before navigating to the respective section.
 */
public class Menu extends Application {
    private Partner partner;
    private Artist artist;
    private RotateTransition rotate;
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Constructs a Menu for a specific partner.
     *
     * @param partner The partner associated with the menu.
     */
    public Menu (Partner partner){

        this.partner = partner;
    }

    public Menu(Artist artist){
        this.artist = artist;
    }

    /**
     * Constructs a Menu with default partner and artist objects.
     */
    public Menu(){
        Partner partner = new Partner();

        Artist artist = new Artist();
        this.artist = artist;
        this.partner = partner;
    }


    /**
     * Overrides the start method from the Application class.
     *
     * @param primaryStage The primary stage for the menu interface.
     * @throws Exception If an error occurs during the start process.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Menu - " + partner.getName());

        Image backButtonImage = new Image(getClass().getResource("/images/return.jpg").toExternalForm());

        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        // Crie 4 botões na terceira forma
        Button btnArtwork = new Button("Artworks");
        btnArtwork.setStyle("-fx-font-size: 30;");
        Circle circle = new Circle(50);
        btnArtwork.setShape(circle);
        Button btnArtists = new Button("Artists");
        btnArtists.setStyle("-fx-font-size: 25;");
        Button btnExhibition = new Button("Exhibitions");
        btnExhibition.setStyle("-fx-font-size: 30;");
        Button btnGenes = new Button("Genes");
        btnGenes.setStyle("-fx-font-size: 25;" + "-fx-shape: \"M15 0 A15 15 0 1 0 30 0 A15 15 0 1 0 0 0 Z\";");
        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: #048DC3;");
        btnBack.setPrefSize(200,20);

        btnBack.setOnAction(event -> {
            Galeries galeries = new Galeries();
            try {
                galeries.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnArtists.setOnAction(event -> {
            Artists artists = new Artists(partner);

            applyRotationTransition(btnArtists, -360);
            rotate.setOnFinished(rotateFinishedEvent ->{
                try {
                    artists.start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            rotate.play();
        });

        btnArtwork.setOnAction(event -> {
            Artworks artworksList = new Artworks(partner);

            applyRotationTransition(btnArtwork, 360);
            rotate.setOnFinished(rotateFinishedEvent -> {
                try {
                    artworksList.start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            rotate.play();
        });

        btnExhibition.setOnAction(event -> {

            Exhibitions exhibitionsList = new Exhibitions(partner);

            applyRotationTransition(btnExhibition, -360);
            rotate.setOnFinished(rotateFinishedEvent ->{
                try {
                    exhibitionsList.start(new Stage());
                    primaryStage.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            rotate.play();
        });

        btnGenes.setOnAction(event -> {
            applyRotationTransition(btnGenes, 360);
            rotate.setOnFinished(rotateFinishedEvent ->{
                showMaintenanceAlert();
            });
            rotate.play();
        });


        VBox vbMenu = new VBox(40);
        vbMenu.setAlignment(Pos.CENTER);
        vbMenu.getChildren().addAll(btnArtwork, btnArtists, btnGenes, btnExhibition);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: gold;");
        vbLayout.getChildren().addAll(vbMenu, vbBack);
        vbLayout.setAlignment(Pos.CENTER);
        vbLayout.setSpacing(100);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(vbLayout, bounds.getWidth(), bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a rotation transition with the specified angle.
     *
     * @param angle The angle of rotation for the transition.
     * @return A RotateTransition object.
     */
    private RotateTransition rotateTransition(int angle) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(1));
        rotate.setByAngle(angle);
        return rotate;
    }

    /**
     * Applies a rotation transition to a button with the specified angle.
     *
     * @param btn   The button to which the rotation is applied.
     * @param angle The angle of rotation for the transition.
     */
    private void applyRotationTransition(Button btn, int angle) {
        rotate = rotateTransition(angle);
        rotate.setNode(btn);
        rotate.play();
    }

    /**
     * Shows an alert indicating that the selected feature is under construction.
     */
    private void showMaintenanceAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Construction!!!");
        alert.setHeaderText(null);
        alert.setContentText("Loading...");
        alert.getDialogPane().setBackground(new Background(new BackgroundFill(Color.FIREBRICK, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
        alert.setX(800);
        alert.setY(300);
        alert.getDialogPane().setPrefSize(300, 100);

        ButtonType btnOk = new ButtonType("Ok");
        alert.getButtonTypes().setAll(btnOk);
        alert.show();
    }
}
