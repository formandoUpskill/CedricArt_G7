package view;

import domain.Artwork;
import domain.Partner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import presenter.CedricArtPresenter;
import util.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class Artworks extends Application {
    private List<Artwork> artworks;
    private ComboBox<Artwork> cmbArtworks;
    private Stage primaryStage;
    private ArtworkInfo artworkInfo;
    private ObservableList<Artwork> overArtwork;
    private Partner partner;
    private static final int NUM_MAX_ARTWORKS_TO_DISPLAY = 10;
    private int numberArtworks;

    public Artworks(List<Artwork> artworks){
        this.artworks = artworks != null ? artworks : new ArrayList<>();
    }

    public Artworks(Artwork artwork){
        this.artworks = new ArrayList<>();
        this.artworks.add(artwork);
    }

    public Artworks(Partner partner){

        this.artworks = new ArrayList<>();
        Artwork artwork = new Artwork();
        this.partner = partner;
        this.artworks.add(artwork);
    }
    public Artworks(){
        this.artworks = new ArrayList<>();
        Artwork artwork = new Artwork();
        this.artworks.add(artwork);
    }

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Artworks");

        artworkInfo = new ArtworkInfo(partner);

        overArtwork = FXCollections.observableArrayList();

        Image backgroundImage = new Image(getClass().getResource("/images/Artwork.jpeg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);

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


        artworks = listArtwork();

        cmbArtworks = new ComboBox<>(overArtwork);
        cmbArtworks.setPromptText("Select Artwork");
        cmbArtworks.setConverter(new StringConverter<Artwork>() {
            @Override
            public String toString(Artwork artwork) {
                return (artwork != null) ? artwork.getTitle() : null;
            }

            @Override
            public Artwork fromString(String string) {
                // Convert the string back to your object if needed
                return null;
            }
        });

        fetchRandomArtworks(listArtwork());

        cmbArtworks.setOnAction(event -> showArtworkInfoForm());


        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: fuchsia;");
        vbLayout.getChildren().addAll(backgroundView ,cmbArtworks, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void fetchRandomArtworks(List<Artwork> lArtwork ) {
        overArtwork.addAll(lArtwork);
    }


    private List<Artwork> listArtwork(){
        CedricArtPresenter presenter = new CedricArtPresenter();

        List<Artwork> allArtworks = presenter.getAllArtworksByPartner(partner.getId());


        this.numberArtworks = allArtworks.size();
        System.out.println(this.numberArtworks);
        return allArtworks;
    }

    private void showArtworkInfoForm(){
        Artwork selectArtwork = cmbArtworks.getValue();

        artworkInfo.updateInfo(selectArtwork);
        try {
            primaryStage.close();
            artworkInfo.start(new Stage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
