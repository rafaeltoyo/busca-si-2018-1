package algoritmos;

import arvore.TreeNode;
import sistema.Agente;

public class BuscaCustoUni extends BuscaCega
{

    public BuscaCustoUni(Agente agente)
    {
        super(agente);
    }

    @Override
    protected boolean cmpNextNode(TreeNode current, TreeNode next)
    {
        return current.getGn() > next.getGn();
    }
}
