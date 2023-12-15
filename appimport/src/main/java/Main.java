import server.RunServer;

public class Main {

    public static void main(String[] args) {


        RunServer server=  new RunServer();
        server.run();


        ImportArtsyData importArtsyData = new ImportArtsyData();

//       importArtsyData.loadAllGenes();

//      importArtsyData.loadAllArtistsWithArtworks();

 //  importArtsyData.loadAllArtworksFromAllLoadedArtists();

//       importArtsyData.loadPartnerForAllArtworksLoaded();

        importArtsyData.loadShowsForAllPartnersLoaded();


        server.stop();




    }
}
