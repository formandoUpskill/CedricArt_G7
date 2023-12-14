package view;

import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ExhibitionInfo extends Application {

    private Label lblDescription;
    private Partner partner;

    public ExhibitionInfo(Partner partner) {
        this.partner = partner;
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("ExhibitionsInfo");

        Image backButtonImage = new Image(getClass().getResource("/images/voltar.jpeg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: YellowGreen; ");
        btnBack.setPrefSize(100,20);

        lblDescription = new Label();

        btnBack.setOnAction(event -> {
            Exhibitions exhibition = new Exhibitions(partner);
            try {
                exhibition.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        VBox vbExhibitionInfo = new VBox(10);
        vbExhibitionInfo.setAlignment(Pos.CENTER);
        vbExhibitionInfo.getChildren().add(lblDescription);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: olive;");
        vbLayout.getChildren().addAll(vbExhibitionInfo, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void updateInfo(Exhibition exhibition) {
        Platform.runLater(() ->{
            String description = exhibition.getDescription();

            lblDescription.setText(description);
        });
    }
}
