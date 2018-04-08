package algoritmos.busca;

import algoritmos.heuristica.Heuristica;
import algoritmos.plano.Plano;
import algoritmos.plano.PlanoLRTA;
import problema.Estado;
import problema.Problema;

import java.io.*;

public class BuscaLRTA extends BuscaOnline
{

    /**
     * Memória de labirinto anterior para comparação
     */
    private static float[][] memory;

    /**
     * Problema que está sendo tratado
     */
    private static Problema problema;

    /**
     * Heurística escolhida para essa busca
     */
    private static Heuristica heuristica;

    /**
     * Salvar uma referência para o último plano gerado
     */
    private Plano currentPlan;

    public BuscaLRTA() { }

    /**
     * Iniciar a memória do Problema com os valores resultantes da Heurística escolhida
     */
    public static void resetMemory(Problema problema, Heuristica heuristica)
    {
        BuscaLRTA.problema = problema;
        BuscaLRTA.heuristica = heuristica;
        BuscaLRTA.memory = new float[problema.crencaLabir.getMaxLin()][problema.crencaLabir.getMaxCol()];
        for (int lin = 0; lin < problema.crencaLabir.getMaxLin(); lin++) {
            for (int col = 0; col < problema.crencaLabir.getMaxCol(); col++) {
                BuscaLRTA.memory[lin][col] = heuristica.Hn(new Estado(lin, col), problema.estObj);
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
        this.currentPlan = new PlanoLRTA();
        return this.currentPlan;
    }

    @Override
    public void printArvoreBusca()
    {

    }

    @Override
    public void printDesempenho()
    {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            String data = String.valueOf(11.5/getCurrentPlan().getCurrentCost()) + System.getProperty("line.separator");

            File file = new File("Data.txt");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(data);
        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }

    public static float[][] getMemory() { return BuscaLRTA.memory; }

    public static Problema getProblema() { return BuscaLRTA.problema; }

    public static Heuristica getHeuristica() { return BuscaLRTA.heuristica; }

    public Plano getCurrentPlan() { return currentPlan; }

}
