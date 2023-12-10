


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
                return gson.toJson( artworks );
            }

            String exhibition_id = request.queryParams("show_id");
            if(exhibition_id != null) {

                System.out.println("exhibition_idexhibition_idexhibition_id " + exhibition_id);
                artworks= storage.getAllArtworksByExhibition(exhibition_id);
                return gson.toJson( artworks );
            }

            artworks = storage.getAllArtworks();
            return gson.toJson( artworks );
        });


        get("/:id", (request, response) -> {

                String artworkId = request.params(":id");

            System.out.println("artworkId " + artworkId);

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

                System.out.println("request.body()" + data);

                Artwork artwork = gson.fromJson(data, Artwork.class);

                Artwork created = storage.createArtwork(artwork);

                System.out.println("created" + created);

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

                artists = storage.getAllArtists();

                System.out.println(artists);
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
