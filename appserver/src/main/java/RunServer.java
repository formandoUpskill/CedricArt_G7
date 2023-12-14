


import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;


import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.DBStorage;

import static spark.Spark.*;

/**
 * Useful resources:
 * <p>https://dzone.com/articles/building-simple-restful-api</p>
 * <p>https://www.baeldung.com/spark-framework-rest-api</p>
 *
 */
public class RunServer {

    private static final Logger logger = LoggerFactory.getLogger(RunServer.class);

    public static void main(String[] args) {
        // Default port is 4567, hence we're running at http://localhost:4567/<endpoint>
        // The following endpoints are implemented
        // GET /clients | retrieves all clients
        // GET /clients/:id | retrieves the client with <id>
        // POST /clients | creates a new client from json data
        // DELETE / clients/:id | deletes the client with <id>
        //
        // GET /clients/:id/purchases | retrieves all purchases of client with <id>
        // ...

        logger.info("Starting Main server at {localhost:4567}", new Date().toString());

        /*  INSTANTIATE STORAGE */
        // MemoryStorage storage = new MemoryStorage();
        DBStorage storage = new DBStorage();

        //   Storage storage = new Storage();

        /* INSTANTIATE GSON CONVERTER */


        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
                //.setPrettyPrinting()
                .create();

        /* CONFIGURE END POINTS */



        // BEGIN SHOWS

        path("/shows", () -> {

            get("", (request, response) -> {
                response.type("application/json");

                List<Exhibition> shows;

                String partner_id = request.queryParams("partner_id");

                // Foi passado este query parameter? Se sim, procurar apenas os os shows daquele partner
                if(partner_id != null) {
                    shows= storage.getAllExhibitionsByPartner(partner_id);
                }

                shows = storage.getAllExhibitions();

                return gson.toJson( shows );
            });


            get("/:id", (request, response) -> {

                String exhibitionId = request.params(":id");


                // Se não existir, retorna 'null'
                Exhibition exhibition = storage.getExhibition(exhibitionId);


                System.out.println("shows get /:id " + exhibition );


                response.type("application/json");

                if(exhibition == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "exhibition not found");
                    return jsonObject.toString();
                }

                return gson.toJson(exhibition);
            });



            post("", (request, response) -> {
                // Devemos receber um Gene serializado em JSON (no body)

                String data = request.body();

                Exhibition exhibition = gson.fromJson(data, Exhibition.class);


                Exhibition created = storage.createExhibition(exhibition);

                response.type("application/json");
                return gson.toJson(created);
            });

        });

        // END SHOWS


        // BEGIN PARTNERS

        path("/partners", () -> {

            get("", (request, response) -> {
                response.type("application/json");

                List<Partner> partners = storage.getAllPartners();

                return gson.toJson( partners );
            });


            get("/:id", (request, response) -> {

                String partnerId = request.params(":id");

                System.out.println("partnerId " + partnerId);

                // Se não existir, retorna 'null'
                Partner partner = storage.getPartner(partnerId);

                response.type("application/json");

                if(partner == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Client not found");
                    return jsonObject.toString();
                }

                return gson.toJson(partner);
            });



            post("", (request, response) -> {
                // Devemos receber um Gene serializado em JSON (no body)

                String data = request.body();

                Partner partner = gson.fromJson(data, Partner.class);

                Partner created = storage.createPartner(partner);

                response.type("application/json");
                return gson.toJson(created);
            });

        });

        // END PARTNERS

        // BEGGIN ARTWORKS

        path("/artworks", () -> {

        get("", (request, response) -> {
            response.type("application/json");

            List<Artwork> artworks;

            String partner_id = request.queryParams("partner_id");

            // Foi passado este query parameter? Se sim, procurar apenas os os shows daquele partner
            if(partner_id != null) {
                artworks= storage.getAllArtworksByPartner(partner_id);
                return gson.toJson( artworks );
            }

            String exhibition_id = request.queryParams("show_id");
            if(exhibition_id != null) {


                artworks= storage.getAllArtworksByExhibition(exhibition_id);
                return gson.toJson( artworks );
            }


            artworks = storage.getAllArtworks();


            return gson.toJson( artworks );
        });


        get("/:id", (request, response) -> {

                String artworkId = request.params(":id");



                // Se o client não existir, retorna 'null'
                Artwork artwork = storage.getArtworkWithPartnerAndGenes(artworkId);

                response.type("application/json");

                if(artwork == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Client not found");
                    return jsonObject.toString();
                }

                return gson.toJson(artwork);
            });


            post("", (request, response) -> {
                // Devemos receber um Gene serializado em JSON (no body)

                String data = request.body();


                Artwork artwork = gson.fromJson(data, Artwork.class);


                Artwork created = storage.createArtwork(artwork);


                response.type("application/json");
                return gson.toJson(created);
            });

        });



        // BEGGIN GENES

        path("/genes", () -> {

            get("", (request, response) -> {
                response.type("application/json");

                List<Gene> genes;

                genes = storage.getAllGenes();
                return gson.toJson( genes );
            });


            get("/:id", (request, response) -> {

                String id= request.params(":id");

                System.out.println("id " + id);

                // Se o gene não existir, retorna 'null'
                Gene gene = storage.getGene(id);

                response.type("application/json");

                if(gene == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Client not found");
                    return jsonObject.toString();
                }

                return gson.toJson(gene);
            });


            post("", (request, response) -> {
                // Devemos receber um Gene serializado em JSON (no body)

                Gene gene = gson.fromJson(request.body(), Gene.class);

                Gene created = storage.createGene(gene);

                response.type("application/json");
                return gson.toJson(created);
            });

        });

        // END GENES

        // BEGIN



        path("/artists", () -> {

            get("", (request, response) -> {
                response.type("application/json");

                List<Artist> artists;

                String partner_id = request.queryParams("partner_id");

                // Foi passado este query parameter? Se sim, procurar apenas os os shows daquele partner
                if(partner_id != null) {
                    artists= storage.getAllArtistsByPartner(partner_id);
                    return gson.toJson( artists );
                }


                artists = storage.getAllArtists();

                return gson.toJson( artists );
            });


            get("/:id", (request, response) -> {

                String id= request.params(":id");

                System.out.println("id " + id);

                // Se o gene não existir, retorna 'null'
                Artist artist = storage.getArtist(id);

                response.type("application/json");

                if(artist == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "artist not found");
                    return jsonObject.toString();
                }

                return gson.toJson(artist);
            });


            post("", (request, response) -> {
                // Devemos receber um Gene serializado em JSON (no body)

                Artist artist = gson.fromJson(request.body(), Artist.class);

                Artist created = storage.createArtist(artist);

                response.type("application/json");
                return gson.toJson(created);
            });

        });


        /**
         *
         */

        exception(Exception.class, (exception, request, response) -> {
            response.type("application/json");
            response.status(400);
            response.body("{\"message\":\"Custom internal server error message\"}");

            exception.printStackTrace();
        });
    }
}
