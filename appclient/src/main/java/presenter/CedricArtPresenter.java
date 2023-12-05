package presenter;

import domain.Artwork;

import java.util.List;

public class CedricArtPresenter {

    private ArtworkPresenter artworkPresenter;

    public CedricArtPresenter(){

        artworkPresenter= new ArtworkPresenter();

    }

    /**
     *
     * @param artworkId
     */
    public Artwork getArtwork(String artworkId) {

        Artwork artwork = this.artworkPresenter.getArtwork(artworkId);
        return artwork;

    }

    /**
     *
     * @return
     */
    public List<Artwork> getAllArtworks ()
    {

       return this.artworkPresenter.getAllArtworks();
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
