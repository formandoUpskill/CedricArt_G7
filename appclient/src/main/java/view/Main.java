package view;


import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        try {
            primaryStage.setTitle("Artsy");

            // Adicione uma imagem de fundo
            Image backgroundImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/Artsy.jpg");
            ImageView backgroundView = new ImageView(backgroundImage);

            // Botão "Entrar"
            Button btnStart = new Button("Enter");
            btnStart.setPrefSize(70, 40);
            btnStart.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-text-fill: white;");

            btnStart.setOnAction(e -> {
                openForm2();

            });


            // Layout da primeira forma
            StackPane layout = new StackPane();
            layout.getChildren().addAll(backgroundView, btnStart);
            layout.setAlignment(Pos.BOTTOM_CENTER);

            Scene scene = new Scene(layout, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void openForm2() {
        Galeries galeries = new Galeries();
        try {
            galeries.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
