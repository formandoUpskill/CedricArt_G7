package services;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Exhibition;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for handling operations related to Exhibition entities.
 * Implements the IService interface for Exhibition objects.
 */
public class ShowService implements IService<Exhibition> {

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();


    /**
     * Creates a new Exhibition in the persistent storage.
     *
     * @param apiUrl The URL of the API where the exhibition is to be created.
     * @param exhibition The exhibition object to be created.
     */
    public void create(String apiUrl, Exhibition exhibition) {
        String json = gson.toJson(exhibition);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        Request postRequest = new Request.Builder().url(apiUrl).post(body).build();

        try (Response response = httpClient.newCall(postRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                Exhibition created = gson.fromJson(response.body().string(), Exhibition.class);
                // Handle the created exhibition object as needed
            } else {
                logErrorResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider a logging framework
        }
    }


    /**
     * Retrieves all exhibitions from the persistent storage.
     *
     * @param apiUrl The URL of the API from where exhibitions are to be fetched.
     * @return A list of Exhibition objects.
     */
    public List<Exhibition> getAll(String apiUrl) {
        List<Exhibition> all = new ArrayList<>();
        Request getRequest = new Request.Builder().url(apiUrl).build();

        try (Response response = httpClient.newCall(getRequest).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String data = response.body().string();
                Type listType = new TypeToken<ArrayList<Exhibition>>(){}.getType();
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
