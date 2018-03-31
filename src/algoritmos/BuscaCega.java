package algoritmos;

import arvore.TreeNode;
import problema.Estado;
import sistema.Agente;

import java.util.ArrayList;
import java.util.Collection;

abstract public class BuscaCega implements Busca
{
    private Agente agente;
    private TreeNode root;
    private Collection<TreeNode> frontier;
    private Heuristica heuristica;

    public BuscaCega(Agente agente)
    {
        this(agente, new HeuristicaDummy());
    }

    public BuscaCega(Agente agente, Heuristica heuristica)
    {
        this(agente, heuristica, new ArrayList<>());
    }

    protected BuscaCega(Agente agente, Heuristica heuristica, Collection<TreeNode> frontier)
    {
        this.agente = agente;

        this.root = new TreeNode();
        this.root.setState(this.agente.sensorPosicao());

        this.frontier = frontier;
        this.frontier.add(this.root);

        this.heuristica = heuristica;
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    /**
     *
     * @implSpec retornar o próximo nó a ser explorado pela Busca
     * @return TreeNode
     */
    abstract protected boolean cmpNextNode(TreeNode current, TreeNode next);

    protected TreeNode getNextNode() {
        TreeNode nextNode = null;

        for (TreeNode node: this.frontier) {
            if (nextNode == null) {
                nextNode = node;
                continue;
            }

            if (cmpNextNode(nextNode, node))
                nextNode = node;
        }

        this.frontier.remove(nextNode);
        return nextNode;
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    protected void exploreNode(TreeNode currentNode)
    {
        TreeNode child;

        int currentAction = 0;
        for (int flagAction : this.agente.getProblem().acoesPossiveis(currentNode.getState())) {
            if (flagAction != -1) {
                Estado nextState = this.agente.getProblem().suc(currentNode.getState(), currentAction);

                child = currentNode.addChild();
                child.setState(nextState);
                child.setAction(currentAction);
                child.setGnHn(
                        currentNode.getGn() + this.agente.getProblem().obterCustoAcao(
                                currentNode.getState(),
                                currentAction,
                                child.getState()
                        ), this.heuristica.Hn(nextState, this.agente.getProblem().estObj));
            }
        }
    }

    protected void addNodeToFrontier()
    {

    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public TreeNode getArvoreBusca() { return this.root; }

}
