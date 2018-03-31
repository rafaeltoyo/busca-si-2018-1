package algoritmos;

import arvore.TreeNode;

/**
 * Busca: classe base para todas buscas que controem um plano para um agente
 */
public interface Busca {

    /**
     * @implSpec retornar um array construido pela metodologia de busca
     * @return String[]
     */
    public int[] getPlano();

    public void printArvoreBusca();

}
