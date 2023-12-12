package view;

import domain.Artist;
import domain.Artwork;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class ArtistsInfo extends Application {
    private Label lblBiography;
    private Label lblArtwork;
    private GridPane gridPane;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("ArtistsInfo");

        Image backButtonImage = new Image(getClass().getResource("/images/voltar.jpeg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        lblBiography = new Label("Biography");
        lblArtwork = new Label("Artwork");

        gridPane = createGridPane();

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: YellowGreen; ");
        btnBack.setPrefSize(100,20);

        btnBack.setOnAction(event -> {
            Artists artists = new Artists();
            try {
                artists.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox vbArtistsInfo = new VBox(10);
        vbArtistsInfo.setAlignment(Pos.CENTER);
        vbArtistsInfo.getChildren().addAll(lblBiography, lblArtwork);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: olive;");
        vbLayout.getChildren().addAll(vbArtistsInfo, gridPane, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateInfo(Artist artist, List<Artwork> artworks) {
        Platform.runLater(() ->{
            String biographyText = "Biography of " + artist.getName();
            lblBiography.setText(biographyText);
            gridPane.getChildren().clear();

            int col = 0, row = 0;
            for (Artwork artwork : artworks){
                Label lblArtwork = new Label(artwork.getTitle());
                Image artworkImage = new Image(artwork.getThumbnail());
                ImageView artworkImageView = new ImageView(artworkImage);
                artworkImageView.setFitWidth(50);
                artworkImageView.setFitHeight(50);
                gridPane.add(lblArtwork, col, row);
                gridPane.add(artworkImageView, col, row + 1);
                col++;
                if (col == 4){
                    col = 0;
                    row += 2;
                }
            }
        });
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        int col = 0;
        int row = 0;

        return gridPane;
    }
}