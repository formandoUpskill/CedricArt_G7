package view;

import domain.Artwork;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ArtworksList extends Application {
    private List<Artwork> artworks;
    private GridPane gpArtworks;

    public ArtworksList(List<Artwork> artworks){
        this.artworks = artworks != null ? artworks : new ArrayList<>();
    }

    public ArtworksList(){
        this(new ArrayList<>());
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

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: fuchsia;");
        vbLayout.getChildren().addAll(gpArtworks, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
