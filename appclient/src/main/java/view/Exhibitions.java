package view;


import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import presenter.CedricArtPresenter;
import util.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class Exhibitions extends Application {
    private List<Exhibition> exhibitions;
    private ComboBox<Exhibition> cmbExhibition;
    private static final int NUMBER = 10;
    private ExhibitionInfo exhibitionInfo;
    private ObservableList<Exhibition> overExhibition;
    private Stage primaryStage;
    private Exhibition exhibition;
    private Partner partner;
    private static final int NUM_MAX_EXHIBITIONS_TO_DISPLAY = 10;
    private int numberExhibitions;

    public Exhibitions(List<Exhibition> exhibitions){
        this.exhibitions = exhibitions != null ? exhibitions : new ArrayList<>();
    }

    public Exhibitions(){
        this(new ArrayList<>());
    }

    public Exhibitions(Partner partner) {
        this.partner = partner;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        primaryStage.setTitle("Exhibitions");

        exhibitionInfo = new ExhibitionInfo(partner);

        overExhibition = FXCollections.observableArrayList();

        Image backButtonImage = new Image(getClass().getResource("/images/voltar.jpeg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: YellowGreen; ");
        btnBack.setPrefSize(100,20);

        btnBack.setOnAction(event -> {
            Menu menu = new Menu(partner);
            try {
                menu.start(new Stage());
                primaryStage.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        exhibitions = listExhibitions();

        cmbExhibition = new ComboBox<>(overExhibition);
        cmbExhibition.setPromptText("Select Exhibition");
        cmbExhibition.setConverter(new StringConverter<Exhibition>() {
            @Override
            public String toString(Exhibition exhibition) {
                return (exhibition != null) ? exhibition.getDescription() : null;
            }

            @Override
            public Exhibition fromString(String string) {
                // Convert the string back to your object if needed
                return null;
            }
        });

        fetchRandomExhibition(listExhibitions());

        cmbExhibition.setOnAction(event -> showExhibitionInfoForm());

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: SlateGray;");
        vbLayout.getChildren().addAll(cmbExhibition, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchRandomExhibition(List<Exhibition> lExhibition ) {
        overExhibition.addAll(lExhibition);
    }


    private List<Exhibition> listExhibitions(){
        CedricArtPresenter presenter = new CedricArtPresenter();
        List<Exhibition> selectExhibition;
        List<Exhibition> allExhibitions = presenter.getAllExhibitionsByPartner(partner.getId());

        if (allExhibitions.size() < NUM_MAX_EXHIBITIONS_TO_DISPLAY){
            selectExhibition = allExhibitions;
        }else {
            selectExhibition = AppUtils.getRandomExhibitions(allExhibitions, NUM_MAX_EXHIBITIONS_TO_DISPLAY);

        }
        this.numberExhibitions = selectExhibition.size();
        System.out.println(this.numberExhibitions);
        return selectExhibition;
    }

    private void showExhibitionInfoForm(){
        Exhibition selectExhibition = cmbExhibition.getValue();

        exhibitionInfo.updateInfo(selectExhibition);
        try {
            primaryStage.close();
            exhibitionInfo.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
