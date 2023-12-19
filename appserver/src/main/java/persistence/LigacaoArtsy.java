package persistence;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Class responsible for managing the connection to the Artsy API.
 */
public class LigacaoArtsy {

    private OkHttpClient client;
    private String xappToken;


    public static  String CLIENT_ID;

    public static  String CLIENT_SECRET;

    public static  String XAPP_TOKEN;

   private static Properties config;

    /**
     * Constructor for LigacaoArtsy.
     * Initializes configurations from a properties file and generates an XAPP token.
     */
    public LigacaoArtsy() {
        try {
            this.config= new Properties();
            FileReader file = new FileReader("resources/config/CedricArt.config");
            config.load(file);
            CLIENT_ID = this.config.getProperty("CLIENT_ID");
            CLIENT_SECRET = this.config.getProperty("CLIENT_SECRET");
            XAPP_TOKEN  = this.config.getProperty("XAPP_TOKEN");

            file.close();
        } catch (IOException e) {
            System.out.println("APP Server Config file not found "+  e.getMessage());
        }

        //this.client = new OkHttpClient();
       this.xappToken = generateXappToken();
    }


    /**
     * Generates an XAPP token for Artsy API authentication.
     *
     * @return The generated XAPP token.
     */
    public static String generateXappToken() {

        OkHttpClient client = new OkHttpClient();

        String clientId = LigacaoArtsy.CLIENT_ID;
        String clientSecret = LigacaoArtsy.CLIENT_SECRET;
        String tokenUrl = LigacaoArtsy.XAPP_TOKEN;

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

}