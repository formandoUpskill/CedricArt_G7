import artsy.ArtistArtsy;
import artsy.ArtworkArtsy;
import artsy.GeneArtsy;
import artsy.PartnerArtsy;
import domain.Artist;
import domain.Artwork;
import domain.Gene;
import domain.Partner;
import services.ArtistService;
import services.ArtworkService;
import services.GeneService;
import services.PartnerService;
import util.ImportUtils;

import java.util.ArrayList;
import java.util.List;


public class ImportArtsyData {

    private GeneService geneService;
    private ArtistService artistService;


    private ArtworkService artworkService;

    private PartnerService partnerService;

    public ImportArtsyData()
    {
        new ImportUtils();
        this.geneService= new GeneService();
        this.artistService = new ArtistService();
        this.artworkService = new ArtworkService();
        this.partnerService = new PartnerService();
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


    /**
     *
     */

    public void loadPartnerForAllArtworksLoaded()
    {

        // obter todos as obras de arte que estão na base de dados
        // e para cada obra de arte  chamar https://api.artsy.net/api/artworks/id_artwork e obter o partner links

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artworks";

        List<Artwork> artworkList =  this.artworkService.getAllArtworks(apiUrl);


        for(Artwork artwork: artworkList){

            String partnerLink = getPartnerLink(artwork);

            loadPartner(partnerLink, artwork);

        }
    }


    private void loadPartner(String partnerlink, Artwork artwork)
    {

        // obter o partner
        PartnerArtsy partnerArtsy = new PartnerArtsy();

        String xappToken = ImportUtils.generateXappToken();

        String artsyApiUrl = partnerlink;

        Partner partner= partnerArtsy.getPartner(artsyApiUrl,xappToken, 1,2);
        partner.setArtwork(artwork);

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/partners";



        this.partnerService.createPartner(apiUrl,partner);

    }

    /**
     *
     * @param artwork
     */

    private String getPartnerLink(Artwork artwork)
    {

        ArtworkArtsy artworkArtsy = new ArtworkArtsy();
        String xappToken = ImportUtils.generateXappToken();

        //https://api.artsy.net/api/artworks/id_artwork e obter o partner links
        String artsyApiUrl = "https://api.artsy.net/api/artworks/" + artwork.getId();

        return artworkArtsy.getPartnerLinks(artsyApiUrl,xappToken);

    }


    /**
     *
     * @param artist
     */
    private void loadAllArtworks(Artist artist)
    {

        ArtworkArtsy artworkArtsy = new ArtworkArtsy();
        GeneArtsy geneArtsy = new GeneArtsy();


        String xappToken = ImportUtils.generateXappToken();

        String artsyApiUrl = "https://api.artsy.net/api/artworks?artist_id=" + artist.getId() +"&total_count=true";

        List<Artwork> artworkList = new ArrayList<>();

        do {
            artsyApiUrl = artworkArtsy.getAllArtworksOfAnArtist(artsyApiUrl, xappToken, artworkList);

        }
        while (!artsyApiUrl.isBlank());


        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artworks";


        for (Artwork artwork : artworkList) {



            List<Gene> geneList = new ArrayList<>();
            artsyApiUrl = artwork.getGenesLink() + "&total_count=true";
            do {
                artsyApiUrl = geneArtsy.getAllGenes(artsyApiUrl, xappToken, geneList);

            }
            while (!artsyApiUrl.isBlank());

            artwork.setArtist(artist);
            artwork.setGeneList(geneList);

            this.artworkService.createArtwork(apiUrl,artwork);

        }

    }




}
