package run;

import artsy.*;
import domain.*;
import services.*;
import util.ImportUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for importing data from the Artsy API.
 * Handles the import of genes, artists, artworks, partners, and shows.
 */
public class ImportArtsyData {

    private GeneService geneService;
    private ArtistService artistService;


    private ArtworkService artworkService;

    private PartnerService partnerService;


    private ShowService showService;

    private boolean isFastLoad;

    private String xappToken;


    /**
     * Constructor for ImportArtsyData.
     * Initializes services and configurations for importing data.
     */
    public ImportArtsyData()
    {
        new ImportUtils();
        this.xappToken= ImportUtils.GENERATED_XAPP_TOKEN;
        this.geneService= new GeneService();
        this.artistService = new ArtistService();
        this.artworkService = new ArtworkService();
        this.partnerService = new PartnerService();
        this.showService = new ShowService();
        isFastLoad= ImportUtils.IS_FAST_ARTSY_LOAD;

    }


    /**
     * Loads all genes from the Artsy API and stores them in the database.
     */
    public void loadAllGenes() {

        GeneArtsy geneArtsy = new GeneArtsy();

        String artsyApiUrl = "https://api.artsy.net/api/genes?total_count=true";

        List<Gene> geneList = new ArrayList<>();

        do {
            try {
                artsyApiUrl = geneArtsy.getAll(artsyApiUrl, xappToken, geneList);
            } catch (ArtsyException e) {
                e.printStackTrace();
            }

        }
        while (!artsyApiUrl.isBlank());

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/genes";

        for (Gene gene : geneList) {
            // inserir esse gene na base de dados
             this.geneService.create(apiUrl, gene);
        }
    }

    /**
     * Loads all artists with artworks from the Artsy API and stores them in the database.
     */

    public void loadAllArtistsWithArtworks (){

        ArtistArtsy artistArtsy = new ArtistArtsy();

        String artsyApiUrl = "https://api.artsy.net/api/artists?artworks=true&size=500&total_count=true";

        List<Artist> artistsList = new ArrayList<>();

        do {
            try {
                artsyApiUrl = artistArtsy.getAll(artsyApiUrl, xappToken, artistsList);
            } catch (ArtsyException e) {
                e.printStackTrace();
            }

        }
        while (!artsyApiUrl.isBlank());

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artists";

        for (Artist artist : artistsList) {
            // inserir esse artista na base de dados
            this.artistService.create(apiUrl,artist);

        }
    }


    /**
     * Loads all artworks for all artists loaded in the database.
     */
    public void loadAllArtworksFromAllLoadedArtists()
    {

        // obter todos os artistas que estão na base de dados
        // e para cada artista chamar https://api.artsy.net/api/artworks?artist_id=4d8b92b34eb68a1b2c0003f4

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artists";

        List<Artist> artistsList =  this.artistService.getAll(apiUrl);

        for(Artist artist: artistsList){

            loadAllArtworks(artist);

        }
    }


    /**
     * Loads shows for all partners loaded in the database.
     */
    public void loadShowsForAllPartnersLoaded()
    {

// obter todos as os partners que estão na base de dados
// e para cada partbner  chamar https://api.artsy.net/api/shows?partner_id=4df2262be7432d000100ba86

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/partners";

        List<Partner> partnerList =  this.partnerService.getAll(apiUrl);

        for(Partner partner: partnerList){

            try {
                loadAllShows(partner);
            } catch (ArtsyException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * Loads all shows for a given partner from the Artsy API.
     *
     * @param partner The partner for which shows are to be loaded.
     * @throws ArtsyException If an error occurs while fetching data from the Artsy API.
     */

    private void loadAllShows(Partner partner)  throws ArtsyException{

        ShowArtsy showArtsy = new ShowArtsy();

        List<Exhibition> exhibitionList = new ArrayList<>();

        String artsyApiUrl = "https://api.artsy.net/api/shows?partner_id=" + partner.getId() +"&total_count=true";


        try {
            if (this.isFastLoad) {
                artsyApiUrl = "https://api.artsy.net/api/shows?partner_id=" + partner.getId();
                showArtsy.getAll(artsyApiUrl, xappToken, exhibitionList);

            } else {

                do {
                    artsyApiUrl = showArtsy.getAll(artsyApiUrl, xappToken, exhibitionList);

                }
                while (!artsyApiUrl.isBlank());
            }


            String apiUrl = ImportUtils.CEDRIC_ART_API_HOST + "/shows";

            for (Exhibition exhibition : exhibitionList) {
                // colocar o partner desse show
                exhibition.setPartner(partner);

                // colocar a lista de obras de arte desse show

                // para cada exibição obter as obras de arte desse exibição
                // https://api.artsy.net/api/artworks?show_id=4ea19ee97bab1a0001001908
                exhibition.setArtworks(getAllArtworks(exhibition));

                this.showService.create(apiUrl, exhibition);

            }
        }

        catch (ArtsyException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Loads partner information for all artworks loaded in the database.
     */
    public void loadPartnerForAllArtworksLoaded()
    {

        // obter todos as obras de arte que estão na base de dados
        // e para cada obra de arte  chamar https://api.artsy.net/api/artworks/id_artwork e obter o partner links

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artworks";

        List<Artwork> artworkList =  this.artworkService.getAll(apiUrl);

        for(Artwork artwork: artworkList){

            String partnerLink = null;
            try {
                partnerLink = getPartnerLink(artwork);

                if (partnerLink!=null) {

                    loadPartner(partnerLink, artwork);
                }
            } catch (ArtsyException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Loads partner information for a specific artwork.
     *
     * @param partnerlink The API link to the partner information.
     * @param artwork The artwork for which the partner is being loaded.
     */
    private void loadPartner(String partnerlink, Artwork artwork)
    {

        // obter o partner
        PartnerArtsy partnerArtsy = new PartnerArtsy();

        String artsyApiUrl = partnerlink;

        Partner partner= null;
        try {
            partner = partnerArtsy.getPartner(artsyApiUrl,xappToken, 1,2);

             partner.setArtwork(artwork);

             String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/partners";

            this.partnerService.create(apiUrl,partner);



        } catch (ArtsyException e) {
            e.printStackTrace();
        }

    }

    /**
     * Retrieves the partner link for a specific artwork.
     *
     * @param artwork The artwork for which the partner link is to be retrieved.
     * @return The API link to the partner information.
     * @throws ArtsyException If an error occurs while fetching data from the Artsy API.
     */

    private String getPartnerLink(Artwork artwork) throws ArtsyException {

        ArtworkArtsy artworkArtsy = new ArtworkArtsy();

        //https://api.artsy.net/api/artworks/id_artwork e obter o partner links
        String artsyApiUrl = "https://api.artsy.net/api/artworks/" + artwork.getId();

        return artworkArtsy.getPartnerLinks(artsyApiUrl,xappToken);

    }


    /**
     * Loads all artworks for a given artist from the Artsy API.
     *
     * @param artist The artist for whom artworks are to be loaded.
     */
    private void loadAllArtworks(Artist artist)
    {
        ArtworkArtsy artworkArtsy = new ArtworkArtsy();
        GeneArtsy geneArtsy = new GeneArtsy();

        String artsyApiUrl = "https://api.artsy.net/api/artworks?artist_id=" + artist.getId() +"&total_count=true";
        List<Artwork> artworkList = new ArrayList<>();

        try {
            if (this.isFastLoad) {
                artsyApiUrl = "https://api.artsy.net/api/artworks?artist_id=" + artist.getId();
                artworkArtsy.getAll(artsyApiUrl, xappToken, artworkList);
            } else {

                do {
                    artsyApiUrl = artworkArtsy.getAll(artsyApiUrl, xappToken, artworkList);

                }
                while (!artsyApiUrl.isBlank());
            }

            String apiUrl = ImportUtils.CEDRIC_ART_API_HOST + "/artworks";

            for (Artwork artwork : artworkList) {

                List<Gene> geneList = new ArrayList<>();

            /* @todo: no fim retirar este comentário do código -- é apenas para carregar mais depressa os genes
            if (this.isFastLoad)
            {
                artsyApiUrl = artwork.getGenesLink() ;
                geneArtsy.getAllGenes(artsyApiUrl, xappToken, geneList);
            }
            else {
                artsyApiUrl = artwork.getGenesLink() + "&total_count=true";
                 do {
                   artsyApiUrl = geneArtsy.getAllGenes(artsyApiUrl, xappToken, geneList);
                  }
                while (!artsyApiUrl.isBlank());
            }
            */
                /* @todo: no fim descpmentar o código acima e apagar este código-- é apenas para carregar mais depresa os genes */
                artsyApiUrl = artwork.getGenesLink();
                geneArtsy.getAll(artsyApiUrl, xappToken, geneList);

                artwork.setArtist(artist);
                artwork.setGeneList(geneList);

                this.artworkService.create(apiUrl, artwork);
            }
        }
        catch (ArtsyException e){
            e.printStackTrace();
        }

    }

    /**
     * Retrieves all artworks for a given exhibition from the Artsy API.
     *
     * @param exhibition The exhibition for which artworks are to be retrieved.
     * @return A list of artworks associated with the exhibition.
     * @throws ArtsyException If an error occurs while fetching data from the Artsy API.
     */
    private List<Artwork> getAllArtworks(Exhibition exhibition) throws ArtsyException
    {
        ArtworkArtsy artworkArtsy = new ArtworkArtsy();

        String artsyApiUrl = "https://api.artsy.net/api/artworks?show_id=" + exhibition.getId() +"&total_count=true";
        List<Artwork> artworkList = new ArrayList<>();

        if (this.isFastLoad)
        {
            artsyApiUrl = "https://api.artsy.net/api/artworks?show_id=" + exhibition.getId();
            artworkArtsy.getAll(artsyApiUrl, xappToken, artworkList);
        }
        else {

            do {
                artsyApiUrl = artworkArtsy.getAll(artsyApiUrl, xappToken, artworkList);

            }
            while (!artsyApiUrl.isBlank());
        }

        return artworkList;
    }

}
