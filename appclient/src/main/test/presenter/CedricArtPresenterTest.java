package presenter;

import domain.Artist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CedricArtPresenterTest {

    String artworkId= "4eb2eb13e742d70001007baf";
    String partnerId="51cc9a88275b24f8700000db";
    String exhibitionId="57ec2f87275b2403270003bf";
    String artistId= "4d8b92684eb68a1b2c00009e";

    CedricArtPresenter presenter;


    @Test
    void testGetArtistID4d8b925d4eb68a1b2c000012OK() {
        // Arrange


        Artist expectedArtist = new Artist();
        expectedArtist.setId(artistId);

        presenter = new CedricArtPresenter();;
        Artist result = presenter.getArtist(artistId);

        // Assert
        assertEquals(expectedArtist.getId(), result.getId());
    }

    @Test
    void testGetArtistID4d8b925d4eb68a1b2c000012NotOK() {
        // Arrange

        Artist expectedArtist = new Artist();
        expectedArtist.setId(artistId+"sdsdsd");


        CedricArtPresenter presenter = new CedricArtPresenter();
        Artist result = presenter.getArtist(artistId);

        // Assert
        assertNotEquals (expectedArtist.getId(), result.getId());
    }


    @Test
    void getAllArtists() {
    }

    @Test
    void getAllArtistsByPartnerId51cc9a88275b24f8700000dbOK() {


        int expectSize= 65;

        CedricArtPresenter presenter = new CedricArtPresenter();
        int resultSize = presenter.getAllArtistsByPartner(partnerId).size();

        // Assert
        assertEquals (expectSize, resultSize);

    }

    @Test
    void getAllArtistsByExhibition() {
    }

    @Test
    void getArtwork() {
    }

    @Test
    void getAllArtworks() {
    }

    @Test
    void getAllArtworksByPartner() {
    }

    @Test
    void getAllArtworksByArtist() {
    }

    @Test
    void getAllArtworksByExhibition() {
    }

    @Test
    void getPartner() {
    }

    @Test
    void getAllPartners() {
    }

    @Test
    void getExhibition() {
    }

    @Test
    void getAllExhibitions() {
    }

    @Test
    void getAllExhibitionsByPartner() {
    }

    // Similar tests for other methods...
}

