package controller;

import algoritmos.busca.*;
import algoritmos.heuristica.HeuristicaManhattan;
import algoritmos.heuristica.HeuristicaPeitoDePeruNaPascoa;
import sistema.Agente;

public class BuscaController
{
    private BuscaCega busca;
    private Agente agente;

    private static BuscaController ourInstance = new BuscaController();

    public static BuscaController getInstance() {
        return ourInstance;
    }

    private BuscaController()
    {
        this.agente = null;
        this.busca = null;
    }

    public void prepare(TipoBusca tipo)
    {
        if (this.agente == null) {
            throw new RuntimeException("Agente não inicializado");
        }

        switch (tipo) {
            default:
            case CUSTO_UNI:
                this.busca = new BuscaCustoUni(this.agente);
                break;
            case ESTRELA_1:
                this.busca = new BuscaEstrela(this.agente, new HeuristicaManhattan());
                break;
            case ESTRELA_2:
                this.busca = new BuscaEstrela(this.agente, new HeuristicaPeitoDePeruNaPascoa());
                break;
        }
    }

    public void execute() throws RuntimeException
    {
        if (this.agente == null) {
            throw new RuntimeException("Agente não inicializado");
        }

        if (this.busca == null) {
            throw new RuntimeException("Busca não preparada");
        }

        this.busca.exec();
        this.busca.setAgentePlan();
    }

    public BuscaCega getBusca() {
        return busca;
    }

    public void setBusca(BuscaCega busca) {
        this.busca = busca;
    }

    public Agente getAgente() {
        return agente;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }
}
