package problema;

public interface Plano
{

    /**
     * Mover para o próximo passo do plano
     * @implSpec retornar true enquanto houver passo e false quando acabar
     * @return
     */
    public boolean nextAction();

    /**
     * @implSpec retornar a ação atual a ser realizada
     * @return
     */
    public int getAction();

    /**
     * @implSpec retornar o custo acumulado até a ação atual (incluindo ela)
     * @return
     */
    public float getCurrentCost();

    /**
     * @implSpec retornar a quantidade total de passos do plano
     * @return
     */
    public int getPlanSize();

}
