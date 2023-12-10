package view;

import domain.Artist;
import domain.Partner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Artists extends Application {
    private ComboBox<String> cmbArtists;
    private ArtistsInfo artistsInfo;
    private Stage primaryStage;
    private Partner partner;
    private Artist artist;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        primaryStage.setTitle("Artists");

        artistsInfo = new ArtistsInfo();

        Image backgroundImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/Artists.jpeg");
        ImageView backgroundView = new ImageView(backgroundImage);

        Image backButtonImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/voltar.jpeg");
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        cmbArtists = new ComboBox<>();
        cmbArtists.getItems().addAll("Artist 1", "Artist 2");
        cmbArtists.setPromptText("Select Artist");

        cmbArtists.setOnAction(event -> showArtistsInfoForm());

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: lightgreen; ");
        btnBack.setPrefSize(100,20);

        btnBack.setOnAction(event -> {
            Menu menu = new Menu();
            try {
                menu.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: blue;");
        vbLayout.getChildren().addAll(backgroundView, cmbArtists, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showArtistsInfoForm(){
        String selectArtist = cmbArtists.getValue();

            artistsInfo.updateInfo(selectArtist);
            try {
                primaryStage.hide();
                artistsInfo.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
}
