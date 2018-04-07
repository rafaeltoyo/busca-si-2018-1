package algoritmos.busca;

import algoritmos.plano.Plano;
import problema.Problema;

public class BuscaLRTA extends BuscaOnline
{

    /**
     * Memória de labirinto anterior para comparação
     */
    private static int[][] labAnterior;

    /**
     * Problema que está sendo tratado
     */
    private Problema problema;

    public BuscaLRTA(Problema problema)
    {

    }

    /**
     * @implSpec Montar o Labirinto e calcular os Hn dos estados
     * @return
     */
    @Override
    public Plano exec()
    {
        return null;
    }

    @Override
    public void printArvoreBusca()
    {

    }

    @Override
    public void printDesempenho()
    {

    }
}
