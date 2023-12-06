package presenter;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Artwork;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArtworkPresenter {

    /**
     *
     * @param artworkId
     * @return
     */
    public Artwork getArtwork(String apiUrl, String artworkId)
    {

        Artwork artwork= new Artwork();

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();


        Request getRequest = new Request.Builder()
                .url(apiUrl + artworkId)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();

            if(response.code() == 200) {
                // Create client object to represent the received data
                String data = response.body().string();

                artwork = gson.fromJson(data, Artwork.class);
                System.out.println(artwork);
                System.out.println("lista de genes + " + artwork.getGeneList().size());

            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return artwork;
    }


    /**
     *
     * @return
     */
    public List<Artwork> getAllArtworks (String apiUrl)
    {
        List<Artwork> all = new ArrayList<>();



        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();

        Request getRequest = new Request.Builder()
                .url(apiUrl)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();


            if(response.code() == 200) {
                // Deserialize a list of clients

                String data = response.body().string();

                Type listType = new TypeToken<ArrayList<Artwork>>(){}.getType();
                all = gson.fromJson(data, listType);
                for (Artwork artwork : all) {
                    System.out.println(artwork);
                }
            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return all;
    }



    public List<Artwork> getAllArtworksByPartner (String apiUrl)
    {
        List<Artwork> all = new ArrayList<>();

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();

        Request getRequest = new Request.Builder()
                .url(apiUrl)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();

            if(response.code() == 200) {
                // Deserialize a list of clients

                String data = response.body().string();

                Type listType = new TypeToken<ArrayList<Artwork>>(){}.getType();
                all = gson.fromJson(data, listType);
                for (Artwork artwork : all) {
                    System.out.println(artwork);
                }
            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return all;
    }


    public List<Artwork> getAllArtworksByExhibition (String apiUrl)
    {
        List<Artwork> all = new ArrayList<>();

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();

        Request getRequest = new Request.Builder()
                .url(apiUrl)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();

            if(response.code() == 200) {
                // Deserialize a list of clients

                String data = response.body().string();

                Type listType = new TypeToken<ArrayList<Artwork>>(){}.getType();
                all = gson.fromJson(data, listType);
                for (Artwork artwork : all) {
                    System.out.println(artwork);
                }
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
