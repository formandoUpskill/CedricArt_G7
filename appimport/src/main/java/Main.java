public class Main {

    public static void main(String[] args) {

        ImportArtsyData importArtsyData = new ImportArtsyData();

       importArtsyData.loadAllGenes();

        importArtsyData.loadAllArtistsWithArtworks();

       importArtsyData.loadAllArtworksFromAllLoadedArtists();

        importArtsyData.loadPartnerForAllArtworksLoaded();







    }
}
