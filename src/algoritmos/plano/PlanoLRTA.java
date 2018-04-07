package algoritmos.plano;

import algoritmos.busca.BuscaLRTA;
import problema.Estado;

public class PlanoLRTA implements Plano
{

    /**
     * Crença de aonde o agente está
     */
    private Estado current;

    public PlanoLRTA()
    {
        this.current = BuscaLRTA.getProblema().estIni;
    }

    /**
     * Plano infinito, busca gerando soluções que eventualmente podem ser ótimas.
     * @todo Comparar os estados vizinho com o (custo + 1) e mover
     * @return boolean
     */
    @Override
    public boolean nextAction()
    {
        BuscaLRTA.getProblema().acoesPossiveis(this.current);
        // Trabalhar com as ações possíveis
        // ...
        return true;
    }

    /**
     * @todo Retornar a ação que leva ao estado current
     * @return
     */
    @Override
    public int getAction()
    {
        // retornar a ação possível escolhida
        // ...
        return 0;
    }

    /**
     * @todo Salvar e retornar o custo da solução até o momento
     * @return
     */
    @Override
    public float getCurrentCost()
    {
        return 0;
    }

    /**
     * @todo Retornar o tamanho atual do plano
     * @return
     */
    @Override
    public int getPlanSize()
    {
        return 0;
    }

    /**
     * Atualiza o estado atual com base na informação passada pelo Agente
     * @param currentState
     */
    @Override
    public void updatePlan(Estado currentState)
    {
        this.setCurrent(currentState);
    }

    /**
     * Atualizar o plano
     * @param current
     */
    public void setCurrent(Estado current) { this.current = current; }
}
