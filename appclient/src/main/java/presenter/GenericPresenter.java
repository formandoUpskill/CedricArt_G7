package presenter;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.List;

public class GenericPresenter<T> {

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();

    public T get(String apiUrl, String id, Class<T> clazz) {
        Request getRequest = new Request.Builder().url(apiUrl + id).build();
        return processRequest(getRequest, clazz);
    }

    public List<T> getAll(String apiUrl, TypeToken<List<T>> typeToken) {
        Request getRequest = new Request.Builder().url(apiUrl).build();

        System.out.println("getAll " + apiUrl);

        Type listType = typeToken.getType();
        return processRequest(getRequest, listType);
    }

    private <R> R processRequest(Request request, Type type) {
        try (Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return gson.fromJson(response.body().charStream(), type);
            } else {
                logErrorResponse(response);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider a logging framework
        }
        return null; // Ensure the method always returns a value
    }
    private void logErrorResponse(Response response) throws IOException {
        // Replace with a proper logging mechanism
        System.out.println("Error: " + response.code() + " - " + response.message());
        if (response.body() != null) {
            System.out.println(response.body().string());
        }
    }
}
