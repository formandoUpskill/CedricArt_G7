import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.IOException;
import java.time.LocalDate;

public class MainPostClient {
    public static void main(String[] args) {
        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        Client newClient = new Client(0, "New Client", 30, "newClient@mail.gg", "265000000",
                new Address("street", "city", "code"), false);

        RequestBody requestJsonBody = RequestBody.create(
                gson.toJson(newClient),
                MediaType.parse("application/json")
        );

        Request postRequest = new Request.Builder()
                .url("http://localhost:4567/clients")
                .post(requestJsonBody)
                .build();

        try {
            Response response = httpClient.newCall(postRequest).execute();
            System.out.println("Response code: " + response.code());
            System.out.println("Response content type: " + response.header("content-type"));
            System.out.println("Response: " + response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
