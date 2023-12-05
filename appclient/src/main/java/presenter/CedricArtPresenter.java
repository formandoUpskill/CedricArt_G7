package presenter;

import domain.Artwork;
import util.AppUtils;

import java.util.List;

public class CedricArtPresenter {

    private ArtworkPresenter artworkPresenter;

    /**
     *
     */
    public CedricArtPresenter(){

        loadConfig();

        artworkPresenter= new ArtworkPresenter();

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


    public ArtworkPresenter getArtworkPresenter() {
        return this.artworkPresenter;
    }

    public static void main(String[] args) {

        CedricArtPresenter presenter = new CedricArtPresenter();

        String artworkId= "4eb2eb13e742d70001007baf";

        presenter.getArtwork(artworkId);

        presenter.getAllArtworks();

    }
}
