package services;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Gene;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class GeneService {

    public void createGene(String apiUrl,Gene gene){

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();

        String json = gson.toJson(gene);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), json);

        Request postRequest = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();

        try {
            Response response = httpClient.newCall(postRequest).execute();
            System.out.println("Response code: " + response.code());
            System.out.println("Response content type: " + response.header("content-type"));

            if(response.code() == 200) {
                // Deserialize a client
                Gene created = gson.fromJson(response.body().string(), Gene.class);
                System.out.println(created);
            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
