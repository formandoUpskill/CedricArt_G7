import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Artwork;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class MainGetArtwork {
    public static void main(String[] args) {
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Scanner keyboard = new Scanner(System.in);
        System.out.print("ArtworkId to retrieve (integer)? ");
        String id = keyboard.nextLine();

        Request getRequest = new Request.Builder()
                .url("http://localhost:4567/artworks/" + id)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code());
            System.out.println("Response content type: " + response.header("content-type"));

            if(response.code() == 200) {
                // Create client object to represent the received data
                Artwork c = gson.fromJson(response.body().string(), Artwork.class);
                System.out.println(c);
            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
