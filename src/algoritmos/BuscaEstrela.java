package algoritmos;

import arvore.TreeNode;
import problema.Estado;
import sistema.Agente;

import java.util.ArrayList;
import java.util.List;

import static comuns.PontosCardeais.*;
import static java.lang.Math.abs;
import static java.lang.Math.exp;

/**
 * Classe utilizada para construir um plano para o agente baseado em sua crença utilizando a técnica de
 * busca por custo-uniforme
 * @author Rafael
 */
public class BuscaEstrela implements Busca
{

    private Agente agente;
    private TreeNode root;
    private final List<Estado> jaVisitados = new ArrayList<>();
    private int iterator;

    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    /**
     *
     * @param agente {@link Agente}
     */
    public BuscaEstrela(Agente agente)
    {
        this.agente = agente;
        this.root = new TreeNode();
        this.root.setState(this.agente.sensorPosicao());
    }

    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public int[] getPlano() {

        TreeNode child;
        List<Integer> plan = new ArrayList<>();
        List<TreeNode> fronteira = new ArrayList<>();
        TreeNode currentNodeCheck;
        boolean foundSolution = false;
        int minimumCost = 0, counter = 0, objectivePathIndex = 0;
        fronteira.add(this.root);
        jaVisitados.add(this.root.getState());
        while(!foundSolution){
            for(int i = 0; i < fronteira.size(); i++){
                if(fronteira.get(minimumCost).getFn() > fronteira.get(i).getFn())
                    minimumCost = i;
            }
            if(this.agente.getProblem().testeObjetivo(fronteira.get(minimumCost).getState())) {
                foundSolution = true;
                objectivePathIndex = minimumCost;
                System.out.printf("Iterations: %2d \n", counter);
            }
            else {
                fronteira.addAll(expandNodeFromPActions(fronteira.get(minimumCost)));
                jaVisitados.add(fronteira.get(minimumCost).getState());
                fronteira.remove(minimumCost);
                minimumCost = 0;
            }
            counter++;
        }
        currentNodeCheck = fronteira.get(objectivePathIndex);

        while(currentNodeCheck != root){
            plan.add(currentNodeCheck.getAction());
            currentNodeCheck = currentNodeCheck.getParent();
        }

        int [] actionPlan = new int[plan.size()];
        for(int i  = 0; i < plan.size(); i++)
            actionPlan[i] = plan.get(plan.size()-i-1);

        return actionPlan;
    }

    protected List<TreeNode> expandNodeFromPActions(TreeNode currentNode) {
        int acao = 0;
        List<TreeNode> childList = new ArrayList<TreeNode>();
        TreeNode child;
        Estado nextState;
        for (int each : this.agente.getProblem().acoesPossiveis(currentNode.getState())) {
            if (each != -1) {
                nextState = this.agente.getProblem().suc(currentNode.getState(), acao);
                if (!checarEstadoValido(nextState)) {
                    continue;
                }


                child = currentNode.addChild();
                child.setState(nextState);
                child.setAction(acao);
                child.setGnHn(currentNode.getGn() + this.agente.getProblem().obterCustoAcao(currentNode.getState(), acao, child.getState()), getHeuristica(nextState, this.agente.getProblem().estObj ));

                childList.add(child);
            }
            acao++;
        }
        return childList;
    }
    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */



    protected boolean checarEstadoValido(Estado estado)
    {
        for (Estado each : this.jaVisitados) {
            if (each.igualAo(estado)) {
                return false;
            }
        }
        return true;
    }

    public void printArvoreBusca()
    {
        this.root.printSubTree();
    }

    private int getHeuristica(Estado estadoInicial, Estado estadoFinal) { return(abs(estadoFinal.getLin() - estadoInicial.getLin()) + abs(estadoFinal.getCol() - estadoInicial.getCol()));}
}
