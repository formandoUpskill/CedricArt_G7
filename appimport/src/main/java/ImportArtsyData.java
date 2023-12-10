import artsy.ArtistArtsy;
import artsy.GeneArtsy;
import domain.Artist;
import domain.Gene;
import services.ArtistService;
import services.GeneService;
import util.ImportUtils;

import java.util.ArrayList;
import java.util.List;


public class ImportArtsyData {

    private GeneService geneService;
    private ArtistService artistService;


    public ImportArtsyData()
    {
        new ImportUtils();
        this.geneService= new GeneService();
        this.artistService = new ArtistService();
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




}
