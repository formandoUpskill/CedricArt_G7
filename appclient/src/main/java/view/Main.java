package view;


import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.RunServer;


public class Main extends Application {
    private Stage primaryStage;

    RunServer server;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        server=  new RunServer();
        server.run();

        this.primaryStage = primaryStage;
        try {
            primaryStage.setTitle("CedricArt");




            // Adicione uma imagem de fundo
            Image backgroundImage = new Image(getClass().getResource("/images/Artsy.jpg").toExternalForm());
            ImageView backgroundView = new ImageView(backgroundImage);

            // Botão "Entrar"
            Button btnStart = new Button("Enter");
            btnStart.setPrefSize(70, 44);
            btnStart.setStyle("-fx-background-color: red; -fx-font-weight: bold; -fx-text-fill: white;" + "-fx-shape: \"M15 0 L29.39 9.39 L24.1 25.45 L9.9 25.45 L4.61 9.39 Z\";");

            btnStart.setOnAction(e -> {
                btnStart.setStyle("-fx-border-color: green; -fx-border-width: 4px;" + "-fx-shape: \"M15 0 L29.39 9.39 L24.1 25.45 L9.9 25.45 L4.61 9.39 Z\";");
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(event -> openForm2());
                pause.play();
            });

            // Layout da primeira forma
            StackPane layout = new StackPane();
            layout.setStyle("-fx-background-color: #778899;");
            layout.getChildren().addAll(backgroundView, btnStart);
            layout.setAlignment(Pos.BOTTOM_CENTER);

            Screen screen = Screen.getPrimary();
            Rectangle2D bounds = screen.getVisualBounds();

            Scene scene = new Scene(layout, bounds.getWidth(), bounds.getHeight());
            primaryStage.setScene(scene);
            primaryStage.show();




        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void stop() {
        System.out.println("The program is now stopping.");
        server.stop();
        // Add any cleanup code here
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
