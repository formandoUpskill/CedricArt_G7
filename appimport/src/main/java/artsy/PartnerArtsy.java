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

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter()).create();

    public Partner getPartner(String apiUrl, String xappToken, int id_gallerist, int id_Coordinator) {
        Partner partner = new Partner();

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-XAPP-Token", xappToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                partner = gson.fromJson(jsonObject, Partner.class);

                cleanPartnerData(partner, id_gallerist, id_Coordinator);

            } else {
                System.out.println("Falha na solicitação à API. Código de resposta: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework
        }

        return partner;
    }

    private void cleanPartnerData(Partner partner, int id_gallerist, int id_Coordinator) {
        partner.setRegion(ImportUtils.cleanString(partner.getRegion()));
        partner.setName(ImportUtils.cleanString(partner.getName()));
        partner.setWebsite(ImportUtils.cleanString(partner.getWebsiteLink()));
        partner.setEmail(ImportUtils.cleanString(partner.getEmail()));
        partner.setId_gallerist(id_gallerist);
        partner.setId_coordinator(id_Coordinator);
    }
}
