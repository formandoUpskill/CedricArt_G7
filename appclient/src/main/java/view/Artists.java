package view;

import domain.Artist;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class Artists extends Application {
    private ComboBox<Artist> cmbArtists;
    private ArtistsInfo artistsInfo;
    private Stage primaryStage;
    private ObservableList<Artist> overArtist;
    private List<Artist> artists;
    private static final int NUMBER = 10;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        primaryStage.setTitle("Artists");

        artistsInfo = new ArtistsInfo();

        overArtist = FXCollections.observableArrayList();

        Image backgroundImage = new Image(getClass().getResource("/images/Artists.jpeg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);

        Image backButtonImage = new Image(getClass().getResource("/images/voltar.jpeg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        artists = listArtist();

        cmbArtists = new ComboBox<>(overArtist);
        cmbArtists.setPromptText("Select Artist");

        cmbArtists.setConverter(new StringConverter<Artist>() {
            @Override
            public String toString(Artist artist) {
                return (artist != null) ? artist.getName() : null;
            }

            @Override
            public Artist fromString(String string) {
                // Convert the string back to your object if needed
                return null;
            }
        });

        fetchRandomArtist(listArtist());

        cmbArtists.setOnAction(event -> showArtistsInfoForm());

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: YellowGreen; ");
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

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: blue;");
        vbLayout.getChildren().addAll(backgroundView, cmbArtists, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbLayout, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showArtistsInfoForm(){
        Artist selectArtist = cmbArtists.getValue();
        List<Artwork> artworks = getArtworksForArtist(selectArtist);

            artistsInfo.updateInfo(selectArtist, artworks);
            try {
                primaryStage.close();
                artistsInfo.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    private List<Artwork> getArtworksForArtist(Artist artist){
        List<Artwork> artworks = new ArrayList<>();
        for (int i = 1; i <= NUMBER; i++){
            Artwork artwork = new Artwork();
            artwork.setTitle("ArtworkTitle " + i);
            artwork.setThumbnail("https://example.com/thumbnail" + i + ".jpg");
            artworks.add(artwork);
        }
        return artworks;
    }

    private void fetchRandomArtist(List<Artist> lArtist ) {
        overArtist.addAll(lArtist);
    }


    private List<Artist> listArtist() {

        ArrayList<Artist> allArtists = new ArrayList<>();
        for (int i = 1; i <= NUMBER; i++) {
            Artist artist = new Artist();
            artist.setName("ArtistName" + i);
            artist.setId(String.valueOf(i));
            allArtists.add(artist);
        }
        return allArtists;
    }
}
