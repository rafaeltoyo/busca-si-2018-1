package algoritmos.plano;

import problema.Estado;

public interface Plano
{

    /**
     * Mover para o próximo passo do plano
     * @implSpec retornar true enquanto houver passo e false quando acabar
     * @return boolean
     */
    public boolean nextAction();

    /**
     * @implSpec retornar a ação atual a ser realizada
     * @return int
     */
    public int getAction();

    /**
     * @implSpec retornar o custo acumulado até a ação atual (incluindo ela)
     * @return float
     */
    public float getCurrentCost();

    /**
     * @implSpec retornar a quantidade total de passos do plano
     * @return int
     */
    public int getPlanSize();

    /**
     * @implSpec informação do agente para atualização do estado do sistema
     * @param currentState
     */
    public void updatePlan(Estado currentState);

}
