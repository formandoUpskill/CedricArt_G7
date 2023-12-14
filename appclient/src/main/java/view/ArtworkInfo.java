package view;

import domain.Artwork;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presenter.CedricArtPresenter;

public class ArtworkInfo extends Application {
    private Label lbTitle;
    private Partner partner;
    private ImageView imThumbnail;

    public static void main(String[] args) {
        launch(args);
    }

    public ArtworkInfo(Partner partner) {
        this.partner = partner;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("ArtworkInfo");

        Image backButtonImage = new Image(getClass().getResource("/images/voltar.jpeg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: YellowGreen; ");
        btnBack.setPrefSize(100,20);

        lbTitle = new Label();
        imThumbnail = new ImageView();

        btnBack.setOnAction(event -> {
            Artworks artworks = new Artworks(partner);
            try {
                artworks.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox vbArtworkInfo = new VBox(10);
        vbArtworkInfo.setAlignment(Pos.CENTER);
        vbArtworkInfo.getChildren().addAll(lbTitle, imThumbnail);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: olive;");
        vbLayout.getChildren().addAll(vbArtworkInfo, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateInfo(Artwork artwork) {
        Platform.runLater(() ->{
            CedricArtPresenter presenter = new CedricArtPresenter();
            Artwork artwork1 = presenter.getArtwork(artwork.getId());

            Image image = new Image(artwork1.getThumbnail());

            lbTitle.setText(artwork1.getTitle());
            imThumbnail.setImage(image);
        });
    }

}
