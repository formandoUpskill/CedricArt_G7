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
        String partnerId="51cc9a88275b24f8700000db";
        String exhibitionId="57ec2f87275b2403270003bf";
        String artistId= "4d8b92684eb68a1b2c00009e";

        Artwork artwork;
        List<Artwork> artworks;

        Partner partner;
        List<Partner> partners;

        Artist artist;
        List<Artist> artists;


        Exhibition exhibition;
        List<Exhibition> exhibitions;

        System.out.println(" APP CLIENT:"+ System.getProperty("user.dir"));


     artwork=  presenter.getArtwork(artworkId);
     System.out.println(artwork);

     /*

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





      artists= presenter.getAllArtists();

 */
/*
          artists=  presenter.getAllArtistsByPartner(partnerId);


        for (Artist aArtist: artists)
        {
            System.out.println(aArtist);
        }

         System.out.println(artists.size());



        artist= presenter.getArtist(artistId);

        System.out.println(artist);


*/





        //  server.stop();
    }

}
