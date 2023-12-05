package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Galeries extends Application {
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        primaryStage.setTitle("Galleries");
        primaryStage.setResizable(false);

        Image backButtonImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/voltar.jpeg");
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        // Botões do segundo formulário
        Button btnGallery1 = new Button("Gallery 1");
        Button btnGallery2 = new Button("Gallery 2");
        Button btnGallery3 = new Button("Gallery 3");
        Button btnGallery4 = new Button("Gallery 4");
        Button btnBack = new Button("", backButtonImageView);

        btnGallery1.setOnAction(event -> openMenu("Gallery 1"));
        btnGallery2.setOnAction(event -> openMenu("Gallery 2"));
        btnGallery3.setOnAction(event -> openMenu("Gallery 3"));
        btnGallery4.setOnAction(event -> openMenu("Gallery 4"));

        btnBack.setOnAction(event -> {
            Main main = new Main();
            try {
                main.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        // Layout do segundo formulário
        VBox vbGalleries = new VBox(10);
        vbGalleries.getChildren().addAll(btnGallery1, btnGallery2, btnGallery3, btnGallery4);
        vbGalleries.setAlignment(Pos.CENTER);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.getChildren().addAll(vbGalleries, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openMenu(String galleryName){
        Menu menu = new Menu(galleryName);
        try {
            menu.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
