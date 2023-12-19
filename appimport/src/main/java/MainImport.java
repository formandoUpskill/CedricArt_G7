import server.RunAPIServer;

public class MainImport {

    public static void main(String[] args) {


        RunAPIServer server=  new RunAPIServer();
        //RunServer server= new RunServer();
        server.run();


        ImportArtsyData importArtsyData = new ImportArtsyData();

        System.out.println(" APP IMPORT:"+ System.getProperty("user.dir"));

  // importArtsyData.loadAllGenes();

  // importArtsyData.loadAllArtistsWithArtworks();

  // importArtsyData.loadAllArtworksFromAllLoadedArtists();

    importArtsyData.loadPartnerForAllArtworksLoaded();

    importArtsyData.loadShowsForAllPartnersLoaded();

        System.out.println("importArtsyData finish");


       server.stop();




    }
}
