package artsy;

import com.google.gson.JsonObject;
import domain.Artwork;

import java.util.List;

/**
 * Interface for Artsy operations on objects of type T.
 *
 * @param <T> the type of objects this interface handles.
 */
public interface IArtsy <T>{


    /**
     * Retrieves all objects of type T from the Artsy API.
     *
     * @param apiUrl    The URL of the Artsy API endpoint.
     * @param xappToken The XAPP token required for authentication to the Artsy API.
     * @param element   The list to which the retrieved objects will be added.
     * @return The next API URL to be called for pagination, or an empty string if there are no more pages.
     * @throws ArtsyException If an error occurs during the API request.
     */
    public String getAll(String apiUrl, String xappToken, List<T> element) throws ArtsyException;


}
