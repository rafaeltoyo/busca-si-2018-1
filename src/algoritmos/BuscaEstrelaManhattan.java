package algoritmos;

import arvore.TreeNode;
import sistema.Agente;

public class BuscaEstrelaManhattan extends BuscaCega
{

    public BuscaEstrelaManhattan(Agente agente)
    {
        super(agente, new HeuristicaManhattan());
    }

    @Override
    protected boolean cmpNextNode(TreeNode current, TreeNode next)
    {
        return current.getFn() > next.getFn();
    }
}
