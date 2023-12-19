package run;

import server.RunAPIServer;

/**
 * Classe principal para carregar artistas.
 * Esta classe inicia um servidor e carrega dados de artistas e suas obras de arte usando a API Artsy.
 */
public class SecondLoadArtists {

    /**
     * Método principal para iniciar a aplicação.
     * Este método configura e executa o servidor, depois carrega informações de todos os artistas
     * com suas obras de arte e, finalmente, encerra o servidor.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {

        // Inicia o servidor da API
        RunAPIServer server = new RunAPIServer();
        server.run();

        // Importa dados de artistas e suas obras de arte do Artsy
        ImportArtsyData importArtsyData = new ImportArtsyData();
        importArtsyData.loadAllArtistsWithArtworks();

        // Mensagem de conclusão do carregamento de artistas
        System.out.println("Load Artists finished");

        // Para o servidor
        server.stop();
    }
}
