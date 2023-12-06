package presenter;

public class Main {


    public static void main(String[] args) {

        CedricArtPresenter presenter = new CedricArtPresenter();

        String artworkId= "4eb2eb13e742d70001007baf";
        String partnerId="5035a14a6cb80200020007e0";
        String exhibitionId="5d9643117c6d34001280d534";


      //   presenter.getArtwork(artworkId);
      //   presenter.getAllArtworks();

      //  presenter.getAllArtworksByPartner(partnerId);

     //  presenter.getAllArtworksByExhibition(exhibitionId);


     //  presenter.getAllPartners();
    //   presenter.getPartner(partnerId);


   //    presenter.getAllExhibitions();
    //    presenter.getExhibition(exhibitionId);
        presenter.getAllExhibitionsByPartner(partnerId);




    }



}
