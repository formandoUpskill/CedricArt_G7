package services;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Gene;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling operations related to Gene entities.
 * Implements the IService interface for Gene objects.
 */
public class GeneService implements IService<Gene> {

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();

    /**
     * Creates a new Gene in the persistent storage.
     *
     * @param apiUrl The URL of the API where the gene is to be created.
     * @param gene The gene object to be created.
     */
    public void create(String apiUrl, Gene gene) {
        String json = gson.toJson(gene);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request postRequest = new Request.Builder().url(apiUrl).post(body).build();

        try (Response response = httpClient.newCall(postRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                Gene created = gson.fromJson(response.body().string(), Gene.class);
                // Handle the created gene object as needed
            } else {
                logErrorResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider a logging framework
        }
    }


    /**
     * Retrieves all genes from the persistent storage.
     *
     * @param apiUrl The URL of the API from where genes are to be fetched.
     * @return A list of Gene objects.
     */
    public List<Gene> getAll(String apiUrl) {
        List<Gene> all = new ArrayList<>();
        Request getRequest = new Request.Builder().url(apiUrl).build();

        try (Response response = httpClient.newCall(getRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String data = response.body().string();
                Type listType = new TypeToken<ArrayList<Gene>>(){}.getType();
                all = gson.fromJson(data, listType);
            } else {
                logErrorResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider a logging framework
        }
        return all;
    }

    /**
     * Logs the error response from the HTTP request.
     *
     * @param response The HTTP response to log.
     * @throws IOException If an I/O error occurs.
     */
    private void logErrorResponse(Response response) throws IOException {
        // Replace with a proper logging mechanism
        System.out.println("Error: " + response.code() + " - " + response.message());
        if (response.body() != null) {
            System.out.println(response.body().string());
        }
    }
}
