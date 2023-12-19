package run;

import server.RunAPIServer;

/**
 * Classe principal para carregar dados de parceiros.
 * Esta classe é responsável por iniciar um servidor e carregar dados de parceiros para todas as obras de arte
 * previamente carregadas, utilizando a API Artsy.
 */
public class FourthLoadPartners {

    /**
     * Método principal para iniciar a aplicação.
     * Este método inicializa o servidor, carrega informações de parceiros para as obras de arte
     * carregadas e, em seguida, encerra o servidor.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {

        // Inicia o servidor da API
        RunAPIServer server = new RunAPIServer();
        server.run();

        // Importa dados de parceiros para as obras de arte carregadas
        ImportArtsyData importArtsyData = new ImportArtsyData();
        importArtsyData.loadPartnerForAllArtworksLoaded();

        // Mensagem de conclusão do carregamento de dados de parceiros
        System.out.println("Load Partners finished");

        // Para o servidor
        server.stop();
    }
}
