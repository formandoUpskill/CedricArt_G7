import artsy.ArtistArtsy;
import artsy.ArtworkArtsy;
import artsy.GeneArtsy;
import domain.Artist;
import domain.Artwork;
import domain.Gene;
import services.ArtistService;
import services.ArtworkService;
import services.GeneService;
import util.ImportUtils;

import java.util.ArrayList;
import java.util.List;


public class ImportArtsyData {

    private GeneService geneService;
    private ArtistService artistService;


    private ArtworkService artworkService;

    public ImportArtsyData()
    {
        new ImportUtils();
        this.geneService= new GeneService();
        this.artistService = new ArtistService();
        this.artworkService = new ArtworkService();
    }

    public void loadAllGenes() {


        GeneArtsy geneArtsy = new GeneArtsy();

        String xappToken = ImportUtils.generateXappToken();

        String artsyApiUrl = "https://api.artsy.net/api/genes?total_count=true";

        List<Gene> geneList = new ArrayList<>();

        do {
            artsyApiUrl = geneArtsy.getAllGenes(artsyApiUrl, xappToken, geneList);

        }
        while (!artsyApiUrl.isBlank());

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/genes";

        for (Gene gene : geneList) {
            // inserir esse gene na base de dados
             this.geneService.createGene(apiUrl, gene);
        }
    }

    /**
     *
     */

    public void loadAllArtistsWithArtworks (){


        ArtistArtsy artistArtsy = new ArtistArtsy();

        String xappToken = ImportUtils.generateXappToken();

        String artsyApiUrl = "https://api.artsy.net/api/artists?artworks=true&size=500&total_count=true";

        List<Artist> artistsList = new ArrayList<>();

        do {
            artsyApiUrl = artistArtsy.getAllArtistsIdWithArtworks(artsyApiUrl, xappToken, artistsList);

        }
        while (!artsyApiUrl.isBlank());


        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artists";


        for (Artist artist : artistsList) {
            // inserir esse artista na base de dados
            this.artistService.createArtist(apiUrl,artist);

        }
    }


    /**
     *
     */
    public void loadAllArtworksFromAllLoadedArtists()
    {

        // obter todos os artistas que estão na base de dados
        // e para cada artista chamar https://api.artsy.net/api/artworks?artist_id=4d8b92b34eb68a1b2c0003f4

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artists";

        List<Artist> artistsList =  this.artistService.getAllArtists(apiUrl);

        for(Artist artist: artistsList){

            loadAllArtworks(artist);

        }
    }



    public void loadPartberForAllArtworksLoaded()
    {

        // obter todos as obras de arte que estão na base de dados
        // e para cada obre de arte  chamar https://api.artsy.net/api/artworks?artist_id=4d8b92b34eb68a1b2c0003f4

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artists";

        List<Artist> artistsList =  this.artistService.getAllArtists(apiUrl);

        for(Artist artist: artistsList){

            loadAllArtworks(artist);

        }
    }

    private void loadAllArtworks(Artist artist)
    {

        ArtworkArtsy artworkArtsy = new ArtworkArtsy();

        String xappToken = ImportUtils.generateXappToken();

        String artsyApiUrl = "https://api.artsy.net/api/artworks?artist_id=" + artist.getId() +"&total_count=true";

        List<Artwork> artworkList = new ArrayList<>();

        do {
            artsyApiUrl = artworkArtsy.getAllArtworksOfAnArtist(artsyApiUrl, xappToken, artworkList);

        }
        while (!artsyApiUrl.isBlank());


        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artworks";


        for (Artwork artwork : artworkList) {
            // inserir esse artista na base de dados
            artwork.setArtist(artist);

            System.out.println("artworkartworkartwork "  + artwork);
            this.artworkService.createArtwork(apiUrl,artwork);

        }

    }

}
