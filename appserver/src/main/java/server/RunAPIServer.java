package server;


import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.DBStorage;
import spark.Request;
import spark.Response;

import java.time.OffsetDateTime;
import java.util.List;

import static spark.Spark.*;

/**
 * Server class for running the API server.
 * Handles setup and management of API endpoints.
 */
public class RunAPIServer {

    private Gson gson;
    private DBStorage storage;

    /**
     * Constructor for RunAPIServer.
     * Initializes Gson and storage components.
     */
    public RunAPIServer()
    {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
                //.setPrettyPrinting()
                .create();

        this.storage = new DBStorage();
    }


    private static final Logger logger = LoggerFactory.getLogger(RunAPIServer.class);


    /**
     * Starts the API server and sets up the endpoints.
     */
    public void run() {
        logger.info("Starting Main server at http://localhost:4567");

        setupEndpoints();

        exception(Exception.class, (exception, request, response) -> {
            handleException(exception, response);
        });
    }

    /**
     * Handles exceptions thrown during API calls.
     *
     * @param exception The caught exception.
     * @param response The Spark response object.
     */
    private void handleException(Exception exception, Response response) {
        logger.error("Unhandled exception: {}", exception.getMessage());
        setJsonResponse(response);
        response.status(500);
        response.body(createErrorResponse("Internal server error"));
    }

    /**
     * Creates a JSON error response.
     *
     * @param message The error message.
     * @return The JSON error response string.
     */
    private String createErrorResponse(String message) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", message);
        return jsonObject.toString();
    }

    /**
     * Sets the response type to JSON.
     *
     * @param response The Spark response object.
     */
    private void setJsonResponse(Response response) {
        response.type("application/json");
    }

    /**
     * Creates an error response with a specific status code and message.
     *
     * @param response The Spark response object.
     * @param statusCode The HTTP status code.
     * @param message The error message.
     * @return The JSON error response string.
     */
    private String createErrorResponse(Response response, int statusCode, String message) {
        response.status(statusCode);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", message);
        return jsonObject.toString();
    }

    /**
     * Sets up all API endpoints.
     */
    private void setupEndpoints() {
        setupShowsEndpoints();
        setupPartnersEndpoints();
        setupArtworksEndpoints();
        setupGenesEndpoints();
        setupArtistsEndpoints();

    }

    /**
     * SHOWS END POINTS
     */
    private void setupShowsEndpoints() {
        path("/shows", () -> {
            get("", this::handleGetAllShowsOrByPartner);
            get("/:id", this::handleGetShowById);
            post("", this::handlePostShow);
        });
    }


    private Object handleGetAllShowsOrByPartner(Request request, Response response) {
        setJsonResponse(response);

        String partnerId = request.queryParams("partner_id");
        List<Exhibition> shows = (partnerId != null) ?
                storage.getAllExhibitionsByPartner(partnerId) : storage.getAllExhibitions();

        return gson.toJson(shows);
    }

    private Object handleGetShowById(Request request, Response response) {
        setJsonResponse(response);
        String exhibitionId = request.params(":id");
        Exhibition exhibition = storage.getExhibition(exhibitionId);

        return (exhibition != null) ? gson.toJson(exhibition) : createErrorResponse(response, 404, "Exhibition not found");
    }


    private Object handlePostShow(Request request, Response response) {
        setJsonResponse(response);

        try {
            Exhibition exhibition = gson.fromJson(request.body(), Exhibition.class);

            // Validate the exhibition object if needed
            if (isValidExhibition(exhibition)) {
                Exhibition createdExhibition = storage.createExhibition(exhibition);
                return gson.toJson(createdExhibition);
            } else {
                return createErrorResponse(response, 400, "Invalid exhibition data");
            }
        } catch (JsonSyntaxException e) {
            // Log and handle JSON parsing errors
            return createErrorResponse(response, 400, "Invalid JSON format");
        } catch (Exception e) {
            // Handle other exceptions, possibly logging them
            return createErrorResponse(response, 500, "Internal server error");
        }
    }


    private boolean isValidExhibition(Exhibition exhibition) {
        // Implement validation logic for the exhibition object
        // Return true if valid, false otherwise
        return true;
    }


    /**
     * PARTNERs END POINTS
     */

    private void setupPartnersEndpoints() {
        path("/partners", () -> {
            get("", this::handleGetAllPartners);
            get("/:id", this::handleGetPartnerById);
            post("", this::handlePostPartner);
        });
    }

    private Object handleGetAllPartners(Request request, Response response) {
        setJsonResponse(response);
        List<Partner> partners = storage.getAllPartners();
        return gson.toJson(partners);
    }

    private Object handleGetPartnerById(Request request, Response response) {
        setJsonResponse(response);
        String partnerId = request.params(":id");
        Partner partner = storage.getPartner(partnerId);

        return (partner != null) ? gson.toJson(partner)
                : createErrorResponse(response, 404, "Client not found");
    }

    private Object handlePostPartner(Request request, Response response) {
        setJsonResponse(response);

        try {
            Partner partner = gson.fromJson(request.body(), Partner.class);
            // Add any necessary validation for the Partner object here
            Partner created = storage.createPartner(partner);
            return gson.toJson(created);
        } catch (JsonSyntaxException e) {
            // Log and handle JSON parsing errors
            return createErrorResponse(response, 400, "Invalid JSON format");
        } catch (Exception e) {
            // Handle other exceptions, possibly logging them
            return createErrorResponse(response, 500, "Internal server error");
        }
    }


    /**
     * ARTWORKS END POINTS
     */

    private void setupArtworksEndpoints() {
        path("/artworks", () -> {
            get("", this::handleGetAllArtworks);
            get("/:id", this::handleGetArtworkById);
            post("", this::handlePostArtwork);
        });
    }

    private Object handleGetAllArtworks(Request request, Response response) {
        setJsonResponse(response);

        String partnerId = request.queryParams("partner_id");
        String exhibitionId = request.queryParams("show_id");
        String artistId = request.queryParams("artist_id");

        List<Artwork> artworks;
        if (partnerId != null) {
            artworks = storage.getAllArtworksByPartner(partnerId);
        } else if (exhibitionId != null) {
            artworks = storage.getAllArtworksByExhibition(exhibitionId);
        } else if (artistId != null) {
            artworks = storage.getAllArtworksByArtist(artistId);
        } else {
            artworks = storage.getAllArtworks();
        }

        return gson.toJson(artworks);
    }

    private Object handleGetArtworkById(Request request, Response response) {
        setJsonResponse(response);
        String artworkId = request.params(":id");
        Artwork artwork = storage.getArtworkWithPartnerAndGenes(artworkId);

        return (artwork != null) ? gson.toJson(artwork)
                : createErrorResponse(response, 404, "Artwork not found");
    }

    private Object handlePostArtwork(Request request, Response response) {
        setJsonResponse(response);

        try {
            Artwork artwork = gson.fromJson(request.body(), Artwork.class);
            // Add any necessary validation for the Artwork object here
            Artwork created = storage.createArtwork(artwork);
            return gson.toJson(created);
        } catch (JsonSyntaxException e) {
            // Log and handle JSON parsing errors
            return createErrorResponse(response, 400, "Invalid JSON format");
        } catch (Exception e) {
            // Handle other exceptions, possibly logging them
            return createErrorResponse(response, 500, "Internal server error");
        }
    }


    /**
     * ARTISTS END POINTS
     */

    private void setupArtistsEndpoints() {
        path("/artists", () -> {
            get("", this::handleGetAllArtists);
            get("/:id", this::handleGetArtistById);
            post("", this::handlePostArtist);
        });
    }

    private Object handleGetAllArtists(Request request, Response response) {
        setJsonResponse(response);


        String partnerId = request.queryParams("partner_id");


        List<Artist> artists = (partnerId != null) ?
                storage.getAllArtistsByPartner(partnerId) :
                storage.getAllArtists();
        return gson.toJson(artists);
    }

    private Object handleGetArtistById(Request request, Response response) {
        setJsonResponse(response);
        String artistId = request.params(":id");
        Artist artist = storage.getArtist(artistId);

        return (artist != null) ? gson.toJson(artist)
                : createErrorResponse(response, 404, "Artist not found");
    }

    private Object handlePostArtist(Request request, Response response) {
        setJsonResponse(response);
        try {
            Artist artist = gson.fromJson(request.body(), Artist.class);
            // Add any necessary validation for the Artist object here
            Artist created = storage.createArtist(artist);
            return gson.toJson(created);
        } catch (JsonSyntaxException e) {
            // Log and handle JSON parsing errors
            return createErrorResponse(response, 400, "Invalid JSON format");
        } catch (Exception e) {
            // Handle other exceptions, possibly logging them
            return createErrorResponse(response, 500, "Internal server error");
        }
    }




    /**
     * GENE END POINTS
     */


    private void setupGenesEndpoints() {
        path("/genes", () -> {
            get("", this::handleGetAllGenes);
            get("/:id", this::handleGetGeneById);
            post("", this::handlePostGene);
        });
    }

    private Object handleGetAllGenes(Request request, Response response) {
        setJsonResponse(response);
        List<Gene> genes = storage.getAllGenes();
        return gson.toJson(genes);
    }

    private Object handleGetGeneById(Request request, Response response) {
        setJsonResponse(response);
        String geneId = request.params(":id");
        Gene gene = storage.getGene(geneId);

        return (gene != null) ? gson.toJson(gene)
                : createErrorResponse(response, 404, "Gene not found");
    }

    private Object handlePostGene(Request request, Response response) {
        setJsonResponse(response);
        try {
            Gene gene = gson.fromJson(request.body(), Gene.class);
            // Add any necessary validation for the Gene object here
            Gene created = storage.createGene(gene);
            return gson.toJson(created);
        } catch (JsonSyntaxException e) {
            // Log and handle JSON parsing errors
            return createErrorResponse(response, 400, "Invalid JSON format");
        } catch (Exception e) {
            // Handle other exceptions, possibly logging them
            return createErrorResponse(response, 500, "Internal server error");
        }
    }



    /**
     * Stops the API server.
     */
    public void stop()
    {

        logger.info("Stopping the server");
        System.exit(0);
    }
}
