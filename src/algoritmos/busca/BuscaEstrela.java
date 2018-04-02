package algoritmos.busca;

import algoritmos.heuristica.Heuristica;
import sistema.Agente;

public class BuscaEstrela extends BuscaCega
{

    public BuscaEstrela(Agente agente, Heuristica heuristica)
    {
        super(agente, heuristica);
    }

    @Override
    protected String getTitle() { return "@@@ Busca A* com heurística '" + this.getHeuristica().toString() + "' @@@"; }
}
