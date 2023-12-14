package presenter;

import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import util.AppUtils;

import java.util.List;

public class CedricArtPresenter {

    private ArtworkPresenter artworkPresenter;
    private PartnerPresenter partnerPresenter;

    private ExhibitionPresenter exhibitionPresenter;

    private ArtistPresenter artistPresenter;
    /**
     *
     */
    public CedricArtPresenter(){

        loadConfig();

        artworkPresenter= new ArtworkPresenter();
        partnerPresenter = new PartnerPresenter();
        exhibitionPresenter =new ExhibitionPresenter();
        artistPresenter = new ArtistPresenter();

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
     * @param artistId
     * @return
     */
    public Artist getArtist(String artistId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists/";
        Artist artist = this.artistPresenter.getArtist(apiUrl,artistId);
        return artist;

    }

    /**
     *
     * @return
     */
    public List<Artist> getAllArtists ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists";

        return this.artistPresenter.getArtists(apiUrl);
    }



    public List<Artist> getAllArtistsByPartner (String partner_id)
    {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists?partner_id=" + partner_id;

        return this.artistPresenter.getArtists(apiUrl);
    }


    public List<Artist> getAllArtistsByExhibition (String exhibition_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists?show_id=" + exhibition_id;

        return this.artistPresenter.getArtists(apiUrl);
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

       return this.artworkPresenter.getArtworks(apiUrl);
    }



    public List<Artwork> getAllArtworksByPartner (String partner_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks?partner_id=" + partner_id;

        return this.artworkPresenter.getArtworks(apiUrl);
    }


    public List<Artwork> getAllArtworksByExhibition (String exhibition_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks?show_id=" + exhibition_id;

        return this.artworkPresenter.getArtworks(apiUrl);
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




    /**
     *
     * @param exhibitionId
     * @return
     */
    public Exhibition getExhibition(String exhibitionId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows/";
        Exhibition partner = this.exhibitionPresenter.getExhibition(apiUrl,exhibitionId);
        return partner;

    }

    /**
     *
     * @return
     */
    public List<Exhibition> getAllExhibitions ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows";

        return this.exhibitionPresenter.getAllExhibitions(apiUrl);
    }


    public List<Exhibition> getAllExhibitionsByPartner (String partner_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows?partner_id=" + partner_id;

        return this.exhibitionPresenter.getAllExhibitions(apiUrl);
    }




    public ArtworkPresenter getArtworkPresenter() {
        return this.artworkPresenter;
    }

    public PartnerPresenter getPartnerPresenter() {
        return partnerPresenter;
    }

    public ExhibitionPresenter getExhibitionPresenter() {
        return exhibitionPresenter;
    }

    public static void main(String[] args) {



    }
}
