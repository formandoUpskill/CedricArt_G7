package services;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Artist;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArtistService {


    /**
     *
     * @param apiUrl
     * @param artist
     */
    public void createArtist(String apiUrl, Artist artist){

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
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


    /**
     *
     * @param apiUrl
     * @return
     */
    public List<Artist> getAllArtists (String apiUrl)
    {
        List<Artist> all = new ArrayList<>();



        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
                .create();

        Request getRequest = new Request.Builder()
                .url(apiUrl)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();


            if(response.code() == 200) {
                // Deserialize a list of clients

                String data = response.body().string();

                Type listType = new TypeToken<ArrayList<Artist>>(){}.getType();
                all = gson.fromJson(data, listType);

            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return all;
    }

}
