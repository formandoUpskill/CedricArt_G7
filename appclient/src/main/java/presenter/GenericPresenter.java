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

/**
 * A generic presenter for handling HTTP requests and parsing responses.
 *
 * @param <T> The type of object this presenter is responsible for.
 */
public class GenericPresenter<T> {

    private static final OkHttpClient httpClient = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();

    /**
     * Retrieves a single object from the specified API URL using its ID.
     *
     * @param apiUrl The base URL of the API.
     * @param id The identifier for the specific object.
     * @param clazz The class of the type T.
     * @return An object of type T, or null if not successful.
     */
    public T get(String apiUrl, String id, Class<T> clazz) {
        Request getRequest = new Request.Builder().url(apiUrl + id).build();
        return processRequest(getRequest, clazz);
    }

    /**
     * Retrieves all objects of type T from the specified API URL.
     *
     * @param apiUrl The URL of the API.
     * @param typeToken A TypeToken representing a List of T objects.
     * @return A List of objects of type T, or null if not successful.
     */
    public List<T> getAll(String apiUrl, TypeToken<List<T>> typeToken) {
        Request getRequest = new Request.Builder().url(apiUrl).build();

        System.out.println("getAll " + apiUrl);

        Type listType = typeToken.getType();
        return processRequest(getRequest, listType);
    }

    /**
     * Processes the HTTP request and returns the response deserialized to the specified type.
     *
     * @param <R> The type of the response object.
     * @param request The HTTP request to process.
     * @param type The Type of the response object.
     * @return An object of type R, or null if the request fails.
     */
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

    /**
     * Logs error responses from HTTP requests.
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
