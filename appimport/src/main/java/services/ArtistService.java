package services;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artist;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArtistService {



    public void createArtist(String apiUrl, Artist artist){

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();

        String json = gson.toJson(artist);

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
                Artist created = gson.fromJson(response.body().string(), Artist.class);

            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
