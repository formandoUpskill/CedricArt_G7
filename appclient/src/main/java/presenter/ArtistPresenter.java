package presenter;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Artist;
import domain.Artwork;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArtistPresenter {


    public Artist getArtist(String apiUrl, String artistId)
    {

        Artist artist= new Artist();

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
                .create();


        Request getRequest = new Request.Builder()
                .url(apiUrl + artistId)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();

            if(response.code() == 200) {
                // Create client object to represent the received data
                String data = response.body().string();

                artist = gson.fromJson(data, Artist.class);

            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return artist;
    }


    /**
     *
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
