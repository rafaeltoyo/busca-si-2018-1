package algoritmos;

import arvore.*;
import problema.*;
import sistema.Agente;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

abstract public class BuscaCega implements Busca
{
    // Agente que vai receber o plano gerado pela busca
    private Agente agente;
    // Raiz da árvore de busca
    private TreeNode root;
    // Heurística a ser adotada para cálculo do Hn
    protected Heuristica heuristica;
    // Lista dos estados já explorados
    private final List<Estado> visited = new ArrayList<>();
    // Nó da árvore de busca que contém a solução encontrada
    private TreeNode finalNode;
    // Fronteira da busca
    protected PriorityQueue<TreeNode> frontier;

    // --- Contadores
    private int ct_nos_arvore_busca = 0;
    private int ct_ja_explorados = 0;
    private int ct_descartados_front = 0;

    /* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public BuscaCega(Agente agente, Heuristica heuristica)
    {
        this(agente, heuristica, new PriorityQueue<>(new fnComparator()));
    }

    protected BuscaCega(Agente agente, Heuristica heuristica, PriorityQueue<TreeNode> frontier)
    {
        // Salvar o agente que receberá o plano da busca
        this.agente = agente;
        // Gerar a raiz da árvore da busca
        this.root = new TreeNode();
        this.root.setState(this.agente.getProblem().estIni);
        // Salvar a estrutura da fronteira
        this.frontier = frontier;
        // Salvar a heurística da busca
        this.heuristica = heuristica;
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public void exec() {
        // Adiciona a raiz da árvore de busca na fronteira para iniciar a busca
        this.frontier.add(this.root);
        this.ct_nos_arvore_busca++;

        do {
            // Escolher um nó da fronteira
            TreeNode current = this.getNextNode();

            // Checa se o estado do nó escolhido é o estado final
            if (current.getState().igualAo(this.agente.getProblem().estObj)) {
                // Salvar a solução
                this.finalNode = current;
                return; // fim da busca
            }

            // Explorar o nó escolhido
            this.exploreNode(current);

        } while (this.frontier.size() > 0); // Enquanto a fronteira possuir nós

        System.out.println("Erro ao encontrar o objetivo");
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    protected TreeNode getNextNode() {
        return this.frontier.poll();
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    protected void exploreNode(TreeNode currentNode) {
        TreeNode child;

        // Marcar o nó a ser explorado como já visitado
        this.visited.add(currentNode.getState());

        // Contador para marcar de qual ação se trata a iteração atual
        int currentAction = 0;
        // Iterar sobre as ações possíveis
        for (int flagAction : this.agente.getProblem().acoesPossiveis(currentNode.getState())) {

            // Se for possível executar a ação ...
            if (flagAction != -1) {
                // ... gera o estado sucessivo ao estado atual após a execução dessa ação
                Estado nextState = this.agente.getProblem().suc(currentNode.getState(), currentAction);

                // Criar um nó filho na árvore de busca para esse estado
                child = currentNode.addChild();
                this.ct_nos_arvore_busca++;
                // Salvar o estado referente a esse nó
                child.setState(nextState);
                // Salvar a ação que gerou ele
                child.setAction(currentAction);
                // Calcular Gn e Hn desse nó
                child.setGnHn(
                        currentNode.getGn() + this.agente.getProblem().obterCustoAcao(
                                currentNode.getState(),
                                currentAction,
                                child.getState()),
                        this.heuristica.Hn(nextState, this.agente.getProblem().estObj));
                // Adicionar esse nó a fronteira
                this.addNodeToFrontier(child);
            }
            currentAction++;
        }
    }

    protected void addNodeToFrontier(TreeNode node) {
        // Se o nó faz referência a estado que não foi visitado ainda ele pode entrar na fronteira
        if (this.isVisited(node)) {
            this.ct_ja_explorados++;
            return;
        }

        TreeNode old = null;
        // Procurar um nó que referência o mesmo estado do nó atual
        // (mesmo destino porém possivelmente com outro percurso)
        for (TreeNode frontierNode : this.frontier) {
            if (frontierNode.getState().igualAo(node.getState())) {
                this.ct_descartados_front++;
                // O nó atual possui um custo menor que ao da fronteira?
                if (frontierNode.getFn() > node.getFn()) {
                    old = frontierNode; // retirar o pior da fronteira
                } else {
                    old = node; // nó atual não entra na fronteira
                }
                break;
            }
        }
        // Tem nó para tirar da fronteira?
        if (old !=  null) {
            this.frontier.add(node);
            this.frontier.remove(old);
        } else {
            this.frontier.add(node);
        }
    }

    protected boolean isVisited(TreeNode node) {
        // Percorre a lista de estados já explorados para determinar se o nó já foi visitado
        for (Estado visitedState : this.visited) {
            if (visitedState.igualAo(node.getState()))
                return true;
        }
        return false;
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    @Override
    public void printArvoreBusca() {
        this.root.printSubTree();
    }

    abstract protected String getTitle();

    public void printDesempenho() {
        PlanoBuscaCega plano = this.gerarPlano();
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

    public void setAgentePlan()
    {
        this.agente.setPlan(this.gerarPlano());
    }

    protected PlanoBuscaCega gerarPlano() throws RuntimeException
    {
        PlanoBuscaCega plano = new PlanoBuscaCega();

        if (this.finalNode != null) {

            TreeNode current = this.finalNode;

            while (current != current.getParent()) {
                plano.getPlan().push(current);
                current = current.getParent();
            }
        } else {
            throw new RuntimeException("Busca ainda não executada ou solução não encontrada!");
        }

        return plano;
    }

/* @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ */

    public TreeNode getArvoreBusca() { return this.root; }

}
