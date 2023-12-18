package presenter;

import domain.Artist;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CedricArtPresenterTest {

    @Test
    void testGetArtist() {
        // Arrange

        String artistId= "4d8b925d4eb68a1b2c000012";

        System.out.println(System.getProperty("user.dir"));

        TestGenericPresenter<Artist> testArtistPresenter = new TestGenericPresenter<>();
        Artist expectedArtist = new Artist();
        expectedArtist.setId(artistId);

        testArtistPresenter.setReturnValue(expectedArtist);

        CedricArtPresenter presenter = new CedricArtPresenter();

        // Act
        Artist result = presenter.getArtist(artistId);

        // Assert
        assertEquals(expectedArtist.getId(), result.getId());
    }

    // Similar tests for other methods...
}

