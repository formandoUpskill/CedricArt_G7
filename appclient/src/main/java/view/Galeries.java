package view;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Galeries extends Application {
    private Stage primaryStage;
    private Button btnGallery1;
    private Button btnGallery2;
    private Button btnGallery3;
    private Button btnGallery4;
    private VBox vbGalleries;
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
        btnGallery1 = new Button("Gallery 1");
        btnGallery2 = new Button(
                "Gallery 2");
        btnGallery3 = new Button("Gallery 3");
        btnGallery4 = new Button("Gallery 4");
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
        vbGalleries = new VBox(10);
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

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), vbGalleries);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setOnFinished(event -> {
            Menu menu = new Menu(galleryName);
            try {
                menu.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        fadeTransition.play();
    }
}
