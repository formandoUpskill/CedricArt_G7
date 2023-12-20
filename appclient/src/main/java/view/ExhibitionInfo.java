package view;

import domain.Exhibition;
import domain.Partner;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import presenter.CedricArtPresenter;

/**
 * ExhibitionInfo class represents a JavaFX application for displaying detailed information about an exhibition.
 * This class extends the Application class.
 */
public class ExhibitionInfo extends Application {

    private Label lblDescription;
    private Label lblName;
    private Label lblStart;
    private Label lblEnd;
    private Label lblStatus;
    private ImageView imThumbnail;
    private Partner partner;

    /**
     * Constructor for the ExhibitionInfo class with a partner.
     *
     * @param partner The partner associated with the exhibition.
     */
    public ExhibitionInfo(Partner partner) {
        this.partner = partner;
    }

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

        primaryStage.setTitle("Exhibitions Info" + partner.getName());

        Image backButtonImage = new Image(getClass().getResource("/images/return.jpg").toExternalForm());
        ImageView backButtonImageView = new ImageView(backButtonImage);
        backButtonImageView.setFitHeight(20);
        backButtonImageView.setFitWidth(20);

        Button btnBack = new Button("", backButtonImageView);
        btnBack.setStyle("-fx-shape: \"M20 10 L30 30 L10 30 Z\";" + "-fx-background-color: #048DC3; ");
        btnBack.setPrefSize(100,20);

        lblDescription = new Label();
        lblName = new Label();
        lblStart = new Label();
        lblEnd = new Label();
        lblStatus = new Label();
        imThumbnail = new ImageView();

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
        vbExhibitionInfo.getChildren().addAll(lblName, lblDescription, lblStart, lblEnd, lblStatus, imThumbnail);

        VBox vbBack = new VBox(10);
        vbBack.getChildren().add(btnBack);
        vbBack.setAlignment(Pos.BOTTOM_RIGHT);
        vbBack.setPadding(new Insets(5));

        VBox vbLayout = new VBox(10);
        vbLayout.setStyle("-fx-background-color: olive;");
        vbLayout.getChildren().addAll(vbExhibitionInfo, vbBack);
        vbLayout.setAlignment(Pos.CENTER);

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        Scene scene = new Scene(vbLayout, bounds.getWidth(), bounds.getHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Updates the displayed information with details about the selected exhibition.
     *
     * @param exhibition The exhibition whose information will be displayed.
     */
    public void updateInfo(Exhibition exhibition) {
        Platform.runLater(() ->{
            CedricArtPresenter presenter = new CedricArtPresenter();
            Exhibition exhibition1 = presenter.getExhibition(exhibition.getId());

            if (!(exhibition1.getThumbnail().equals("") )){

                Image thumbnail = new Image(exhibition1.getThumbnail());
                imThumbnail.setImage(thumbnail);
            }

            lblName.setText("Name: " + exhibition1.getName());
            lblName.setFont(new Font(20));
            lblDescription.setText("Description: " + exhibition1.getDescription());
            lblDescription.setFont(new Font(16));
            lblDescription.setWrapText(true);
            lblStart.setText("Start at: " + exhibition1.getStart_at().toString());
            lblStart.setFont(new Font(14));
            lblEnd.setText("End at: " + exhibition1.getEnd_at().toString());
            lblEnd.setFont(new Font(14));
            lblStatus.setText("Status: " + exhibition1.getStatus());
            lblStatus.setFont(new Font(16));

        });
    }
}
