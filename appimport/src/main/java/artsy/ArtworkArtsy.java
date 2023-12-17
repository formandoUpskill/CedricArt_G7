package artsy;

import adapters.LocalDateAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Artwork;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.ImportUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.List;

public class ArtworkArtsy implements IArtsy<Artwork> {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
            .create();

    public String getPartnerLinks(String apiUrl, String xappToken) {
        System.out.println(apiUrl);

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-XAPP-Token", xappToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                Artwork artwork = gson.fromJson(responseBody, Artwork.class);
                return artwork.getPartnerLink();
            } else {
                System.out.println("Falha na solicitação à API. Código de resposta: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework
        }
        return null;
    }

    public String getAll(String apiUrl, String xappToken, List<Artwork> artworkList) {
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

                JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("artworks");
                Type listType = new TypeToken<List<Artwork>>() {}.getType();
                List<Artwork> artworks = gson.fromJson(data, listType);

                artworks.forEach(artwork -> {
                    cleanArtworkData(artwork);
                    artworkList.add(artwork);
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

    private void cleanArtworkData(Artwork artwork) {

        artwork.setTitle(ImportUtils.cleanString(artwork.getTitle()));
        artwork.setThumbnail(ImportUtils.cleanString(artwork.getThumbnailLinks()));
        artwork.setUrl(ImportUtils.cleanString(artwork.getUrl()));
        artwork.setDate(ImportUtils.cleanString(artwork.getDate()));


    }
}
