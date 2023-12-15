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
import java.util.ArrayList;
import java.util.List;

public class ShowArtsy {


    public  String getAllShows(String apiUrl, String xappToken, List exhibitionList) {


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
                } catch (NullPointerException ex) {
                    apiUrl = "";
                }

                JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("shows");
                // Deserialize a list of exhibitions
                List<Exhibition> exhibitions = new ArrayList<>();
                Type listType = new TypeToken<ArrayList<Exhibition>>() {
                }.getType();
                exhibitions = gson.fromJson(data, listType);

                if (exhibitions != null) {

                    for (Exhibition exhibition : exhibitions) {


                        exhibition.setThumbnail(ImportUtils.cleanString(exhibition.getThumbnailLinks()));
                        exhibition.setDescription(ImportUtils.cleanString(exhibition.getDescription()));
                        exhibition.setName(ImportUtils.cleanString(exhibition.getName()));
                        exhibition.setUrl(ImportUtils.cleanString(exhibition.getUrl()));

                        exhibitionList.add(exhibition);

                    }
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
