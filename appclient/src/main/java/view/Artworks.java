package view;

import domain.Artwork;
import javafx.application.Application;
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

import java.util.ArrayList;
import java.util.List;

public class Artworks extends Application {
    private List<Artwork> artworks;
    private GridPane gpArtworks;
    private Artwork artworki;
    private static final int NUMBER = 10;

    public Artworks(List<Artwork> artworks){
        this.artworks = artworks != null ? artworks : new ArrayList<>();
    }

    public Artworks(Artwork artwork){
        this.artworks = new ArrayList<>();
        this.artworks.add(artwork);
    }

    public Artworks(){
        this.artworks = new ArrayList<>();
        Artwork artwork = new Artwork();
        artwork.setTitle("zzz");
        this.artworks.add(artwork);
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Artworks");

        Image backButtonImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/voltar.jpeg");
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

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


        gpArtworks = new GridPane();
        gpArtworks.setAlignment(Pos.CENTER);
        gpArtworks.setHgap(10);
        gpArtworks.setVgap(10);


        int row = 0, col = 0;
        for(Artwork artwork : artworks){
            Button btnartwork = new Button(artwork.getTitle());
            gpArtworks.add(btnartwork, col, row);
            col++;
            if (col == 3){
                col = 0;
                row++;
            }
        }

        fetchRandomArtworks(listArtwork());

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: fuchsia;");
        vbLayout.getChildren().addAll(gpArtworks, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchRandomArtworks(List<Artwork> lArtwork ) {
        gpArtworks.getChildren().clear();
        for (int i = 0; i < lArtwork.size(); i++) {
            Artwork artwork = lArtwork.get(i);
            Label imageView = new Label(artwork.getTitle());
            ImageView imageView1 = new ImageView(artwork.getThumbnail());
            gpArtworks.add(imageView1, i, 0);
            gpArtworks.add(imageView, i, 1);
        }
    }


    private List<Artwork> listArtwork() {

        ArrayList<Artwork> allArtworks = new ArrayList<>();
        for (int i = 1; i <= NUMBER; i++) {
            Artwork artwork = new Artwork();
            artwork.setTitle("ArtworkTitle" + i);
            artwork.setThumbnail("https://d32dm0rphc51dk.cloudfront.net/--eR7DMEH7_ZVCAE5Oh9mw/medium.jpg");
            allArtworks.add(artwork);
        }

        return allArtworks;
    }
}
