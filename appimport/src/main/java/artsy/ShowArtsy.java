package artsy;

import adapters.LocalDateAdapter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Exhibition;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.ImportUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.List;

public class ShowArtsy implements IArtsy<Exhibition> {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter()).create();

    public String getAll(String apiUrl, String xappToken, List<Exhibition> exhibitionList) {
        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-XAPP-Token", xappToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                apiUrl = getNextApiUrl(jsonObject);

                JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("shows");
                Type listType = new TypeToken<List<Exhibition>>() {}.getType();
                List<Exhibition> exhibitions = gson.fromJson(data, listType);

                exhibitions.forEach(exhibition -> {
                    cleanExhibitionData(exhibition);
                    exhibitionList.add(exhibition);
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

    private void cleanExhibitionData(Exhibition exhibition) {
        exhibition.setThumbnail(ImportUtils.cleanString(exhibition.getThumbnailLinks()));
        exhibition.setDescription(ImportUtils.cleanString(exhibition.getDescription()));
        exhibition.setName(ImportUtils.cleanString(exhibition.getName()));
        exhibition.setUrl(ImportUtils.cleanString(exhibition.getUrl()));
    }
}
