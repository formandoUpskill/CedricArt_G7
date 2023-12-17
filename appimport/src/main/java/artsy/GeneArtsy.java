package artsy;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Gene;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.ImportUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class GeneArtsy implements IArtsy<Gene> {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public String getAll(String apiUrl, String xappToken, List<Gene> geneList) {
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

                JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("genes");
                Type listType = new TypeToken<List<Gene>>() {}.getType();
                List<Gene> genes = gson.fromJson(data, listType);

                genes.forEach(gene -> {
                    cleanGeneData(gene);
                    geneList.add(gene);
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

    private void cleanGeneData(Gene gene) {
        gene.setDescription(ImportUtils.cleanString(gene.getDescription()));
        gene.setName(ImportUtils.cleanString(gene.getName()));

    }
}
