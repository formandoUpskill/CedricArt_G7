

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static spark.Spark.*;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.DBStorage;
import g7.upskill.ips.adapters.LocalDateAdapter;
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
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                //.setPrettyPrinting()
                .create();

        /* CONFIGURE END POINTS */
        get("/artworks", (request, response) -> {
            response.type("application/json");

            List artworks = storage.getAllArtworks();

            System.out.println("artworks.size() " + artworks.size());

            return gson.toJson( artworks );
        });

/*
        get("/artworks/:id", (request, response) -> {
            response.type("application/json");
            String idStr = request.params(":id");

            int artworkId = 0;
            try {
                artworkId = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                response.status(500);
                return new MessageResponse("Invalid Artwork id (%s), expecting a number.", idStr);
            }

            Artwork artwork = storage.getArtwork(artworkId);
            if(artwork == null) {
                response.status(404);
                return new MessageResponse("Artwork with id %s not found.", idStr);
            }
            return gson.toJson( artwork );
        });
*/
        /*
        post("/clients", (request, response) -> {
            response.type("application/json");

            Client received = gson.fromJson(request.body(), Client.class);

            Client created = storage.createClient(received.getName(), received.getAge(), received.getEmail(), received.getTelephoneNumber(),
                    received.getAddress().getStreet(), received.getAddress().getCity(), received.getAddress().getCode(),
                    received.isPremium());

            return gson.toJson( created );
        });

        delete("/clients/:id", (request, response) -> {
            response.type("application/json");

            String idStr = request.params(":id");

            int clientId = 0;
            try {
                clientId = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                response.status(500);
                return new MessageResponse("Invalid client id (%s), expecting a number.", idStr);
            }

            Client deletedClient = storage.deleteClient(clientId);
            if(deletedClient == null) {
                response.status(404);
                return new MessageResponse("Client with id %s not found.", idStr);
            }

            return gson.toJson( deletedClient );
        });

        get("/clients/:id/purchases", (request, response) -> {
            response.type("application/json");

            String idStr = request.params(":id");

            int clientId = 0;
            try {
                clientId = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                response.status(500);
                return new MessageResponse("Invalid client id (%s), expecting a number.", idStr);
            }

            Client client = storage.getClient(clientId);
            if(client == null) {
                response.status(404);
                return new MessageResponse("Client with id %s not found.", idStr);
            }

            return gson.toJson( client.getPurchaseHistory() );
        });
        */

    }
}
