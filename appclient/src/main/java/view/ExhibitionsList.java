package view;

import domain.Artwork;
import domain.Exhibition;
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

public class ExhibitionsList extends Application {
    private List<Exhibition> exhibitions;
    private GridPane gpExhibition;

    public ExhibitionsList(List<Exhibition> exhibitions){
        this.exhibitions = exhibitions != null ? exhibitions : new ArrayList<>();
    }

    public ExhibitionsList(){
        this(new ArrayList<>());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Exhibitions");

        Image backButtonImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/voltar.jpeg");
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: lightgreen; ");
        btnBack.setPrefSize(100,20);

        btnBack.setOnAction(event -> {
            Menu menu = new Menu();
            try {
                menu.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        gpExhibition = new GridPane();
        gpExhibition.setAlignment(Pos.CENTER);
        gpExhibition.setHgap(10);
        gpExhibition.setVgap(10);

        int row = 0, col = 0;
        for(Exhibition exhibition : exhibitions){
            Button btnartwork = new Button(exhibition.getName());
            gpExhibition.add(btnartwork, col, row);
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
        vbLayout.getChildren().addAll(gpExhibition, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
