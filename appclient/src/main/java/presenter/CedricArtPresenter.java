package presenter;

import com.google.gson.reflect.TypeToken;
import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import util.AppUtils;

import java.util.List;

public class CedricArtPresenter {

   // private ArtworkPresenter artworkPresenter;


    private GenericPresenter<Artwork> artworkPresenter;
    private GenericPresenter<Partner> partnerPresenter;

    private GenericPresenter<Exhibition>  exhibitionPresenter;

    private GenericPresenter<Artist> artistPresenter;
    /**
     *
     */
    public CedricArtPresenter(){

        loadConfig();


        partnerPresenter = new GenericPresenter<>();
        exhibitionPresenter = new GenericPresenter<>();
        artworkPresenter = new GenericPresenter<>();
        artistPresenter= new GenericPresenter<>();

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

        return  artistPresenter.get(apiUrl,artistId, Artist.class);

    }

    /**
     *
     * @return
     */
    public List<Artist> getAllArtists ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists";
        return artistPresenter.getAll(apiUrl, new TypeToken<List<Artist>>(){});
    }


    /**
     *
     * @param partner_id
     * @return
     */
    public List<Artist> getAllArtistsByPartner (String partner_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists?partner_id=" + partner_id;
        return artistPresenter.getAll(apiUrl, new TypeToken<List<Artist>>(){});
    }


    public List<Artist> getAllArtistsByExhibition (String exhibition_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists?show_id=" + exhibition_id;

        return artistPresenter.getAll(apiUrl, new TypeToken<List<Artist>>(){});
    }






    /**
     *
     * @param artworkId
     */
    public Artwork getArtwork(String artworkId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks/";
        return  artworkPresenter.get(apiUrl,artworkId, Artwork.class);

    }

    /**
     *
     * @return
     */
    public List<Artwork> getAllArtworks ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks";
        return artworkPresenter.getAll(apiUrl, new TypeToken<List<Artwork>>(){});

    }



    public List<Artwork> getAllArtworksByPartner (String partner_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks?partner_id=" + partner_id;

        return artworkPresenter.getAll(apiUrl, new TypeToken<List<Artwork>>(){});

    }


    public List<Artwork> getAllArtworksByArtist (String artist_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks?artist_id=" + artist_id;

        return artworkPresenter.getAll(apiUrl, new TypeToken<List<Artwork>>(){});


    }



    public List<Artwork> getAllArtworksByExhibition (String exhibition_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks?show_id=" + exhibition_id;
        return artworkPresenter.getAll(apiUrl, new TypeToken<List<Artwork>>(){});

    }






    /**
     *
     * @param partnerId
     * @return
     */
    public Partner getPartner(String partnerId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/partners/";

        return  partnerPresenter.get(apiUrl,partnerId, Partner.class);

    }

    /**
     *
     * @return
     */
    public List<Partner> getAllPartners ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/partners";
        return partnerPresenter.getAll(apiUrl, new TypeToken<List<Partner>>(){});
    }




    /**
     *
     * @param exhibitionId
     * @return
     */
    public Exhibition getExhibition(String exhibitionId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows/";

        return  exhibitionPresenter.get(apiUrl,exhibitionId, Exhibition.class);

    }

    /**
     *
     * @return
     */
    public List<Exhibition> getAllExhibitions ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows";
        return exhibitionPresenter.getAll(apiUrl, new TypeToken<List<Exhibition>>(){});
    }


    public List<Exhibition> getAllExhibitionsByPartner (String partner_id)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows?partner_id=" + partner_id;
        return exhibitionPresenter.getAll(apiUrl, new TypeToken<List<Exhibition>>(){});
    }





    public static void main(String[] args) {



    }
}
