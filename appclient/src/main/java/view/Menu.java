package view;

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
       // partner.setId("1");
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
        Button btnArtwork = new Button("Artwork");
        Button btnArtists = new Button("Artists");
        Button btnExhibition = new Button("Exhibition");
        Button btnGenes = new Button("Genes");
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


        VBox vbMenu = new VBox(10);
        vbMenu.setAlignment(Pos.CENTER);
        vbMenu.getChildren().addAll(btnArtwork, btnArtists, btnExhibition, btnGenes);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: fuchsia;");
        vbLayout.getChildren().addAll(vbMenu, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
