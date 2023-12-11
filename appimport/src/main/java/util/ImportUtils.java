package util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ImportUtils {

    private OkHttpClient client;
    private String xappToken;


    public static  String CLIENT_ID;

    public static  String CLIENT_SECRET;

    public static  String XAPP_TOKEN;

    private static Properties config;

    public static String CEDRIC_ART_API_HOST;

    public static boolean IS_FAST_ARTSY_LOAD;



    public ImportUtils() {
        try {
            this.config= new Properties();
            FileReader file = new FileReader("resources/config/CedricArt.config");
            config.load(file);
            CLIENT_ID = this.config.getProperty("CLIENT_ID");
            CLIENT_SECRET = this.config.getProperty("CLIENT_SECRET");
            XAPP_TOKEN  = this.config.getProperty("XAPP_TOKEN");
            CEDRIC_ART_API_HOST = this.config.getProperty("CEDRIC_ART_API_HOST");
            IS_FAST_ARTSY_LOAD=  Boolean.parseBoolean(this.config.getProperty("IS_FAST_ARTSY_LOAD"));


            file.close();
        } catch (IOException e) {
            System.out.println("Config file not found "+  e.getMessage());
        }

        //this.client = new OkHttpClient();
        this.xappToken = generateXappToken();
    }






    public static String generateXappToken() {

        OkHttpClient client = new OkHttpClient();

        String clientId = ImportUtils.CLIENT_ID;
        String clientSecret = ImportUtils.CLIENT_SECRET;
        String tokenUrl = ImportUtils.XAPP_TOKEN;

        RequestBody formBody = new FormBody.Builder()
                .add("client_id", clientId)
                .add("client_secret", clientSecret)
                .build();

        Request request = new Request.Builder()
                .url(tokenUrl)
                .post(formBody)
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JsonObject jsonResponse = new Gson().fromJson(response.body().string(), JsonObject.class);
                return jsonResponse.get("token").getAsString();
            } else {
                throw new IOException("Failed to generate Xapp token. Code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String cleanString(String original) {
        if (original != null) {
            String replaced = original.replace("’", "\\’")
                    .replace("\"", "\\\"")
                    .replace("'", "\\'");

            return replaced;
        }
        return null;
    }


}
