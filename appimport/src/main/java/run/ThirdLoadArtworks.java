package run;

import server.RunAPIServer;

/**
 * Classe principal para carregar obras de arte.
 * Esta classe inicia um servidor e carrega dados de obras de arte de todos os artistas previamente carregados,
 * utilizando a API Artsy.
 */
public class ThirdLoadArtworks {

    /**
     * Método principal para iniciar a aplicação.
     * Este método configura e executa o servidor, carrega informações de obras de arte de todos os artistas
     * já carregados e, em seguida, encerra o servidor.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {

        // Inicia o servidor da API
        RunAPIServer server = new RunAPIServer();
        server.run();

        // Importa dados de obras de arte de todos os artistas carregados
        ImportArtsyData importArtsyData = new ImportArtsyData();
        importArtsyData.loadAllArtworksFromAllLoadedArtists();

        // Mensagem de conclusão do carregamento de obras de arte
        System.out.println("Load Artworks finished");

        // Para o servidor
        server.stop();
    }
}
