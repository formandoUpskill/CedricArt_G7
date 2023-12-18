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

import java.time.OffsetDateTime;

public class PartnerArtsy {

    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(OffsetDateTime.class, new LocalDateAdapter()).create();

    public Partner getPartner(String apiUrl, String xappToken, int id_gallerist, int id_Coordinator) throws ArtsyException {
        Partner partner = new Partner();

        Request request = new Request.Builder()
                .url(apiUrl)
                .header("X-XAPP-Token", xappToken)
                .build();


        int maxAttempts = ImportUtils.MAX_ATTEMPTS_API;
        int attempt = 0;
        boolean requestSuccessful = false;


        while(attempt < maxAttempts && !requestSuccessful)
        {

            try (Response response = client.newCall(request).execute()) {


                if (response.isSuccessful() && response.body() != null) {

                    String responseBody = response.body().string();

                    JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

                    partner = gson.fromJson(jsonObject, Partner.class);

                    cleanPartnerData(partner, id_gallerist, id_Coordinator);

                    requestSuccessful = true; // Se a solicitação for bem-sucedida

                } //IF
                else{
                    if  (response.code() == 429)
                    {
                        // Esperar antes de tentar novamente
                        int waitTime = ImportUtils.calculateWaitTime(attempt);
                        Thread.sleep(waitTime);
                        attempt++;

                    }
                } // ELSE


            } // TRY
            catch (Exception e) {
                e.printStackTrace(); // Consider using a logging framework
                throw new ArtsyException(e.getMessage());
            }
        } //WHILE


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
