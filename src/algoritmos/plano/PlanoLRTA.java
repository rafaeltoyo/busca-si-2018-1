package algoritmos.plano;

import problema.Estado;

public class PlanoLRTA implements Plano
{

    private Estado current;

    /**
     * Memória de controle da busca
     */
    private int[][] labAtual;

    public PlanoLRTA()
    {
    }

    /**
     * Plano infinito, busca gerando soluções que eventualmente podem ser ótimas.
     * @todo Comparar os estados vizinho com o (custo + 1) e mover
     * @return boolean
     */
    @Override
    public boolean nextAction()
    {
        this.current = null;
        return true;
    }

    /**
     * @todo Retornar a ação que leva ao estado current
     * @return
     */
    @Override
    public int getAction()
    {
        return 0;
    }

    @Override
    public float getCurrentCost()
    {
        return 0;
    }

    @Override
    public int getPlanSize()
    {
        return 0;
    }
}
