package run;

import server.RunAPIServer;

/**
 * Classe principal para carregar genes.
 * Esta classe inicializa o servidor e carrega dados de genes usando a API Artsy.
 */
public class FirstLoadGenes {

    /**
     * Método principal para iniciar a aplicação.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     */
    public static void main(String[] args) {

        // Inicia o servidor da API
        RunAPIServer server = new RunAPIServer();
        server.run();

        // Importa dados do Artsy
        ImportArtsyData importArtsyData = new ImportArtsyData();
        importArtsyData.loadAllGenes();

        // Mensagem de conclusão do carregamento
        System.out.println("Load Genes finished");

        // Para o servidor
        server.stop();
    }
}
