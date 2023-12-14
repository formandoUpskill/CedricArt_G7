package presenter;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import util.AppUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {


    public static void main(String[] args) {

        CedricArtPresenter presenter = new CedricArtPresenter();


        String artworkId= "4eb2eb13e742d70001007baf";
        String partnerId="5035a14a6cb80200020007e0";
        String exhibitionId="4ea1e81bb5482b000100437c";

        Artwork artwork;
        List<Artwork> artworks;

        Partner partner;
        List<Partner> partners;

        Artist artist;
        List<Artist> artists;


        Exhibition exhibition;
        List<Exhibition> exhibitions;


      //  artwork=  presenter.getArtwork(artworkId);
       // System.out.println(artwork);

       // artworks= presenter.getAllArtworks();

//       artworks= presenter.getAllArtworksByPartner(partnerId);
/*
        for (Artwork aArtwork: artworks)
        {
            System.out.println(aArtwork);
        }
*/


      //  System.out.println(presenter.getExhibition(exhibitionId));
      //   presenter.getAllArtworksByExhibition(exhibitionId);


   //  partners= presenter.getAllPartners();
     //   System.out.println( partners.size());

/*
    List<Partner> randomPartners= getRandomPartners(partners,10);

    for (Partner aPartner: randomPartners)
    {
        System.out.println(aPartner);
    }

*/

/*
        for (Partner aPartner: partners)
        {
            System.out.println(aPartner);
        }

        */

     //   System.out.println((presenter.getPartner(partnerId)));



       // exhibitions= presenter.getAllExhibitions();



        exhibitions= presenter.getAllExhibitionsByPartner(partnerId);

        exhibitions= AppUtils.getRandomNumberOfElementsOfAList(exhibitions, 5);

        for (Exhibition aExhibition: exhibitions)
        {
            System.out.println(aExhibition);
        }
/*
      artists=  presenter.getAllArtistsByPartner(partnerId);

        for (Artist aArtist: artists)
        {
            System.out.println(aArtist);
        }
*/
    }


    /**
     * Select random numPartners from all partner list
     */


}
