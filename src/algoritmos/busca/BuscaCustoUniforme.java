package algoritmos.busca;

import problema.Problema;

public class BuscaCustoUniforme extends BuscaOffline
{
    public BuscaCustoUniforme(Problema problem) { super(problem); }

    @Override
    protected String getTitle() { return "@@@ Busca de custo uniforme @@@"; }
}
