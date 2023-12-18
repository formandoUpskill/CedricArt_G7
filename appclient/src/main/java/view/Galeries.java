package view;

import domain.Partner;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import presenter.CedricArtPresenter;
import util.AppUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Galeries extends Application {
    private Stage primaryStage;
    private Button[] btnGalleries;
    private GridPane gpGalleries;
    private int numberPartners;
    private List<Partner> partners;


    private static final int NUM_MAX_PARTNERS_TO_DISPLAY = 15;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;
        List<Partner> partners = listPartners();

        primaryStage.setTitle("Galleries");
//        primaryStage.setResizable(false);

        Image backButtonImage = new Image(getClass().getResource("/images/return.jpg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        // Botões do segundo formulário

        btnGalleries = new Button[numberPartners];


        for(int i = 0; i < numberPartners; i++){
            System.out.println("partensr partensr" + i + ":" + partners.get(i) + "555555");
            btnGalleries[i] = createGalleryBtn(partners.get(i));
            btnGalleries[i].setStyle("-fx-shape: \"M15 0 L18.09 11.36 L30 13.64 L21.82 22.11 L24.09 34.64 L15 28.18 L5.91 34.64 L8.18 22.11 L0 13.64 L11.91 11.36 Z\"; -fx-text-fill: #FF8C00; -fx-background-color: #F0F0F0;");
            btnGalleries[i].setPrefSize(300,750);
            //btnGalleries[i].setWrapText(true);
        }
        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: #048DC3;");
        btnBack.setPrefSize(400,20);



        btnBack.setOnAction(event -> {
            Main main = new Main();
            try {
                main.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        gpGalleries = new GridPane();
        gpGalleries.setAlignment(Pos.CENTER);
        gpGalleries.setHgap(5);
        gpGalleries.setVgap(5);

        fetchRandomGalleries(partners);

        //gpGalleries.setStyle("-fx-grid-lines-visible: true; -fx-border-color: black; -fx-border-width: 1;");


        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: #191970;");
        vbLayout.getChildren().addAll(gpGalleries, vbBack);
        vbLayout.setSpacing(50);
        vbLayout.setAlignment(Pos.CENTER);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(vbLayout, bounds.getWidth(), bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createGalleryBtn(Partner partner){
        Button button = new Button(partner.getName());

        button.setOnAction(event ->  openMenu(partner));

        return button;
    }

    private void openMenu(Partner partner){

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), gpGalleries);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setOnFinished(event -> {
            Menu menu = new Menu(partner);

            try {
                primaryStage.close();
                menu.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        fadeTransition.play();
    }

    private void fetchRandomGalleries(List<Partner> lPartners ) {
        List<Button> shuffleButtons = Arrays.asList(btnGalleries);
        Collections.shuffle(shuffleButtons);
        for (int i = 0; i < this.numberPartners; i++) {
            gpGalleries.add(shuffleButtons.get(i), i % 5, i / 4);
        }
    }


    private List<Partner> listPartners(){
        CedricArtPresenter presenter = new CedricArtPresenter();
        List<Partner> selectPartners;

        List<Partner> allPartners = presenter.getAllPartners();
        if (allPartners.size() < NUM_MAX_PARTNERS_TO_DISPLAY){
            selectPartners = allPartners;
        }else {
            selectPartners = AppUtils.getRandomNumberOfElementsOfAList(allPartners, NUM_MAX_PARTNERS_TO_DISPLAY);

        }
            this.numberPartners = selectPartners.size();
            System.out.println(this.numberPartners);
            return selectPartners;
    }
}
