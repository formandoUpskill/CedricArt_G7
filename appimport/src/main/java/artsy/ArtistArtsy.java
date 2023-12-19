package artsy;

import adapters.LocalDateAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Artist;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.ImportUtils;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Implementation of the IArtsy interface for Artist entities.
 * Handles the retrieval of artist data from the Artsy API.
 */
public class ArtistArtsy implements IArtsy<Artist> {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();

    /**
     * Retrieves all artists from the Artsy API.
     *
     * @param apiUrl    The API URL to fetch artists from.
     * @param xappToken The XAPP token for Artsy API authentication.
     * @param artistsList A list to store the retrieved artist data.
     * @return The next API URL for pagination, or an empty string if there are no more pages.
     * @throws ArtsyException If an error occurs during the API request.
     */
    public String getAll(String apiUrl, String xappToken, List<Artist> artistsList) throws ArtsyException{
        System.out.println(apiUrl);

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-XAPP-Token", xappToken)
                .build();


        int maxAttempts = ImportUtils.MAX_ATTEMPTS_API;
        int attempt = 0;
        boolean requestSuccessful = false;


        while(attempt < maxAttempts && !requestSuccessful)
        {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseBody = response.body().string();
                    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                    apiUrl = ImportUtils.getNextApiUrl(jsonObject);

                    JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("artists");
                    Type listType = new TypeToken<List<Artist>>() {}.getType();
                    List<Artist> artists = gson.fromJson(data, listType);

                    artists.forEach(artist -> {
                        cleanArtistData(artist);
                        artistsList.add(artist);
                    });

                    requestSuccessful = true; // Se a solicitação for bem-sucedida
                }//IF
                else{
                    if  (response.code() == 429)
                    {
                        // Esperar antes de tentar novamente
                        int waitTime = ImportUtils.calculateWaitTime(attempt);
                        Thread.sleep(waitTime);
                        attempt++;
                    }
                } // ELSE


            } // TRY
            catch (Exception e) {
                e.printStackTrace(); // Consider using a logging framework
                throw new ArtsyException(e.getMessage());
            }
        } //WHILE

        return apiUrl;

    }


    /**
     * Cleans and standardizes the data of an Artist object.
     *
     * @param artist The artist object whose data is to be cleaned.
     */

    private void cleanArtistData(Artist artist) {
        artist.setBiography(ImportUtils.cleanString(artist.getBiography()));
        artist.setBirthyear(ImportUtils.cleanString(artist.getBirthyear()));
        artist.setLocation(ImportUtils.cleanString(artist.getLocation()));
        artist.setHometown(ImportUtils.cleanString(artist.getHometown()));
        artist.setName(ImportUtils.cleanString(artist.getName()));
        artist.setSlug(ImportUtils.cleanString(artist.getSlug()));
        artist.setDeathyear(ImportUtils.cleanString(artist.getDeathyear()));
        artist.setThumbnail(ImportUtils.cleanString(artist.getThumbnailLinks()));
        artist.setUrl(ImportUtils.cleanString(artist.getUrl()));
        artist.setNationality(ImportUtils.cleanString(artist.getNationality()));

    }
}
