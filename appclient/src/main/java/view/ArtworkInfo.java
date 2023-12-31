package view;

import domain.Artwork;
import domain.Partner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import presenter.CedricArtPresenter;

/**
 * ArtworkInfo class represents a JavaFX application for displaying detailed information about an artwork.
 * This class extends the Application class.
 */

public class ArtworkInfo extends Application {
    private Label lblTitle;
    private Partner partner;
    private ImageView imThumbnail;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Constructor for the ArtworkInfo class.
     *
     * @param partner The partner associated with the artwork.
     */

    public ArtworkInfo(Partner partner) {
        this.partner = partner;
    }

    /**
     * Overrides the start method from the Application class.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an error occurs during the start process.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Artwork Info " + partner.getName());

        Image backButtonImage = new Image(getClass().getResource("/images/return.jpg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: #048DC3; ");
        btnBack.setPrefSize(100,20);

        lblTitle = new Label();

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
        vbArtworkInfo.getChildren().addAll(lblTitle, imThumbnail);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: olive;");
        vbLayout.getChildren().addAll(vbArtworkInfo, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(vbLayout, bounds.getWidth(), bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Updates the displayed information with details about the selected artwork.
     *
     * @param artwork The artwork whose information will be displayed.
     */

    public void updateInfo(Artwork artwork) {
        Platform.runLater(() ->{
            CedricArtPresenter presenter = new CedricArtPresenter();
            Artwork artwork1 = presenter.getArtwork(artwork.getId());

            if (!(artwork1.getThumbnail().equals("") )){

                Image thumbnail = new Image(artwork1.getThumbnail());
                imThumbnail.setImage(thumbnail);
            }

            Text name = new Text("Title: ");
            name.setStyle("-fx-font-weight: bold;");
            Text value = new Text(artwork1.getTitle());
            value.setFont(new Font(16));
            TextFlow textFlow = new TextFlow(name, value);

            lblTitle.setGraphic(textFlow);
            lblTitle.setFont(new Font(20));


        });
    }
}
