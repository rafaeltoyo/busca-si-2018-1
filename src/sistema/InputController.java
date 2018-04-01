package sistema;

import algoritmos.*;

import java.util.Scanner;

public class InputController {

    private static InputController ourInstance = new InputController();

    public static InputController getInstance() {
        return ourInstance;
    }

    private InputController()
    {
    }

    public BuscaCega getBusca(Agente ag)
    {
        int teste = 0;
        Scanner keyboard = new Scanner(System.in);

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
                return new BuscaCustoUni(ag);
            else if (teste == 2)
                return new BuscaEstrela(ag, new HeuristicaManhattan());
            else if (teste == 3)
                return new BuscaEstrela(ag, new HeuristicaPeitoDePeruNaPascoa());

            System.out.println("Opção inválida!");
        }
    }
}
