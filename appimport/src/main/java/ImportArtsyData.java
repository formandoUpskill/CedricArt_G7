import artsy.GeneArtsy;
import domain.Gene;
import services.GeneService;
import util.ImportUtils;

import java.util.ArrayList;
import java.util.List;


public class ImportArtsyData {

    private GeneService geneService;


    public ImportArtsyData()
    {
        new ImportUtils();
        this.geneService= new GeneService();
    }

    public void loadAllGenes() {


        GeneArtsy geneArtsy = new GeneArtsy();
        //  new LigacaoArtsy();

        String xappToken = ImportUtils.generateXappToken();

        String artsyApiUrl = "https://api.artsy.net/api/genes?total_count=true";

        //  DBStorage storage = new DBStorage();
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



}
