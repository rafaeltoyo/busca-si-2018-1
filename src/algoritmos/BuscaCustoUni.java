package algoritmos;

import sistema.Agente;

public class BuscaCustoUni extends BuscaEstrela
{

    public BuscaCustoUni(Agente agente)
    {
        super(agente, new HeuristicaDummy());
    }

    @Override
    protected String getTitle() { return "@@@ Busca de custo uniforme @@@"; }
}
