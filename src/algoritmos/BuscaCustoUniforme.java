package algoritmos;

import arvore.TreeNode;
import problema.Estado;
import sistema.Agente;
import sun.reflect.generics.tree.Tree;

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

    public int[] getPlano()
    {
        int[] plan = {N,N,N,NE,L,L,L,L,NE,NE,L};
        return plan;
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
