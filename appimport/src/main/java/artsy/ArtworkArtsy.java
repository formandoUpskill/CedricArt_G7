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
import java.util.ArrayList;
import java.util.List;

public class ArtworkArtsy {



    public String getPartnerLinks(String apiUrl,String xappToken)
    {

        Artwork artwork = new Artwork();

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
                JsonObject jsonObject = (JsonObject)parser.parse(responseBody);

                String jsonString = jsonObject.toString();

                artwork = gson.fromJson(jsonString, Artwork.class);
                return artwork.getPartnerLink();

            } else {
                System.out.println("Falha na solicitação à API. Código de resposta: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    public  String getAllArtworks(String apiUrl, String xappToken, List<Artwork> artworkList)
    {

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
                    apiUrl = jsonObject.getAsJsonObject("_links")
                            .getAsJsonObject("next")
                            .get("href").getAsString();
                }
                catch(NullPointerException ex)
                {
                    apiUrl="";
                }

                JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("artworks");


                // Deserialize a list of genes
                List<Artwork>  artworks = new ArrayList<>();
                Type listType = new TypeToken<ArrayList<Artwork>>(){}.getType();
                artworks = gson.fromJson(data, listType);


                for (Artwork artwork : artworks) {

                    artwork.setTitle(ImportUtils.cleanString(artwork.getTitle()));
                    artwork.setThumbnail(ImportUtils.cleanString(artwork.getThumbnailLinks()));
                    artwork.setUrl(ImportUtils.cleanString(artwork.getUrl()));
                    artwork.setDate(ImportUtils.cleanString(artwork.getDate()));

                    artworkList.add(artwork);
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
