package controller;

import algoritmos.busca.Busca;
import algoritmos.busca.TipoBusca;
import ambiente.Model;
import comuns.PontosCardeais;
import problema.Estado;

import java.util.Scanner;

public class ViewController
{
    private static ViewController ourInstance = new ViewController();

    public static ViewController getInstance() {
        return ourInstance;
    }

    private ViewController() { }

    public static void desenharLabModel(Model model)
    {
        model.desenhar();
    }

    public static void printSeparador()
    {
        System.out.println("-----------------------------------------------------------");
    }

    public static void printError(String title, Throwable tw)
    {
        System.out.println(title);
        System.out.println("\t" + tw.getMessage());
    }

    public static void printError(Throwable tw)
    {
        printError("Erro: ", tw);
    }

    public static void printEstadoAtual(Estado estado)
    {
        System.out.println("estado atual: " + estado.getLin() + "," + estado.getCol());
    }

    public static void printAcoesPossiveis(int[] acoes)
    {
        System.out.print("açoes possiveis: {");
        for (int i=0; i < acoes.length; i++) {
            if (acoes[i]!=-1)
                System.out.print(PontosCardeais.acao[i]+" ");
        }
        System.out.println("}");
    }

    public static void printDesempenho()
    {
        for (Busca busca: BuscaController.bufferBuscas) {
            printSeparador();
            busca.printDesempenho();
        }
        printSeparador();
    }

    public static TipoBusca printTipoBuscaOptions()
    {
        int teste = 0;
        Scanner keyboard = new Scanner(System.in);

        printSeparador();

        System.out.println("Escolha um algoritmo:");
        System.out.println("[1] Busca de custo uniforme");
        System.out.println("[2] Busca estrela com a heurística 1");
        System.out.println("[3] Busca estrela com a heurística 2");

        while (true) {
            try {
                teste = keyboard.nextInt();
            } catch (Exception e) {
                // nothing
                keyboard.nextLine();
            }

            if (teste == 1)
                return TipoBusca.CUSTO_UNI;
            else if (teste == 2)
                return TipoBusca.ESTRELA_1;
            else if (teste == 3)
                return TipoBusca.ESTRELA_2;

            System.out.println("Opção inválida!");
        }
    }
}
