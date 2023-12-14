package util;

import domain.Artwork;
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
     * @param partners
     * @param numPartners
     * @return
     */
    public static ArrayList<Partner> getRandomPartners(List<Partner> partners, int numPartners) {
        ArrayList<Partner> randomPartners = new ArrayList<>();
        Random random = new Random();

        while (randomPartners.size() < numPartners) {
            int randomIndex = random.nextInt(partners.size());
            Partner randomPartner = partners.get(randomIndex);

            if (!randomPartners.contains(randomPartner)) {
                randomPartners.add(randomPartner);
            }
        }

        return randomPartners;
    }

    public static ArrayList<Artwork> getRandomArtworks(List<Artwork> artworks, int numArtworks) {
        ArrayList<Artwork> randomArtwoks = new ArrayList<>();
        Random random = new Random();

        while (randomArtwoks.size() < numArtworks) {
            int randomIndex = random.nextInt(artworks.size());
            Artwork randomArtwork = artworks.get(randomIndex);

            if (!randomArtwoks.contains(randomArtwork)) {
                randomArtwoks.add(randomArtwork);
            }
        }

        return randomArtwoks;
    }

}
