package artsy;

import domain.Gene;
import util.ArtsyUtils;

import java.util.ArrayList;
import java.util.List;

public class MainArtsy {

    public MainArtsy()
    {
        new ArtsyUtils();
    }

    public void loadAllGenes() {


        GeneArtsy geneArtsy = new GeneArtsy();
        //  new LigacaoArtsy();

        String xappToken = ArtsyUtils.generateXappToken();

        String apiUrl = "https://api.artsy.net/api/genes?total_count=true";

        //  DBStorage storage = new DBStorage();
        List<Gene> geneList = new ArrayList<>();

        do {
            apiUrl = geneArtsy.getAllGenes(apiUrl, xappToken, geneList);

        }
        while (!apiUrl.isBlank());

        System.out.println(geneList.size());

        for (Gene gene : geneList) {
            // inserir esse artista na base de dados

            System.out.println(gene);
            // this.storage.createGene(gene);
        }
    }
}
