import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import domain.Client;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Scanner;

public class MainDeleteClient {
    public static void main(String[] args) {
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Scanner keyboard = new Scanner(System.in);
        System.out.print("ClientId to delete (integer)? ");
        int id = keyboard.nextInt();

        Request deleteRequest = new Request.Builder()
                .url("http://localhost:4567/clients/" + id)
                .delete()
                .build();

        try {
            Response response = httpClient.newCall(deleteRequest).execute();
            System.out.println("Response code: " + response.code());
            System.out.println("Response content type: " + response.header("content-type"));

            if(response.code() == 200) {
                // Create client object to represent the received data
                Client c = gson.fromJson(response.body().string(), Client.class);
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
