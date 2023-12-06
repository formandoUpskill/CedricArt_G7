package view;

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

public class ArtistsInfo extends Application {
    private Label lblBiography;
    private Label lblArtwork;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("ArtistsInfo");

        Image backButtonImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/voltar.jpeg");
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        lblBiography = new Label("Biography");
        lblArtwork = new Label("Artwork");

        GridPane gridPane = createGridPane();

        Button btnBack = new Button("", backButtonImageView);

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

        Scene scene = new Scene(vbLayout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateInfo(String artistName) {
        Platform.runLater(() ->{
            String biographyText = "Biography of ";
            String artworkText = "Artwork by ";

            lblBiography.setText(biographyText);
            lblArtwork.setText(artworkText);
        });
    }

    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Adicionar rótulos e imagens
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 2; col++) {
                Label label = new Label("Item " + (row * 2 + col + 1));

                // Exemplo de imagem (substitua pelo caminho correto)
                Image image1 = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/GridExemplo" + (row * 1 + col + 1) + ".jpeg");
                ImageView imageView1 = new ImageView(image1);
                imageView1.setFitWidth(50);
                imageView1.setFitHeight(50);

                Image image2 = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/GridExemplo2" + (row * 1 + col + 2) + ".jpeg");
                ImageView imageView2 = new ImageView(image2);
                imageView2.setFitWidth(50);
                imageView2.setFitHeight(50);

                // Adicionar rótulo e imagem à GridPane
                gridPane.add(label, col * 2, row);
                gridPane.add(imageView1, col * 2, row + 1); // Iniciar abaixo dos rótulos
                gridPane.add(imageView2, col * 2 + 1, row + 1);
            }
        }

        return gridPane;
    }

}
