package util;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
/**
 * Utility class for the application, providing various utility methods and handling application configuration.
 */
public class AppUtils {

    public static String CEDRIC_ART_API_HOST;

    Properties config;

    /**
     * Constructor for AppUtils. Loads configuration properties from a file.
     */
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
     * Selects a random subset of elements from a given list.
     * @param elements The list of elements to select from.
     * @param numElements The number of random elements to select.
     * @param <T> The type of elements in the list.
     * @return A list containing a random selection of elements.
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
