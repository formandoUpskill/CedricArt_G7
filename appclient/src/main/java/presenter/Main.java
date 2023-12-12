package presenter;

import domain.Artwork;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        CedricArtPresenter presenter = new CedricArtPresenter();

        String artworkId= "4eb2eb13e742d70001007baf";
        String partnerId="5035a14a6cb80200020007e0";
        String exhibitionId="4ea1e81bb5482b000100437c";

        Artwork artwork;
        List<Artwork> artworks;

        artwork=  presenter.getArtwork(artworkId);
        System.out.println(artwork);

       // artworks= presenter.getAllArtworks();

       artworks= presenter.getAllArtworksByPartner(partnerId);

        for (Artwork aArtwork: artworks)
        {
            System.out.println(aArtwork);
        }

       // presenter.getExhibition(exhibitionId);
      //   presenter.getAllArtworksByExhibition(exhibitionId);


 //     presenter.getAllPartners();
     // presenter.getPartner(partnerId);


   // presenter.getAllExhibitions();

  // presenter.getAllExhibitionsByPartner(partnerId);




    }



}
