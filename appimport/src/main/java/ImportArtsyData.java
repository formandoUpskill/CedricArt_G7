import artsy.*;
import domain.*;
import services.*;
import util.ImportUtils;

import java.util.ArrayList;
import java.util.List;


public class ImportArtsyData {

    private GeneService geneService;
    private ArtistService artistService;


    private ArtworkService artworkService;

    private PartnerService partnerService;


    private ShowService showService;

    private boolean isFastLoad;

    /**
     *
     */
    public ImportArtsyData()
    {
        new ImportUtils();
        this.geneService= new GeneService();
        this.artistService = new ArtistService();
        this.artworkService = new ArtworkService();
        this.partnerService = new PartnerService();
        this.showService = new ShowService();
        isFastLoad= ImportUtils.IS_FAST_ARTSY_LOAD;

    }

    /**
     *
     */
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
    public void loadShowsForAllPartnersLoaded()
    {

// obter todos as os partners que estão na base de dados
// e para cada partbner  chamar https://api.artsy.net/api/shows?partner_id=4df2262be7432d000100ba86

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/partners";

        List<Partner> partnerList =  this.partnerService.getAllPartners(apiUrl);


        for(Partner partner: partnerList){

            loadAllShows(partner);
        }
    }


    /**
     *
     * @param partner
     */
    private void loadAllShows(Partner partner)
    {

        ShowArtsy showArtsy = new ShowArtsy();

        List<Exhibition> exhibitionList = new ArrayList<>();

        String xappToken = ImportUtils.generateXappToken();


        String artsyApiUrl = "https://api.artsy.net/api/shows?partner_id=" + partner.getId() +"&total_count=true";

        if (this.isFastLoad)
        {
            artsyApiUrl = "https://api.artsy.net/api/shows?partner_id=" + partner.getId();
            showArtsy.getAllShows (artsyApiUrl, xappToken, exhibitionList);
        }
        else {

            do {

                artsyApiUrl = showArtsy.getAllShows (artsyApiUrl, xappToken, exhibitionList);

            }
            while (!artsyApiUrl.isBlank());
        }



        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/shows";

        for (Exhibition exhibition : exhibitionList) {
            // colocar o partner desse show
            exhibition.setPartner(partner);

            // colocar a lista de obras de arte desse show

            // para cada exibição obter as obras de arte desse exibição
            // https://api.artsy.net/api/artworks?show_id=4ea19ee97bab1a0001001908
            exhibition.setArtworks(getAllArtworks(exhibition));

            this.showService.createShow(apiUrl,exhibition);

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

    /**
     *
     * @param partnerlink
     * @param artwork
     */
    private void loadPartner(String partnerlink, Artwork artwork)
    {

        // obter o partner
        PartnerArtsy partnerArtsy = new PartnerArtsy();

        String xappToken = ImportUtils.generateXappToken();

        String artsyApiUrl = partnerlink;

        /*@todo: Tratar desta excepção

            https://api.artsy.net/api/artworks/53dba451726169410d330300
null
Exception in thread "main" java.lang.NullPointerException: Parameter specified as non-null is null: method okhttp3.Request$Builder.url, parameter url
	at okhttp3.Request$Builder.url(Request.kt)
	at artsy.PartnerArtsy.getPartner(PartnerArtsy.java:32)
	at ImportArtsyData.loadPartner(ImportArtsyData.java:233)
	at ImportArtsyData.loadPartnerForAllArtworksLoaded(ImportArtsyData.java:213)
	at Main.main(Main.java:14)
	*/


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

        if (this.isFastLoad)
        {
            artsyApiUrl = "https://api.artsy.net/api/artworks?artist_id=" + artist.getId();
            artworkArtsy.getAllArtworks(artsyApiUrl, xappToken, artworkList);
        }
        else {

            do {
                artsyApiUrl = artworkArtsy.getAllArtworks(artsyApiUrl, xappToken, artworkList);

            }
            while (!artsyApiUrl.isBlank());
        }

        String apiUrl = ImportUtils.CEDRIC_ART_API_HOST+ "/artworks";


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
            artsyApiUrl = artwork.getGenesLink() ;
            geneArtsy.getAllGenes(artsyApiUrl, xappToken, geneList);


            artwork.setArtist(artist);
            artwork.setGeneList(geneList);

            this.artworkService.createArtwork(apiUrl,artwork);

        }

    }

    /**
     *
     * @param exhibition
     * @return
     */
    private List<Artwork> getAllArtworks(Exhibition exhibition)
    {
        ArtworkArtsy artworkArtsy = new ArtworkArtsy();

        String xappToken = ImportUtils.generateXappToken();

        String artsyApiUrl = "https://api.artsy.net/api/artworks?show_id=" + exhibition.getId() +"&total_count=true";
        List<Artwork> artworkList = new ArrayList<>();

        if (this.isFastLoad)
        {
            artsyApiUrl = "https://api.artsy.net/api/artworks?show_id=" + exhibition.getId();
            artworkArtsy.getAllArtworks(artsyApiUrl, xappToken, artworkList);
        }
        else {

            do {
                artsyApiUrl = artworkArtsy.getAllArtworks(artsyApiUrl, xappToken, artworkList);

            }
            while (!artsyApiUrl.isBlank());
        }

        return artworkList;
    }


}
