package services;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Partner;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling operations related to Partner entities.
 * Implements the IService interface for Partner objects.
 */
public class PartnerService implements IService<Partner> {

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();

    /**
     * Creates a new Partner in the persistent storage.
     *
     * @param apiUrl The URL of the API where the partner is to be created.
     * @param partner The partner object to be created.
     */
    public void create(String apiUrl, Partner partner) {
        String json = gson.toJson(partner);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request postRequest = new Request.Builder().url(apiUrl).post(body).build();

        try (Response response = httpClient.newCall(postRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                Partner created = gson.fromJson(response.body().string(), Partner.class);

                // Handle the created partner object as needed
            } else {
                logErrorResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider a logging framework
        }
    }


    /**
     * Retrieves all partners from the persistent storage.
     *
     * @param apiUrl The URL of the API from where partners are to be fetched.
     * @return A list of Partner objects.
     */
    public List<Partner> getAll(String apiUrl) {
        List<Partner> all = new ArrayList<>();
        Request getRequest = new Request.Builder().url(apiUrl).build();

        try (Response response = httpClient.newCall(getRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String data = response.body().string();
                Type listType = new TypeToken<ArrayList<Partner>>(){}.getType();
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
