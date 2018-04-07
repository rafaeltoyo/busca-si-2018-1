package sistema;

import algoritmos.busca.Busca;
import algoritmos.plano.Plano;
import ambiente.*;
import controller.ViewController;
import problema.*;
import comuns.*;

/**
 *
 * @author tacla
 */
public class Agente implements PontosCardeais {
    /* referência ao ambiente para poder atuar no mesmo*/
    Model model;
    Problema prob;
    Estado estAtu; // guarda o estado atual (posição atual do agente)
    Busca busca;
    Plano plan;

    double custo;
    int ct = -1;
           
    public Agente(Model m) {
        this(m, TemplateLabirinto.makeProblemLab1());
    }

    public Agente(Model m, Problema p)
    {
        this.model = m;
        this.prob = p;
        this.estAtu = prob.estIni;
        this.custo = 0;
    }
    
    /**Escolhe qual ação (UMA E SOMENTE UMA) será executada em um ciclo de raciocínio
     * @return 1 enquanto o plano não acabar; -1 quando acabar
     */
    public int deliberar() {
        // Primeira iteração do deliberar execura a busca para resgatar o plano do agente
        if (++ct == 0) plan = busca.exec();

        int[] ap = prob.acoesPossiveis(estAtu);
        // Não atingiu objetivo e há acoesPossiveis a serem executadas no plano
        if (!prob.testeObjetivo(estAtu) && plan.nextAction()) {

            // Estado atual
            ViewController.printEstadoAtual(estAtu);

            // Ações possíveis
            ViewController.printAcoesPossiveis(ap);

            // Executar a próxima ação do plano
            executarIr(plan.getAction());

            // Atualiza o custo até o momento
            custo = plan.getCurrentCost();

            ViewController.printSeparador();
            System.out.println("ct = " + ct + " de " + plan.getPlanSize());
            System.out.println("Ação escolhida = " + acao[plan.getAction()]);
            System.out.println("Custo ate o momento: " + custo);
            ViewController.printSeparador();

            // atualiza estado atual - sabendo que o ambiente é deterministico
            estAtu = prob.suc(estAtu, plan.getAction());

        } else {
            return (-1);
        }
        
        return 1;
    }
    
    /**Funciona como um driver ou um atuador: envia o comando para
     * agente físico ou simulado (no nosso caso, simulado)
     * @param direcao N NE S SE ...
     * @return 1 se ok ou -1 se falha
     */
    public int executarIr(int direcao) {
        if (direcao >= 0 && direcao <=7) {
            model.ir(direcao);
            return 1;
        }
        return -1;
    }
    
    // Sensor
    public Estado sensorPosicao() {
        int pos[];
        pos = model.lerPos();
        return new Estado(pos[0], pos[1]);
    }

    public Problema getProblem() { return this.prob; }

    public Model getModel() { return this.model; }

    public Busca getBusca() {
        return busca;
    }

    public void setBusca(Busca busca) {
        this.busca = busca;
    }
}
    

