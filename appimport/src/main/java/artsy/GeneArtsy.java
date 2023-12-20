package artsy;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import domain.Gene;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.ImportUtils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Implementation of the IArtsy interface for Gene entities.
 * Handles the retrieval of gene data from the Artsy API.
 */
public class GeneArtsy implements IArtsy<Gene> {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    /**
     * Retrieves all genes from the Artsy API.
     *
     * @param apiUrl    The API URL to fetch genes from.
     * @param xappToken The XAPP token for Artsy API authentication.
     * @param geneList  A list to store the retrieved gene data.
     * @return The next API URL for pagination, or an empty string if there are no more pages.
     * @throws ArtsyException If an error occurs during the API request.
     */
    public String getAll(String apiUrl, String xappToken, List<Gene> geneList) throws ArtsyException{

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
            try (Response response = client.newCall(request).execute())
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    String responseBody = response.body().string();
                    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();
                    apiUrl = ImportUtils.getNextApiUrl(jsonObject);

                    JsonArray data = jsonObject.getAsJsonObject("_embedded").getAsJsonArray("genes");
                    Type listType = new TypeToken<List<Gene>>() {
                    }.getType();
                    List<Gene> genes = gson.fromJson(data, listType);

                    genes.forEach(gene -> {
                        cleanGeneData(gene);
                        geneList.add(gene);
                    });

                    requestSuccessful = true; // Se a solicitação for bem-sucedida
                } //IF
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
     * Cleans and standardizes the data of a Gene object.
     *
     * @param gene The gene object whose data is to be cleaned.
     */
    private void cleanGeneData(Gene gene) {
        gene.setDescription(ImportUtils.cleanString(gene.getDescription()));
        gene.setName(ImportUtils.cleanString(gene.getName()));

    }




}
