package view;

import domain.Partner;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Galeries extends Application {
    private Stage primaryStage;
    private Button[] btnGalleries;
    private GridPane gpGalleries;
    private static final int NUMBER = 10;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        primaryStage.setTitle("Galleries");
//        primaryStage.setResizable(false);

        Image backButtonImage = new Image("file:/home/RicardoReis/Desktop/Upskill/Projecto Final/Imagens/voltar.jpeg");
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        // Botões do segundo formulário

        btnGalleries = new Button[NUMBER];
        List<Partner> partners = listPartners();

        for(int i = 0; i < NUMBER; i++){
            btnGalleries[i] = createGalleryBtn(partners.get(i));
            btnGalleries[i].setWrapText(true);
        }
        Button btnBack = new Button("", backButtonImageView);



        btnBack.setOnAction(event -> {
            Main main = new Main();
            try {
                main.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


        fetchRandomGalleries(listPartners());

        gpGalleries = new GridPane();
        gpGalleries.setAlignment(Pos.CENTER);
        gpGalleries.setHgap(10);
        gpGalleries.setVgap(10);

        gpGalleries.add(btnGalleries[0], 0, 0);
        gpGalleries.add(btnGalleries[1], 1, 2);
        gpGalleries.add(btnGalleries[2], 2, 4);
        gpGalleries.add(btnGalleries[3], 3, 6);
        gpGalleries.add(btnGalleries[4], 4, 8);
        gpGalleries.add(btnGalleries[5], 2, 10);
        gpGalleries.add(btnGalleries[6], 5, 4);
        gpGalleries.add(btnGalleries[7], 6, 18);
        gpGalleries.add(btnGalleries[8], 6, 0);
        gpGalleries.add(btnGalleries[9], 0, 18);
        //vbGalleries.setStyle("-fx-grid-lines-visible: true; -fx-border-color: black; -fx-border-width: 1;");


        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: navy;");
        vbLayout.getChildren().addAll(gpGalleries, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createGalleryBtn(Partner partner){
        Button button = new Button(partner.getName());
        button.setOnAction(event -> openMenu(partner));
        return button;
    }

    private void openMenu(Partner partner){

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), gpGalleries);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setOnFinished(event -> {
            Menu menu = new Menu(partner);
            try {
                menu.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        fadeTransition.play();
    }

    private void fetchRandomGalleries(List<Partner> lPartners ) {
        for (int i = 0; i < lPartners.size(); i++) {
            Partner partner = lPartners.get(i);
            btnGalleries[i].setText(partner.getName());
        }
    }


    private List<Partner> listPartners(){

        ArrayList<Partner> allPartners = new ArrayList<>();
        for(int i = 1; i <= NUMBER; i++) {
            Partner partner = new Partner();
            partner.setName("partnerName" + i);
            partner.setId(String.valueOf(i));
            allPartners.add(partner);
        }

        return allPartners;
    }
}
