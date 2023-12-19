package view;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import presenter.CedricArtPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Artists class represents a JavaFX application for displaying artist information.
 * This class extends the Application class.
 */
public class Artists extends Application {
    private ComboBox<Artist> cmbArtists;
    private ArtistsInfo artistsInfo;
    private Stage primaryStage;
    private ObservableList<Artist> overArtist;
    private List<Artist> artists;
    private static final int NUMBER = 10;
    private Partner partner;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Constructor for the Artists class.
     *
     * @param partner The partner associated with the artists.
     */

    public Artists(Partner partner) {
        this.partner = partner;
    }

    /**
     * Overrides the start method from the Application class.
     *
     * @param primaryStage The primary stage for the application.
     * @throws Exception If an error occurs during the start process.
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        /**
         * Displays the artist information form when a specific artist is selected.
         */

        this.primaryStage = primaryStage;

        primaryStage.setTitle("Artists " + partner.getName());

        artistsInfo = new ArtistsInfo(partner);

        overArtist = FXCollections.observableArrayList();

        Image backgroundImage = new Image(getClass().getResource("/images/Artists.jpeg").toExternalForm());
        ImageView backgroundView = new ImageView(backgroundImage);

        Image backButtonImage = new Image(getClass().getResource("/images/return.jpg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        artists = listArtists();

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

        fetchRandomArtist(listArtists());

        cmbArtists.setOnAction(event -> showArtistsInfoForm());

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: #048DC3; ");
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

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: blue;");
        vbLayout.getChildren().addAll(backgroundView, cmbArtists, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(vbLayout, bounds.getWidth(), bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showArtistsInfoForm(){
        Artist selectArtist = cmbArtists.getValue();
        List<Artwork> artworks = getArtworksForArtist(selectArtist);

            artistsInfo.updateInfo(selectArtist);
            try {
                primaryStage.close();
                artistsInfo.start(new Stage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }

    /**
     * Retrieves artworks associated with a specific artist.
     *
     * @param artist The artist for which artworks are retrieved.
     * @return A list of artworks.
     */

    private List<Artwork> getArtworksForArtist(Artist artist){
        List<Artwork> artworks = new ArrayList<>();
        CedricArtPresenter presenter = new CedricArtPresenter();
        //presenter.getAll
        for (int i = 1; i <= NUMBER; i++){
            Artwork artwork = new Artwork();
            artwork.setTitle("ArtworkTitle " + i);
            artwork.setThumbnail("https://example.com/thumbnail" + i + ".jpg");
            artworks.add(artwork);
        }
        return artworks;
    }

    /**
     * Fetches a list of random artists and adds them to the ComboBox.
     *
     * @param lArtist The list of artists to be added.
     */

    private void fetchRandomArtist(List<Artist> lArtist ) {
        overArtist.addAll(lArtist);
    }


    /**
     * Retrieves a list of artists for a specific partner.
     *
     * @return A list of artists.
     */
    private List<Artist> listArtists(){
        CedricArtPresenter presenter = new CedricArtPresenter();

        List<Artist> allArtists = presenter.getAllArtistsByPartner(partner.getId());

        return allArtists;
    }
}
