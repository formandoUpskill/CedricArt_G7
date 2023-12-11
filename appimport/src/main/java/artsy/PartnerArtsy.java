package artsy;

import adapters.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import domain.Partner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import util.ImportUtils;

import java.io.IOException;
import java.time.OffsetDateTime;

public class PartnerArtsy {


    public Partner getPartner(String apiUrl, String xappToken, int id_gallerist, int id_Coordinator) {

        Partner partner= new Partner();

        OkHttpClient client = new OkHttpClient();

        Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter()).create();


        System.out.println(apiUrl);

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-XAPP-Token", xappToken)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                // Processar a resposta aqui conforme necessário
                String responseBody = response.body().string();
                JsonParser parser = new JsonParser();
                JsonObject jsonObject = (JsonObject)parser.parse(responseBody);

                String jsonString = jsonObject.toString();

                partner = gson.fromJson(jsonString, Partner.class);

                partner.setRegion(ImportUtils.cleanString(partner.getRegion()));
                partner.setName(ImportUtils.cleanString(partner.getName()));
                partner.setWebsite(ImportUtils.cleanString(partner.getWebsiteLink()));
                partner.setEmail(ImportUtils.cleanString(partner.getEmail()));
                partner.setId_gallerist(id_gallerist);
                partner.setId_coordinator(id_Coordinator);

            } else {
                System.out.println("Falha na solicitação à API. Código de resposta: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("PartnerArtsyPartnerArtsyPartnerArtsy getPartner" + partner);

        return partner;
    }

}
