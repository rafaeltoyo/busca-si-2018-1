package algoritmos.plano;

import algoritmos.busca.BuscaLRTA;
import problema.Estado;

import java.util.Random;

public class PlanoLRTA implements Plano
{

    /**
     * Crença de aonde o agente está
     */
    private Estado current;

    private int chosenNextAction = 0, pathSize = 0;
    private float pathCost = 0;
    private final static Random rand = new Random(System.currentTimeMillis());

    public PlanoLRTA()
    {
        this.current = BuscaLRTA.getProblema().estIni;
    }

    @Override
    public boolean nextAction()
    {
        int currentAction = 0, chosenAction = 0;
        float minCost = 10000000;

        for (int flagAction : BuscaLRTA.getProblema().acoesPossiveis(this.current)) {
            // Se for possível executar a ação ...
            if (flagAction != -1) {
                if (BuscaLRTA.getMemory()[BuscaLRTA.getProblema().suc(this.current, currentAction).getLin()][BuscaLRTA.getProblema().suc(this.current, currentAction).getCol()] + BuscaLRTA.getProblema().obterCustoAcao(this.current, currentAction, this.current) < minCost) {
                    minCost = BuscaLRTA.getMemory()[BuscaLRTA.getProblema().suc(this.current, currentAction).getLin()][BuscaLRTA.getProblema().suc(this.current, currentAction).getCol()] + BuscaLRTA.getProblema().obterCustoAcao(this.current, currentAction, this.current);
                    chosenAction = currentAction;
                }
                if (BuscaLRTA.getMemory()[BuscaLRTA.getProblema().suc(this.current, currentAction).getLin()][BuscaLRTA.getProblema().suc(this.current, currentAction).getCol()] + BuscaLRTA.getProblema().obterCustoAcao(this.current, currentAction, this.current) == minCost) {
                    if (rand.nextInt(100) + 1 >= 50) {
                        minCost = BuscaLRTA.getMemory()[BuscaLRTA.getProblema().suc(this.current, currentAction).getLin()][BuscaLRTA.getProblema().suc(this.current, currentAction).getCol()] + BuscaLRTA.getProblema().obterCustoAcao(this.current, currentAction, this.current);
                        chosenAction = currentAction;
                    }
                }
            }
            currentAction++;
        }
        BuscaLRTA.getMemory()[this.current.getLin()][this.current.getCol()] = minCost;
        chosenNextAction = chosenAction;
        pathCost += BuscaLRTA.getProblema().obterCustoAcao(this.current, chosenNextAction, this.current);
        pathSize += 1;
        return true;
    }

    @Override
    public int getAction()
    {
        return chosenNextAction;
    }


    @Override
    public float getCurrentCost()
    {
        return pathCost;
    }


    @Override
    public int getPlanSize()
    {
        return pathSize;
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
