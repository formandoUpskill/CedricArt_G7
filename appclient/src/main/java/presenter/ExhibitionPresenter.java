package presenter;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import domain.Exhibition;
import domain.Partner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExhibitionPresenter {


    public List<Exhibition> getAllExhibitions (String apiUrl)
    {
        List<Exhibition> all = new ArrayList<>();

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

                Type listType = new TypeToken<ArrayList<Exhibition>>(){}.getType();
                all = gson.fromJson(data, listType);
                for (Exhibition exhibition : all) {
                    System.out.println(exhibition);
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



    public Exhibition getExhibition(String apiUrl, String exhibitionId)
    {

        Exhibition exhibition= new Exhibition();

        OkHttpClient httpClient = new OkHttpClient();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();


        Request getRequest = new Request.Builder()
                .url(apiUrl + exhibitionId)
                .build();

        try {
            Response response = httpClient.newCall(getRequest).execute();

            if(response.code() == 200) {
                // Create client object to represent the received data
                String data = response.body().string();

                exhibition = gson.fromJson(data, Exhibition.class);
                System.out.println(exhibition);

            } else {
                // Something failed, maybe client does not exist
                System.out.println(response.body().string());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return exhibition;
    }
}
