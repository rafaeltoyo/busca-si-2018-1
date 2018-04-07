package algoritmos.busca;

import arvore.TreeNode;
import arvore.fnComparator;
import problema.Estado;
import problema.Plano;
import problema.PlanoBuscaOffline;
import problema.Problema;
import sistema.Agente;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

abstract public class BuscaOffline implements Busca
{

    /**
     * Raiz da árvore de busca
     */
    private TreeNode root;

    /**
     * Problema dessa busca
     */
    protected Problema problem;

    /**
     * Lista dos estados já explorados
     */
    private final List<Estado> explored = new ArrayList<>();

    /**
     * Fronteira da busca
     */
    private PriorityQueue<TreeNode> frontier;

    /**
     * Plano gerado
     */
    private PlanoBuscaOffline plano;

    // --- Contadores
    private int ct_nos_arvore_busca = 0;
    private int ct_ja_explorados = 0;
    private int ct_descartados_front = 0;

    // #################################################################################################################

    public BuscaOffline(Problema problem)
    {
        this.plano = null;
        // Salvar o agente que receberá o plano da busca
        this.problem = problem;
        // Gerar a raiz da árvore da busca
        this.root = new TreeNode();
        this.root.setState(this.problem.estIni);
        // Salvar a estrutura da fronteira
        this.initFrontier();
    }

    // #################################################################################################################
    // Métodos para print dos resultados
    // #################################################################################################################

    /**
     * Print da árvore de busca gerada
     */
    @Override
    public void printArvoreBusca() {
        this.root.printSubTree();
    }

    abstract protected String getTitle();

    /**
     * Print do desempenho da busca
     */
    @Override
    public void printDesempenho() {
        PlanoBuscaOffline plano = this.plano;
        float custo = 0;

        System.out.println(this.getTitle());

        System.out.print("Solução gerada: { ");
        while (plano.nextAction()) {
            System.out.print(Agente.acao[plano.getAction()]+" ");
            custo = plano.getCurrentCost();
        }
        System.out.println("} (custo = " + custo + ")");

        System.out.println("--------------------");

        System.out.println("Complexidade temporal: ");
        System.out.println("ct_ja_explorados: " + this.ct_ja_explorados);
        System.out.println("ct_descartados_front: " + this.ct_descartados_front);

        System.out.println("--------------------");

        System.out.println("Complexidade espacial: ");
        System.out.println("Árvore de busca: " + this.ct_nos_arvore_busca);

        System.out.println("--------------------");

        System.out.println("Árvore de Busca gerada: ");
        this.printArvoreBusca();

        System.out.println("--------------------");
    }

    // #################################################################################################################
    // Métodos para inicialização básica de atributos
    // #################################################################################################################

    /**
     * Iniciar a estrutura da fronteira
     */
    protected void initFrontier()
    {
        this.frontier = new PriorityQueue<>(new fnComparator());
    }

    // #################################################################################################################
    // Método Principal
    // #################################################################################################################

    /**
     * Execução da busca, retornando um plano para ser usado
     * @throws RuntimeException
     */
    @Override
    public Plano exec() throws RuntimeException
    {
        // Adiciona a raiz da árvore de busca na fronteira para iniciar a busca
        this.frontier.add(this.root);
        this.ct_nos_arvore_busca++;

        do {
            // Escolher um nó da fronteira
            TreeNode current = this.getNextNode();

            // Checa se o estado do nó escolhido é o estado final
            if (current.getState().igualAo(this.problem.estObj)) {
                // fim da busca
                this.plano = makePlan(current);
                return this.makePlan(current);
            }

            // Explorar o nó escolhido
            this.exploreNode(current);

        } while (this.frontier.size() > 0); // Enquanto a fronteira possuir nós

        throw new RuntimeException("Erro ao encontrar o objetivo");
    }

    // #################################################################################################################
    // Métodos auxiliares de busca
    // #################################################################################################################

    /**
     * Pegar o próximo nó da fronteira
     * @return TreeNode
     */
    protected TreeNode getNextNode()
    {
        return this.frontier.poll();
    }

    /**
     * Explorar o nó passado
     * @param currentNode
     */
    protected void exploreNode(TreeNode currentNode)
    {
        TreeNode child;

        // Marcar o nó a ser explorado como já visitado
        this.explored.add(currentNode.getState());

        // Contador para marcar de qual ação se trata a iteração atual
        int currentAction = 0;

        // Iterar sobre as ações possíveis
        for (int flagAction : this.problem.acoesPossiveis(currentNode.getState())) {

            // Se for possível executar a ação ...
            if (flagAction != -1) {
                // ... gera o estado sucessivo ao estado atual após a execução dessa ação
                Estado nextState = this.problem.suc(currentNode.getState(), currentAction);

                // Criar um nó filho na árvore de busca para esse estado
                child = currentNode.addChild();
                this.ct_nos_arvore_busca++;
                // Salvar o estado referente a esse nó
                child.setState(nextState);
                // Salvar a ação que gerou ele
                child.setAction(currentAction);
                // Calcular Gn e Hn desse nó
                this.updateNode(child);
                // Adicionar esse nó a fronteira
                this.addNodeToFrontier(child);
            }
            currentAction++;
        }
    }

    /**
     * Método para atualizar Fn dos nós
     * @param node
     */
    protected void updateNode(TreeNode node)
    {
        node.setGn(node.getParent().getGn() + this.problem.obterCustoAcao(
                node.getParent().getState(),
                node.getAction(),
                node.getState()));
        node.setHn(0);
    }

    /**
     * Método para tentar adicionar um nó a fronteira
     * @param node
     */
    protected void addNodeToFrontier(TreeNode node)
    {
        // Se o nó faz referência a estado que não foi visitado ainda ele pode entrar na fronteira
        if (this.isExplored(node)) {
            this.ct_ja_explorados++;
            return;
        }

        TreeNode toRemove = null;
        boolean uniqueNode = true;
        // Procurar um nó que referência o mesmo estado do nó atual
        // (mesmo destino porém possivelmente com outro percurso)
        for (TreeNode frontierNode : this.frontier) {
            if (frontierNode.getState().igualAo(node.getState())) {
                this.ct_descartados_front++;
                uniqueNode = false;
                // O nó atual possui um custo menor que ao da fronteira?
                if (this.isBetter(node, frontierNode)) {
                    this.frontier.add(node);
                    toRemove = frontierNode; // retirar o pior da fronteira
                }
                break;
            }
        }
        // Tem nó para tirar da fronteira?
        if (toRemove !=  null) {
            this.frontier.remove(toRemove);
        }
        if (uniqueNode) {
            this.frontier.add(node);
        }
    }

    /**
     * Método para realizar a checagem se um nó é melhor que outro (usado para determinar quem permanecerá na fronteira)
     * @param currentNode
     * @param targetNode
     * @return
     */
    protected boolean isBetter(TreeNode currentNode, TreeNode targetNode)
    {
        if (currentNode.getGn() < targetNode.getGn()) {
            return true;
        }
        return false;
    }

    /**
     * Método para realizar a checagem se um nó já foi explorado
     * @param node
     * @return boolean
     */
    protected boolean isExplored(TreeNode node)
    {
        // Percorre a lista de estados já explorados para determinar se o nó já foi explorado
        for (Estado exploredState : this.explored) {
            if (exploredState.igualAo(node.getState())) return true;
        }
        return false;
    }

    /**
     * Método para gerar um Plano
     * @param finalNode
     * @return PlanoBuscaOffline
     */
    protected PlanoBuscaOffline makePlan(TreeNode finalNode)
    {
        PlanoBuscaOffline plano = new PlanoBuscaOffline();

        while (finalNode != finalNode.getParent()) {
            plano.getPlan().push(finalNode);
            finalNode = finalNode.getParent();
        }
        return plano;
    }
}
