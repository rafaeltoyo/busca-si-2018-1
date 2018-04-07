package algoritmos.plano;

import arvore.TreeNode;

import java.util.Stack;

public class PlanoBuscaOffline implements Plano
{

    private final Stack<TreeNode> plan = new Stack<>();

    private TreeNode current;

    @Override
    public boolean nextAction() {
        if (plan.size() > 0) {
            current = plan.pop();
            return true;
        }
        return false;
    }

    @Override
    public int getAction() {
        return current.getAction();
    }

    @Override
    public float getCurrentCost() {
        return current.getGn();
    }

    @Override
    public int getPlanSize() {
        return plan.size();
    }

    public Stack<TreeNode> getPlan() {
        return plan;
    }
}
