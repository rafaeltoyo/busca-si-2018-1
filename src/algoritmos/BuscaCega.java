package algoritmos;

import arvore.TreeNode;
import arvore.fnComparator;
import problema.Estado;
import sistema.Agente;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

abstract public class BuscaCega implements Busca
{
    private Agente agente;
    private TreeNode root;
    private Heuristica heuristica;
    private final List<Estado> visited = new ArrayList<>();
    private TreeNode finalNode;
    protected PriorityQueue<TreeNode> frontier;

    public BuscaCega(Agente agente)
    {
        this(agente, new HeuristicaDummy());
    }

    public BuscaCega(Agente agente, Heuristica heuristica)
    {
        this(agente, heuristica, new PriorityQueue<>(new fnComparator()));
    }

    protected BuscaCega(Agente agente, Heuristica heuristica, PriorityQueue<TreeNode> frontier)
    {
        this.agente = agente;

        this.root = new TreeNode();
        this.root.setState(this.agente.getProblem().estIni);

        this.frontier = frontier;

        this.heuristica = heuristica;
    }

    public void exec() {
        TreeNode current;

        this.frontier.add(this.root);

        do {

            current = this.getNextNode();

            if (current.getState().igualAo(this.agente.getProblem().estObj)) {
                this.finalNode = current;
                this.printSolucao();
                return;
            }

            this.exploreNode(current);

        } while (this.frontier.size() > 0);

        System.out.println("Erro ao encontrar o objetivo");
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    protected TreeNode getNextNode() {
        return this.frontier.poll();
    }

    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    protected void exploreNode(TreeNode currentNode)
    {
        TreeNode child;
        this.visited.add(currentNode.getState());

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
                                child.getState()),
                        this.heuristica.Hn(nextState, this.agente.getProblem().estObj));
                this.addNodeToFrontier(child);
            }
            currentAction++;
        }
    }

    protected void addNodeToFrontier(TreeNode node)
    {
        if (!this.isVisited(node)) {
            this.frontier.add(node);
        }
    }

    protected boolean isVisited(TreeNode node)
    {
        for (Estado visitedState : this.visited) {
            if (visitedState.igualAo(node.getState()))
                return true;
        }
        return false;
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    @Override
    public void printArvoreBusca()
    {
        this.root.printSubTree();
    }

    public void printSolucao()
    {
        TreeNode current;

        if (this.finalNode == null) {
            System.out.println("----- Solução não encontrada! -----");
        }

        System.out.println("----- SOLUÇÃO -----");
        current = this.finalNode;

        do {
            System.out.println("Nó -> " + current.getState().getString());
            System.out.println("Gn -> " + current.getGn());
            System.out.println("Fn -> " + current.getHn());
            current = current.getParent();
        } while(current != current.getParent());
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public TreeNode getArvoreBusca() { return this.root; }

}
