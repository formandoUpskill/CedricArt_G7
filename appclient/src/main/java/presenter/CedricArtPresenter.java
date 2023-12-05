package presenter;

import domain.Artwork;
import domain.Partner;
import util.AppUtils;

import java.util.List;

public class CedricArtPresenter {

    private ArtworkPresenter artworkPresenter;
    private PartnerPresenter partnerPresenter;

    /**
     *
     */
    public CedricArtPresenter(){

        loadConfig();

        artworkPresenter= new ArtworkPresenter();
        partnerPresenter = new PartnerPresenter();

    }

    /**
     *
     */
    private void loadConfig()
    {
        new AppUtils();
    }

    /**
     *
     * @param artworkId
     */
    public Artwork getArtwork(String artworkId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks/";
        Artwork artwork = this.artworkPresenter.getArtwork(apiUrl,artworkId);
        return artwork;

    }

    /**
     *
     * @return
     */
    public List<Artwork> getAllArtworks ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks";

       return this.artworkPresenter.getAllArtworks(apiUrl);
    }


    /**
     *
     * @param partnerId
     * @return
     */
    public Partner getPartner(String partnerId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/partners/";
        Partner partner = this.partnerPresenter.getPartner(apiUrl,partnerId);
        return partner;

    }

    /**
     *
     * @return
     */
    public List<Partner> getAllPartners ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/partners";

        return this.partnerPresenter.getAllPartners(apiUrl);
    }


    public ArtworkPresenter getArtworkPresenter() {
        return this.artworkPresenter;
    }

    public PartnerPresenter getPartnerPresenter() {
        return partnerPresenter;
    }

    public static void main(String[] args) {



    }
}
