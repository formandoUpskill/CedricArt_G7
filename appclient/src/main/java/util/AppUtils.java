package util;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppUtils {

    public static String CEDRIC_ART_API_HOST;

    Properties config;


    public AppUtils(){

        try {
            this.config= new Properties();
            FileReader file = new FileReader("resources/config/CedricArt.config");
            config.load(file);
            CEDRIC_ART_API_HOST = this.config.getProperty("CEDRIC_ART_API_HOST");
            file.close();
        } catch (IOException e) {
            System.out.println("Config file not found "+  e.getMessage());
        }

        System.out.println("CEDRIC_ART_API_HOST " + CEDRIC_ART_API_HOST);
    }
}
