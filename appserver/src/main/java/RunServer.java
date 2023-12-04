

import java.time.LocalDate;
import java.util.Date;

import static spark.Spark.*;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.DBStorage;

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

        System.out.println("111111");

        DBStorage storage = new DBStorage();

        System.out.println("222222");

        /* INSTANTIATE GSON CONVERTER */


        Gson gson = new GsonBuilder()
               // .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                //.setPrettyPrinting()
                .create();

        System.out.println("GsonBuilder" + gson.toString());

        /* CONFIGURE END POINTS */
        get("/artworks", (request, response) -> {

            System.out.println(  "get  artworks");


            response.type("application/json");


            System.out.println("storage.getAllArtworks() " + storage.getAllArtworks().size());


            return gson.toJson( storage.getAllArtworks() );
        });


//        get("/artworks/:id", (request, response) -> {
//            response.type("application/json");
//            String idStr = request.params(":id");
//
//            int artworkId = 0;
//            try {
////                artworkId = Integer.parseInt(idStr);
//            } catch (NumberFormatException e) {
//                response.status(500);
//                return new MessageResponse("Invalid Artwork id (%s), expecting a number.", idStr);
//            }
//            Artworkiii artworkiii = storage.getArtwork(artworkId);
//            if (artworkiii == null) {
//                response.status(404);
//                return new MessageResponse("Artwork with id %s not found.", idStr);
//            }
//            return gson.toJson(artworkiii);
//
//        }

    }
}