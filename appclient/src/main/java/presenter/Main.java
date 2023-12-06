package presenter;

public class Main {


    public static void main(String[] args) {

        CedricArtPresenter presenter = new CedricArtPresenter();

        //  String artworkId= "4eb2eb13e742d70001007baf";

        // presenter.getArtwork(artworkId);

        //  presenter.getAllArtworks();


        String partnerId="5035a14a6cb80200020007e0";

        presenter.getAllPartners();
        presenter.getPartner(partnerId);

    }



}
