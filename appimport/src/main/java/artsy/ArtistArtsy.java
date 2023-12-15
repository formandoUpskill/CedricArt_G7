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
import java.util.ArrayList;
import java.util.List;

public class ArtistArtsy implements IArtsy<Artist>{


    public  String getAll(String apiUrl, String xappToken, List<Artist> artistsList) {



        Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter()).create();

        System.out.println(apiUrl);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-XAPP-Token", xappToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Processar a resposta aqui conforme necessário
                String responseBody = response.body().string();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject) parser.parse(responseBody);


                try {
                    apiUrl = jsonObject.getAsJsonObject("_links").
                            getAsJsonObject("next").
                            get("href").getAsString();

                } catch (NullPointerException ex) {
                    apiUrl = "";
                    return apiUrl;
                }

                JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("artists");


                List<Artist> artists = new ArrayList<>();
                Type listType = new TypeToken<ArrayList<Artist>>() {
                }.getType();
                artists = gson.fromJson(data, listType);


                for (Artist artist : artists) {

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

                    artistsList.add(artist);

                }


            } else {
                System.out.println("Falha na solicitação à API. Código de resposta: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return apiUrl;
    }
}
