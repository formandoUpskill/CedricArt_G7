


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;


import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.DBStorage;
import util.MessageResponse;

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
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                //.setPrettyPrinting()
                .create();

        /* CONFIGURE END POINTS */



        // BEGIN PARTNERS

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

                response.type("application/json");

                if(exhibition == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "exhibition not found");
                    return jsonObject.toString();
                }

                return gson.toJson(exhibition);
            });

        });

        // END SHOWS


        // BEGIN PARTNERS

        path("/partners", () -> {

            get("", (request, response) -> {
                response.type("application/json");

                List<Partner> partners = storage.getAllPartners();

                System.out.println("partners.size() " + partners.size());

                return gson.toJson( partners );
            });


            get("/:id", (request, response) -> {

                String partnerId = request.params(":id");

                System.out.println("artworkId " + partnerId);

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
            }

            artworks = storage.getAllArtworks();



            return gson.toJson( artworks );
        });


        get("/:id", (request, response) -> {

                String artworkId = request.params(":id");

            System.out.println("artworkId " + artworkId);

                // Se o client não existir, retorna 'null'
                Artwork artwork = storage.getArtwork(artworkId);

                response.type("application/json");

                if(artwork == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Client not found");
                    return jsonObject.toString();
                }

                return gson.toJson(artwork);
            });

        });

/*
        path("/clients", () -> {


            delete("/:id", (request, response) -> {
                String strId = request.params(":id");
                int clientId = Integer.parseInt(strId);

                Client existing = storage.getClient(clientId);

                response.type("application/json");

                if(existing == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Client not found");
                    return jsonObject.toString();
                }

                Client deletedClient = storage.deleteClient(clientId);

                return gson.toJson(deletedClient);
            });

            put("/:id", (request, response) -> {
                String strId = request.params(":id");
                int clientId = Integer.parseInt(strId);

                response.type("application/json");

                // Validar se existe client com ID especificado
                Client existing = storage.getClient(clientId);
                if(existing == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Client not found");
                    return jsonObject.toString();
                }

                Client clientInfo = gson.fromJson(request.body(), Client.class);

                // storage.updateClient tem de ter um client com o ID atribuído
                clientInfo.setId(clientId); // o do /:id
                Client updatedClient = storage.updateClient(clientInfo);


                return gson.toJson(updatedClient);
            });

            post("", (request, response) -> {
                // Devemos receber um Client serializado em JSON (no body)

                Client newClient = gson.fromJson(request.body(), Client.class);

                Client createdClient = storage.createClient(newClient);

                response.type("application/json");
                return gson.toJson(createdClient);
            });

            get("", (request, response) -> {
                response.type("application/json");

                List<Client> allClients = storage.getAllClients();

                String sortValue = request.queryParams("sortBy");
                // Foi passado este query parameter? Se sim, ordenar
                if(sortValue != null) {
                    if(sortValue.equalsIgnoreCase("name")) {
                        Collections.sort(allClients, (c1, c2) -> {
                            return c1.getName().compareToIgnoreCase(c2.getName());
                        });
                    }
                    // Outra ordenação
                }

                return gson.toJson(allClients);
            });

            get("/:id", (request, response) -> {

                String strId = request.params(":id");
                int clientId = Integer.parseInt(strId);

                // Se o client não existir, retorna 'null'
                Client client = storage.getClient(clientId);

                response.type("application/json");

                if(client == null) {
                    response.status(404);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("message", "Client not found");
                    return jsonObject.toString();
                }

                return gson.toJson(client);
            });


        });

*/

    }
}
