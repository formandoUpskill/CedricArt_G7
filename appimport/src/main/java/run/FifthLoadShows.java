package run;

import server.RunAPIServer;

/**
 * Classe principal para carregar dados de exposições.
 * Esta classe é responsável por iniciar um servidor e carregar informações de exposições
 * para todos os parceiros previamente carregados, usando a API Artsy.
 */
public class FifthLoadShows {

    /**
     * Método principal para iniciar a aplicação.
     * Este método inicializa o servidor, carrega informações de exposições para todos os parceiros
     * carregados e, em seguida, encerra o servidor.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {

        // Inicia o servidor da API
        RunAPIServer server = new RunAPIServer();
        server.run();

        // Importa dados de exposições para todos os parceiros carregados
        ImportArtsyData importArtsyData = new ImportArtsyData();
        importArtsyData.loadShowsForAllPartnersLoaded();

        // Mensagem de conclusão do carregamento de dados de exposições
        System.out.println("Load Shows finished");

        // Para o servidor
        server.stop();
    }
}
