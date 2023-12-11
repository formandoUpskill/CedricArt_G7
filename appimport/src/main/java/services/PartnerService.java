package services;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artwork;
import domain.Partner;
import okhttp3.*;

import java.io.IOException;
import java.time.OffsetDateTime;

public class PartnerService {

    public void createPartner(String apiUrl, Partner partner){

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
                .create();

        String json = gson.toJson(partner);

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), json);

        Request postRequest = new Request.Builder()
                .url(apiUrl)
                .post(body)
                .build();

        try {
            Response response = httpClient.newCall(postRequest).execute();


            if(response.code() == 200) {
                // Deserialize a client
                Partner created = gson.fromJson(response.body().string(), Partner.class);

            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
