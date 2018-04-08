package algoritmos.busca;

import algoritmos.heuristica.Heuristica;
import arvore.TreeNode;
import problema.Problema;

public class BuscaInformada extends BuscaOffline
{

    /**
     * Heurística para cálculo de Hn
     */
    private Heuristica heuristica;

    public BuscaInformada(Problema problem, Heuristica heuristica)
    {
        super(problem);
        this.heuristica = heuristica;
    }

    @Override
    protected String getTitle() { return "@@@ Busca A* com heurística '" + this.heuristica.toString() + "' @@@"; }

    /**
     * Método para atualizar Fn dos nós
     * @param node
     */
    @Override
    protected void updateNode(TreeNode node)
    {
        super.updateNode(node);
        node.setHn(this.heuristica.Hn(node.getState(), this.problem.estObj));
    }

    /**
     * Método para realizar a checagem se um nó é melhor que outro (usado para determinar quem permanecerá na fronteira)
     * @param currentNode
     * @param targetNode
     * @return
     */
    @Override
    protected boolean isBetter(TreeNode currentNode, TreeNode targetNode)
    {
        if (currentNode.getFn() < targetNode.getFn()) {
            return true;
        }
        return false;
    }
}
