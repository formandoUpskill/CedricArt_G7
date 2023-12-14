package util;

import domain.Artwork;
import domain.Exhibition;
import domain.Partner;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

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


    /**
     * 
     * @param elements
     * @param numElements
     * @return
     * @param <T>
     */
    public static <T> ArrayList<T> getRandomNumberOfElementsOfAList(List<T> elements, int numElements) {
        ArrayList<T> randomElements = new ArrayList<>();
        Random random = new Random();

        while (randomElements.size() < numElements) {
            int randomIndex = random.nextInt(elements.size());
            T randomElement = elements.get(randomIndex);

            if (!randomElements.contains(randomElement)) {
                randomElements.add(randomElement);
            }
        }

        return randomElements;
    }


}
