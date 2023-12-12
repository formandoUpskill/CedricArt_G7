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
import java.util.ArrayList;
import java.util.List;

public class GeneArtsy {

    public String getAllGenes(String apiUrl, String xappToken, List<Gene> geneList) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
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

                JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("genes");

                // Deserialize a list of genes
                List<Gene> genes = new ArrayList<>();
                Type listType = new TypeToken<ArrayList<Gene>>() {
                }.getType();
                genes = gson.fromJson(data, listType);


                for (Gene gene : genes) {

                    gene.setDescription(ImportUtils.cleanString(gene.getDescription()));
                    gene.setName(ImportUtils.cleanString(gene.getName()));

                    geneList.add(gene);

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
