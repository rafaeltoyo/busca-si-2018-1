package algoritmos.plano;

import algoritmos.busca.BuscaLRTA;
import controller.BuscaController;
import problema.Estado;
import problema.Problema;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlanoLRTA implements Plano
{

    /**
     * Crença de aonde o agente está
     */
    private Estado current;


    private  List<Integer> plan = new ArrayList<>();
    private int chosenNextAction = 0, pathSize = 0;
    private float pathCost = 0;
    private final static Random rand = new Random(System.currentTimeMillis());
    private static List<List<Integer>> optimalPathsFound = new ArrayList<>();
    private static List<Integer> currentPath = new ArrayList<>();
    private static int pathNumber = 0;

    public PlanoLRTA()
    {
        optimalPathsFound.add(new ArrayList<>());
        this.current = BuscaLRTA.getProblema().estIni;
    }

    @Override
    public boolean nextAction()
    {
        int currentAction = 0, actionTestLine = 0, actionTestColumn= 0;
        float minCost = 10000000, actionCost = 0;
        List<Integer> minActionsArray = new ArrayList<>();
        Problema problem = BuscaLRTA.getProblema();
        float [][] problemMemory = BuscaLRTA.getMemory();

        for (int flagAction : problem.acoesPossiveis(this.current)) {
            // Se for possível executar a ação ...
            if (flagAction != -1) {
                actionTestLine = problem.suc(this.current, currentAction).getLin();
                actionTestColumn = problem.suc(this.current, currentAction).getCol();
                actionCost = problem.obterCustoAcao(this.current, currentAction, this.current);
                if (problemMemory[actionTestLine][actionTestColumn] + actionCost < minCost)
                    minCost = problemMemory[actionTestLine][actionTestColumn] + actionCost;
            }
            currentAction++;
        }
        currentAction = 0;
        for (int flagAction : problem.acoesPossiveis(this.current)) {
            // Se for possível executar a ação ...
            if (flagAction != -1) {
                actionTestLine = problem.suc(this.current, currentAction).getLin();
                actionTestColumn = problem.suc(this.current, currentAction).getCol();
                actionCost = problem.obterCustoAcao(this.current, currentAction, this.current);
                if (minCost == problemMemory[actionTestLine][actionTestColumn] + actionCost)
                    minActionsArray.add(currentAction);
            }
            currentAction++;
        }
        chosenNextAction = minActionsArray.get(rand.nextInt(minActionsArray.size()));
        currentPath.add(chosenNextAction);

        BuscaLRTA.getMemory()[this.current.getLin()][this.current.getCol()] = minCost;
        pathCost += BuscaLRTA.getProblema().obterCustoAcao(this.current, chosenNextAction, this.current);
        pathSize += 1;

        // Se: O proximo estado for o objetivo e o peso do caminho for otimo, passa pra proxima lista
        // Caso contrario: Flush na lista atual
        if(problem.testeObjetivo(problem.suc(this.current,chosenNextAction)) && pathCost == 11.5) {
            if(!checkRepeatedPath(currentPath)) {
                optimalPathsFound.get(pathNumber).addAll(currentPath);
                pathNumber += 1;
                optimalPathsFound.add(new ArrayList<>());
            }
            currentPath.clear();
        }
        else if(problem.testeObjetivo(problem.suc(this.current,chosenNextAction)) && pathCost != 11.5){
            currentPath.clear();
        }

        if(pathNumber >= 6){
            System.out.printf("DEU BOOOOOOOOOOOOOOOOOOOOOOOOOOOOOM %d \n", BuscaController.getInstance().bufferBuscas.size());
        }

        return true;
    }

    @Override
    public int getAction()
    {
        plan.add(chosenNextAction);
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


    public List<Integer> getPlan() { return plan; }

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

    private boolean checkRepeatedPath(List<Integer> testList) {
        List<Integer> curOptimalList;
        boolean repeated;
        for(int i = 0; i < optimalPathsFound.size(); i++){
            repeated = true;
            curOptimalList = optimalPathsFound.get(i);
            if(curOptimalList.size() == 0){
                continue;
            }
            for(int j = 0; j < Math.min(curOptimalList.size(), testList.size());j++){
                if(curOptimalList.get(j) != testList.get(j))
                    repeated = false;
            }
            if(repeated == true)
                return true;
        }
        return false;
    }
}
