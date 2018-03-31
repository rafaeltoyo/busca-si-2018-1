package algoritmos;

import arvore.TreeNode;
import problema.Estado;
import sistema.Agente;

import java.util.ArrayList;
import java.util.List;

import static comuns.PontosCardeais.*;
import static java.lang.Math.abs;

/**
 * Classe utilizada para construir um plano para o agente baseado em sua crença utilizando a técnica de
 * busca por custo-uniforme
 * @author Rafael
 */
public class BuscaCustoUniforme implements Busca
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
    public BuscaCustoUniforme(Agente agente)
    {
        this.agente = agente;
        this.root = new TreeNode();
        this.root.setState(this.agente.sensorPosicao());
        this.adicionarProximoNivel(this.root);
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public int[] getPlano() {

        List<Integer> plan = new ArrayList<>();
        List<TreeNode> fronteira = new ArrayList<>();
        TreeNode currentNodeCheck;
        boolean foundSolution = false;
        int minimumCost = 0, objectivePathIndex = 0;
        fronteira.addAll(this.root.getChildren());
        while(!foundSolution){
            for(int i = 0; i < fronteira.size(); i++){
                if(fronteira.get(minimumCost).getGn() > fronteira.get(i).getGn())
                    minimumCost = i;
            }
            fronteira.addAll(fronteira.get(minimumCost).getChildren());
            fronteira.remove(minimumCost);
            if(this.agente.getProblem().testeObjetivo(fronteira.get(minimumCost).getState())) {
                foundSolution = true;
                objectivePathIndex = minimumCost;
                System.out.printf("Col = %2d || Lin = %2d || Price = %f \n", fronteira.get(minimumCost).getState().getCol(),fronteira.get(minimumCost).getState().getLin(), fronteira.get(minimumCost).getGn());
            }
            minimumCost = 0 ;
        }
        currentNodeCheck = fronteira.get(minimumCost);
        plan.add(currentNodeCheck.getAction());
        while(currentNodeCheck != root){
            plan.add(currentNodeCheck.getParent().getAction());
            System.out.println(currentNodeCheck.getParent().getAction());
            currentNodeCheck = currentNodeCheck.getParent();
        }

        int [] actionPlan = new int[plan.size()];
        for(int i  = 0; i < plan.size(); i++){
            actionPlan[i] = plan.get(plan.size()-i-1);
        }
        return actionPlan;
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    protected void adicionarProximoNivel(TreeNode currentNode)
    {
        TreeNode child;

        this.jaVisitados.add(currentNode.getState());
        this.agente.getProblem().acoesPossiveis(currentNode.getState());

        int acao = 0;
        for (int each : this.agente.getProblem().acoesPossiveis(currentNode.getState())) {
            if (each != -1) {
                Estado nextState = this.agente.getProblem().suc(currentNode.getState(), acao);

                if (!this.checarEstadoValido(nextState)) {
                    continue;
                }

                child = currentNode.addChild();
                child.setState(nextState);
                child.setAction(acao);
                child.setGnHn(currentNode.getGn() + this.agente.getProblem().obterCustoAcao(currentNode.getState(), acao, child.getState()),0 );

                this.adicionarProximoNivel(child);
            }
            acao++;
        }
    }

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
