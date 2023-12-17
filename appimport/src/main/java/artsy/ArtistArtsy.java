package artsy;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Artist;
import adapters.LocalDateAdapter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.ImportUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.List;

public class ArtistArtsy implements IArtsy<Artist> {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();

    public String getAll(String apiUrl, String xappToken, List<Artist> artistsList) {
        System.out.println(apiUrl);

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-XAPP-Token", xappToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                apiUrl = getNextApiUrl(jsonObject);

                JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("artists");
                Type listType = new TypeToken<List<Artist>>() {}.getType();
                List<Artist> artists = gson.fromJson(data, listType);

                artists.forEach(artist -> {
                    cleanArtistData(artist);
                    artistsList.add(artist);
                });

            } else {
                System.out.println("Falha na solicitação à API. Código de resposta: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework
        }
        return apiUrl;
    }

    private String getNextApiUrl(JsonObject jsonObject) {
        try {
            return jsonObject.getAsJsonObject("_links").getAsJsonObject("next").get("href").getAsString();
        } catch (NullPointerException ex) {
            return "";
        }
    }

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
