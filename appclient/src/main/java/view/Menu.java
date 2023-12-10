package view;

import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Menu extends Application {
    private Partner partner;
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

        primaryStage.setTitle("Menu - " + partner.getName());

        Image backButtonImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/voltar.jpeg");
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        // Crie 4 botões na terceira forma
        Button btnArtwork = new Button("Artworks");
        btnArtwork.setStyle("-fx-font-size: 30;");
        Button btnArtists = new Button("Artists");
        btnArtists.setStyle("-fx-font-size: 25;");
        Button btnExhibition = new Button("Exhibitions");
        btnExhibition.setStyle("-fx-font-size: 30;");
        Button btnGenes = new Button("Genes");
        btnGenes.setStyle("-fx-font-size: 25;");
        Button btnBack = new Button("", backButtonImageView);

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
            try {
                artists.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnArtwork.setOnAction(event -> {
            List<Artwork> artworks = partner.getAllArtworks();
            ArtworksList artworksList = new ArtworksList(artworks);
            try {
                artworksList.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        btnExhibition.setOnAction(event -> {
            List<Exhibition> exhibitions = partner.getAllExhibitions();
            ExhibitionsList exhibitionsList = new ExhibitionsList(exhibitions);
            try {
                exhibitionsList.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
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

        Scene scene = new Scene(vbLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
