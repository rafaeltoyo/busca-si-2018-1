package algoritmos;

import arvore.TreeNode;
import sistema.Agente;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.List;

abstract public class BuscaCega implements Busca
{
    private Agente agente;
    private TreeNode root;
    private final List<TreeNode> fronteira = new ArrayList<>();

    public BuscaCega(Agente agente)
    {
        this.agente = agente;
        this.root = new TreeNode();
        this.root.setState(this.agente.sensorPosicao());
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    /**
     *
     * @implSpec retornar o próximo nó a ser explorado pela Busca
     * @return TreeNode
     */
    abstract protected TreeNode getNextNode();

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public void exec()
    {
        this.root.getChildren();
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public TreeNode getArvoreBusca() { return this.root; }

}
