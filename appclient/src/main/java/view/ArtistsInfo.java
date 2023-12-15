package view;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import presenter.CedricArtPresenter;

import java.util.List;

public class ArtistsInfo extends Application {
    private Label lblBiography;
    private ImageView imThumbnail;
    private Label lblName;
    private Label lblHometown;
    private Partner partner;

    public static void main(String[] args) {
        launch(args);
    }

    public ArtistsInfo(Partner partner) {
        this.partner = partner;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("ArtistsInfo");

        Image backButtonImage = new Image(getClass().getResource("/images/voltar.jpeg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        lblBiography = new Label();
        imThumbnail = new ImageView();
        lblHometown = new Label();
        lblName = new Label();

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: YellowGreen; ");
        btnBack.setPrefSize(100,20);

        btnBack.setOnAction(event -> {
            Artists artists = new Artists(partner);
            try {
                artists.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox vbArtistsInfo = new VBox(10);
        vbArtistsInfo.setAlignment(Pos.CENTER);
        vbArtistsInfo.getChildren().addAll(lblName, lblHometown, lblBiography, imThumbnail);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: olive;");
        vbLayout.getChildren().addAll(vbArtistsInfo, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateInfo(Artist artist) {
        Platform.runLater(() -> {
            CedricArtPresenter presenter = new CedricArtPresenter();
            Artist artist1 = presenter.getArtist(artist.getId());

            Image thumbnail = new Image(artist1.getThumbnail());

            lblName.setText("Name: " + artist1.getName());
            lblName.setFont(new Font(20));
            lblHometown.setText("Hometown: " + artist1.getHometown());
            lblHometown.setFont(new Font(18));
            lblBiography.setText("Biography: " + artist1.getBiography());
            lblBiography.setFont(new Font(16));
            imThumbnail.setImage(thumbnail);
        });
    }
}