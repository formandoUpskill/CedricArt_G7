package presenter;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        CedricArtPresenter presenter = new CedricArtPresenter();

     //  RunServer server=  new RunServer();
      // server.run();

        String artworkId= "4eb2eb13e742d70001007baf";
        String partnerId="5035a14a6cb80200020007e0";
        String exhibitionId="57ec2f87275b2403270003bf";
        String artistId= "4d8b925d4eb68a1b2c000012";

        Artwork artwork;
        List<Artwork> artworks;

        Partner partner;
        List<Partner> partners;

        Artist artist;
        List<Artist> artists;


        Exhibition exhibition;
        List<Exhibition> exhibitions;


     artwork=  presenter.getArtwork(artworkId);
     System.out.println(artwork);

     artworks= presenter.getAllArtworks();

   artworks= presenter.getAllArtworksByPartner(partnerId);


        for (Artwork aArtwork: artworks)
        {
            System.out.println(aArtwork);
        }

        System.out.println(artworks.size());


        System.out.println(presenter.getExhibition(exhibitionId));

       artworks= presenter.getAllArtworksByExhibition(exhibitionId);
        for (Artwork aArtwork: artworks)
        {
            System.out.println(aArtwork);
        }
        System.out.println(artworks.size());



       partner= presenter.getPartner(partnerId);
       System.out.println(partner);

    partners= presenter.getAllPartners();
        for (Partner aPartner: partners)
        {
            System.out.println(aPartner);
        }

        System.out.println(partners.size());


/*
    List<Partner> randomPartners= getRandomPartners(partners,10);

    for (Partner aPartner: randomPartners)
    {
        System.out.println(aPartner);
    }



*/




      exhibitions= presenter.getAllExhibitions();


       exhibitions= presenter.getAllExhibitionsByPartner(partnerId);


        for (Exhibition aExhibition: exhibitions)
        {
            System.out.println(aExhibition);

        }
        System.out.println(exhibitions.size());



   artworks=  presenter.getAllArtworksByArtist(artistId);


        for (Artwork aArtwork: artworks)
        {
            System.out.println(aArtwork);
        }

        System.out.println(artworks.size());




       artist= presenter.getArtist(artistId);
       System.out.println(artist);

      artists= presenter.getAllArtists();

          artists=  presenter.getAllArtistsByPartner(partnerId);


        for (Artist aArtist: artists)
        {
            System.out.println(aArtist);
        }

         System.out.println(artists.size());










      //  server.stop();
    }

}
