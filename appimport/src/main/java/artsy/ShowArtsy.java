package artsy;

import adapters.LocalDateAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Exhibition;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.ImportUtils;

import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * Class responsible for retrieving exhibition data from the Artsy API.
 * Implements the IArtsy interface for Exhibition objects.
 */
public class ShowArtsy implements IArtsy<Exhibition> {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter()).create();


    /**
     * Retrieves all exhibitions from the Artsy API.
     *
     * @param apiUrl The API URL to fetch exhibitions from.
     * @param xappToken The XAPP token for Artsy API authentication.
     * @param exhibitionList A list to store the retrieved exhibition data.
     * @return The next API URL for pagination, or an empty string if there are no more pages.
     * @throws ArtsyException If an error occurs during the API request.
     */
    public String getAll(String apiUrl, String xappToken, List<Exhibition> exhibitionList) throws ArtsyException{
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

                    JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("shows");
                    Type listType = new TypeToken<List<Exhibition>>() {}.getType();
                    List<Exhibition> exhibitions = gson.fromJson(data, listType);

                    exhibitions.forEach(exhibition -> {
                        cleanExhibitionData(exhibition);
                        exhibitionList.add(exhibition);
                    });

                    requestSuccessful = true; // Se a solicitação for bem-sucedida
                } // IF

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
     * Cleans and standardizes the data of an Exhibition object.
     *
     * @param exhibition The exhibition object whose data is to be cleaned.
     */
    private void cleanExhibitionData(Exhibition exhibition) {
        exhibition.setThumbnail(ImportUtils.cleanString(exhibition.getThumbnailLinks()));
        exhibition.setDescription(ImportUtils.cleanString(exhibition.getDescription()));
        exhibition.setName(ImportUtils.cleanString(exhibition.getName()));
        exhibition.setUrl(ImportUtils.cleanString(exhibition.getUrl()));
    }
}
