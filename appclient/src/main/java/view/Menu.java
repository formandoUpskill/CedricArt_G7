package view;

import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.net.URL;
import java.util.List;

public class Menu extends Application {
    private Partner partner;
    private RotateTransition rotate;
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }

    public Menu (Partner partner){
        this.partner = partner;
    }

    public Menu(){
        Partner partner = new Partner();
        partner.setName("hhhh");
        this.partner = partner;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Menu - " + partner.getName());

        Image backButtonImage = new Image(getClass().getResource("/images/voltar.jpeg").toExternalForm());

        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        // Crie 4 botões na terceira forma
        Button btnArtwork = new Button("Artworks");
        btnArtwork.setStyle("-fx-font-size: 30;" + "-fx-shape: \"M15 0 A15 15 0 1 0 30 0 A15 15 0 1 0 0 0 Z\";");
        Button btnArtists = new Button("Artists");
        btnArtists.setStyle("-fx-font-size: 25;");
        Button btnExhibition = new Button("Exhibitions");
        btnExhibition.setStyle("-fx-font-size: 30;");
        Button btnGenes = new Button("Genes");
        btnGenes.setStyle("-fx-font-size: 25;" + "-fx-shape: \"M15 0 A15 15 0 1 0 30 0 A15 15 0 1 0 0 0 Z\";");
        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: lightgreen; ");
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
            Artists artists = new Artists();

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
            List<Artwork> artworks = partner.getAllArtworks();
            Artworks artworksList = new Artworks();

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
            List<Exhibition> exhibitions = partner.getAllExhibitions();
            ExhibitionsList exhibitionsList = new ExhibitionsList(exhibitions);

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
        vbLayout.setStyle("-fx-background-color: fuchsia;");
        vbLayout.getChildren().addAll(vbMenu, vbBack);
        vbLayout.setAlignment(Pos.CENTER);
        vbLayout.setSpacing(100);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private RotateTransition rotateTransition(int angle) {
        RotateTransition rotate = new RotateTransition(Duration.seconds(1));
        rotate.setByAngle(angle);
        return rotate;
    }

    private void applyRotationTransition(Button btn, int angle) {
        rotate = rotateTransition(angle);
        rotate.setNode(btn);
        rotate.play();
    }

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
