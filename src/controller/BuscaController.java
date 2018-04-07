package controller;

import algoritmos.busca.*;
import algoritmos.heuristica.HeuristicaChebyshev;
import algoritmos.heuristica.HeuristicaEuclidiana;
import sistema.Agente;

import java.util.ArrayList;
import java.util.List;

public class BuscaController
{

    public final static List<Busca> bufferBuscas = new ArrayList<>();

    // #################################################################################################################

    private static BuscaController ourInstance = new BuscaController();

    public static BuscaController getInstance() {
        return ourInstance;
    }

    private BuscaController() {}

    // #################################################################################################################

    public static Busca prepare(Agente agente, TipoBusca tipo)
    {
        Busca busca = null;
        switch (tipo) {
            default:
            case CUSTO_UNI:
                busca = (new BuscaCustoUniforme(agente.getProblem()));
                break;
            case ESTRELA_1:
                busca = (new BuscaInformada(agente.getProblem(), new HeuristicaChebyshev()));
                break;
            case ESTRELA_2:
                busca = (new BuscaInformada(agente.getProblem(), new HeuristicaEuclidiana()));
                break;
            case LRTA_1:
                busca = (new BuscaLRTA());
        }
        agente.setBusca(busca);
        bufferBuscas.add(busca);
        return busca;
    }
}
