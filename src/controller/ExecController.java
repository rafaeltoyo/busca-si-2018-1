package controller;

import algoritmos.busca.TipoBusca;
import sistema.Agente;

public class ExecController
{
    private final ModelController modelController = ModelController.getInstance();
    private final BuscaController buscaController = BuscaController.getInstance();
    private final ViewController viewController = ViewController.getInstance();

    private static ExecController ourInstance = new ExecController();

    public static ExecController getInstance() {
        return ourInstance;
    }

    private ExecController() { }

    public void run()
    {
        // Escolher o método de busca
        TipoBusca tipo = viewController.printTipoBuscaOptions();
        run(tipo);
    }

    public void run(TipoBusca tipo)
    {
        // Criar um novo agente e para o controller de busca
        buscaController.setAgente(new Agente(modelController.getModel()));

        try {
            // Prepara a busca
            buscaController.prepare(tipo);
            // Executar a busca
            buscaController.execute();
        } catch (Throwable tw) {
            ViewController.printError("Erro na execução da busca:", tw);
        }

        // Ciclo de execucao do sistema
        // desenha labirinto
        ViewController.desenharLabModel();

        // agente escolhe proxima açao e a executa no ambiente (modificando
        // o estado do labirinto porque ocupa passa a ocupar nova posicao)
        System.out.println("\n*** Inicio do ciclo de raciocinio do agente ***\n");
        while (buscaController.getAgente().deliberar() != -1) {
            ViewController.desenharLabModel();
        }
    }
}
