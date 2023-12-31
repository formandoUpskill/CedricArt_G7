package view;


import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import server.RunAPIServer;

/**
 * Main class represents the entry point of the CedricArt application.
 * This class extends the Application class and launches the user interface.
 */
public class Main extends Application {
    private Stage primaryStage;

    RunAPIServer server;
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Overrides the start method from the Application class.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an error occurs during the start process.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

       server=  new RunAPIServer();
       server.run();

        this.primaryStage = primaryStage;
        try {
            primaryStage.setTitle("CedricArt");


            // Adicione uma imagem de fundo
            Image backgroundImage = new Image(getClass().getResource("/images/Artsy.jpg").toExternalForm());
            ImageView backgroundView = new ImageView(backgroundImage);

            // Botão "Entrar"
            Button btnStart = new Button("Enter");

            btnStart.setStyle("-fx-background-color: #B22222; -fx-font-weight: bold; -fx-text-fill: white; -fx-pref-width: 100px; -fx-pref-height: 100px;" + "-fx-shape: \"M15 0 L29.39 9.39 L24.1 25.45 L9.9 25.45 L4.61 9.39 Z\";");

            btnStart.setOnAction(e -> {
                btnStart.setStyle("-fx-border-color: green; -fx-border-width: 4px;" + "-fx-pref-width: 100px; -fx-pref-height: 100px;" + "-fx-shape: \"M15 0 L29.39 9.39 L24.1 25.45 L9.9 25.45 L4.61 9.39 Z\";");
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(event -> openGalleriesForm());
                pause.play();
            });

            VBox vbBackground = new VBox();
            vbBackground.getChildren().add(backgroundView);
            vbBackground.setMargin(backgroundView, new Insets(10));
            vbBackground.setAlignment(Pos.TOP_CENTER);

            VBox vbStart = new VBox();
            vbStart.getChildren().add(btnStart);
            vbStart.setAlignment(Pos.BOTTOM_CENTER);
            vbStart.setMargin(btnStart, new Insets(30));


            StackPane layout = new StackPane();

            layout.setStyle("-fx-background-color: #778899;");
            layout.getChildren().addAll(vbBackground, vbStart);
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


    /**
     * Stops the application, closing any running processes.
     */
    public void stop() {
        System.out.println("The program is now stopping.");
       server.stop();
        // Add any cleanup code here
    }
    /**
     * Opens the second form (Galeries) of the application.
     */
    private void openGalleriesForm() {
        Galeries galeries = new Galeries();
        try {
            galeries.start(new Stage());
            primaryStage.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
