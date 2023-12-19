package presenter;

import com.google.gson.reflect.TypeToken;
import domain.Artist;
import domain.Artwork;
import domain.Exhibition;
import domain.Partner;
import util.AppUtils;

import java.util.List;

/**
 * Presenter class for Cedric Art API.
 * This class provides methods to interact with the Cedric Art API
 * and retrieve data about artists, artworks, exhibitions, and partners.
 */
public class CedricArtPresenter {

   // private ArtworkPresenter artworkPresenter;


    private GenericPresenter<Artwork> artworkPresenter;
    private GenericPresenter<Partner> partnerPresenter;

    private GenericPresenter<Exhibition>  exhibitionPresenter;

    private GenericPresenter<Artist> artistPresenter;


    /**
     * Constructor for initializing presenters.
     */
    public CedricArtPresenter(){

        loadConfig();

        partnerPresenter = new GenericPresenter<>();
        exhibitionPresenter = new GenericPresenter<>();
        artworkPresenter = new GenericPresenter<>();
        artistPresenter= new GenericPresenter<>();

    }

    /**
     * Loads configuration settings.
     */
    private void loadConfig()
    {
        new AppUtils();
    }


    /**
     * Retrieves an artist by their ID.
     *
     * @param artistId The ID of the artist.
     * @return The Artist object corresponding to the specified ID.
     */
    public Artist getArtist(String artistId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists/";

        return  artistPresenter.get(apiUrl,artistId, Artist.class);

    }

    /**
     * Retrieves all available artists.
     *
     * @return A list of all Artists.
     */
    public List<Artist> getAllArtists ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists";
        return artistPresenter.getAll(apiUrl, new TypeToken<List<Artist>>(){});
    }


    /**
     * Retrieves all artists associated with a specific partner.
     *
     * @param partnerId The ID of the partner.
     * @return A list of Artists associated with the given partner ID.
     */
    public List<Artist> getAllArtistsByPartner (String partnerId)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists?partner_id=" + partnerId;
        return artistPresenter.getAll(apiUrl, new TypeToken<List<Artist>>(){});
    }

    /**
     * Retrieves all artists participating in a specific exhibition.
     *
     * @param exhibitionId The ID of the exhibition.
     * @return A list of Artists participating in the given exhibition.
     */
    public List<Artist> getAllArtistsByExhibition (String exhibitionId)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artists?show_id=" + exhibitionId;

        return artistPresenter.getAll(apiUrl, new TypeToken<List<Artist>>(){});
    }


    /**
     * Retrieves an artwork by its ID.
     *
     * @param artworkId The ID of the artwork.
     * @return The Artwork object corresponding to the specified ID.
     */
    public Artwork getArtwork(String artworkId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks/";
        return  artworkPresenter.get(apiUrl,artworkId, Artwork.class);

    }

    /**
     * Retrieves all available artworks.
     *
     * @return A list of all Artworks.
     */
    public List<Artwork> getAllArtworks ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks";
        return artworkPresenter.getAll(apiUrl, new TypeToken<List<Artwork>>(){});

    }


    /**
     * Retrieves all artworks associated with a specific partner.
     *
     * @param partnerId The ID of the partner.
     * @return A list of Artworks associated with the given partner ID.
     */
    public List<Artwork> getAllArtworksByPartner (String partnerId)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks?partner_id=" + partnerId;

        return artworkPresenter.getAll(apiUrl, new TypeToken<List<Artwork>>(){});

    }

    /**
     * Retrieves all artworks created by a specific artist.
     *
     * @param artistId The ID of the artist.
     * @return A list of Artworks created by the given artist.
     */
    public List<Artwork> getAllArtworksByArtist (String artistId)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks?artist_id=" + artistId;

        return artworkPresenter.getAll(apiUrl, new TypeToken<List<Artwork>>(){});


    }


    /**
     * Retrieves all artworks featured in a specific exhibition.
     *
     * @param exhibitionId The ID of the exhibition.
     * @return A list of Artworks featured in the given exhibition.
     */
    public List<Artwork> getAllArtworksByExhibition (String exhibitionId)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/artworks?show_id=" + exhibitionId;
        return artworkPresenter.getAll(apiUrl, new TypeToken<List<Artwork>>(){});

    }

    /**
     * Retrieves a partner by their ID.
     *
     * @param partnerId The ID of the partner.
     * @return The Partner object corresponding to the specified ID.
     */
    public Partner getPartner(String partnerId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/partners/";

        return  partnerPresenter.get(apiUrl,partnerId, Partner.class);

    }

    /**
     * Retrieves all available partners.
     *
     * @return A list of all Partners.
     */
    public List<Partner> getAllPartners ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/partners";
        return partnerPresenter.getAll(apiUrl, new TypeToken<List<Partner>>(){});
    }



    /**
     * Retrieves an exhibition by its ID.
     *
     * @param exhibitionId The ID of the exhibition.
     * @return The Exhibition object corresponding to the specified ID.
     */
    public Exhibition getExhibition(String exhibitionId) {

        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows/";

        return  exhibitionPresenter.get(apiUrl,exhibitionId, Exhibition.class);

    }

    /**
     * Retrieves all available exhibitions.
     *
     * @return A list of all Exhibitions.
     */
    public List<Exhibition> getAllExhibitions ()
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows";
        return exhibitionPresenter.getAll(apiUrl, new TypeToken<List<Exhibition>>(){});
    }

    /**
     * Retrieves all exhibitions associated with a specific partner.
     *
     * @param partnerId The ID of the partner.
     * @return A list of Exhibitions associated with the given partner ID.
     */
    public List<Exhibition> getAllExhibitionsByPartner (String partnerId)
    {
        String apiUrl = AppUtils.CEDRIC_ART_API_HOST+ "/shows?partner_id=" + partnerId;
        return exhibitionPresenter.getAll(apiUrl, new TypeToken<List<Exhibition>>(){});
    }


}
