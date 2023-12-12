package presenter;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Partner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class PartnerPresenter {


    public List<Partner> getAllPartners (String apiUrl)
    {
        List<Partner> all = new ArrayList<>();

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
                .create();

        Request getRequest = new Request.Builder()
                .url(apiUrl)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code());
            System.out.println("Response content type: " + response.header("content-type"));

            if(response.code() == 200) {
                // Deserialize a list of clients

                String data = response.body().string();

                Type listType = new TypeToken<ArrayList<Partner>>(){}.getType();
                all = gson.fromJson(data, listType);
                for (Partner partner : all) {
                    System.out.println(partner);
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



    public Partner getPartner(String apiUrl, String partnerId)
    {

        Partner partner= new Partner();

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter())
                .create();


        Request getRequest = new Request.Builder()
                .url(apiUrl + partnerId)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();
            System.out.println("Response code: " + response.code());
            System.out.println("Response content type: " + response.header("content-type"));

            if(response.code() == 200) {
                // Create client object to represent the received data
                String data = response.body().string();

                partner = gson.fromJson(data, Partner.class);
                System.out.println(partner);

            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return partner;
    }
}
