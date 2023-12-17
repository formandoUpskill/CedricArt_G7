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

public class ShowService implements IService<Exhibition> {

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();

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

    private void logErrorResponse(Response response) throws IOException {
        // Replace with a proper logging mechanism
        System.out.println("Error: " + response.code() + " - " + response.message());
        if (response.body() != null) {
            System.out.println(response.body().string());
        }
    }
}
