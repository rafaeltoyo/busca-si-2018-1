package algoritmos.busca;

import algoritmos.heuristica.Heuristica;
import algoritmos.plano.Plano;
import algoritmos.plano.PlanoLRTA;
import problema.Estado;
import problema.Problema;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BuscaLRTA extends BuscaOnline
{

    /**
     * Memória de labirinto anterior para comparação
     */
    private float[][] memory;

    /**
     * Problema que está sendo tratado
     */
    private Problema problema;

    /**
     * Heurística escolhida para essa busca
     */
    private Heuristica heuristica;

    /**
     * Salvar uma referência para o último plano gerado
     */
    private Plano currentPlan;

    public BuscaLRTA(Problema problema, Heuristica heuristica)
    {
        this.problema = problema;
        this.heuristica = heuristica;
        this.memory = new float[problema.crencaLabir.getMaxLin()][problema.crencaLabir.getMaxCol()];
        resetMemory();
    }

    /**
     * Iniciar a memória do Problema com os valores resultantes da Heurística escolhida
     */
    protected void resetMemory()
    {
        for (int lin = 0; lin < problema.crencaLabir.getMaxLin(); lin++) {
            for (int col = 0; col < problema.crencaLabir.getMaxCol(); col++) {
                this.memory[lin][col] = heuristica.Hn(new Estado(lin, col), problema.estObj);
            }
        }
    }

    /**
     * @implSpec Montar o Labirinto e calcular os Hn dos estados
     * @return
     */
    @Override
    public Plano exec()
    {
        this.currentPlan = new PlanoLRTA(this);
        return this.currentPlan;
    }

    @Override
    public void printArvoreBusca()
    {

    }

    @Override
    public void printDesempenho()
    {

    }

    public float[][] getMemory() {
        return memory;
    }

    public Problema getProblema() {
        return problema;
    }

    public Heuristica getHeuristica() {
        return heuristica;
    }

    public Plano getCurrentPlan() {
        return currentPlan;
    }
}
