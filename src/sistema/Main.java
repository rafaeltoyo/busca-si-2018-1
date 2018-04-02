/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema;

import algoritmos.busca.BuscaCega;
import algoritmos.busca.TipoBusca;
import controller.BuscaController;
import controller.ExecController;
import controller.ModelController;
import controller.ViewController;

/**
 *
 * @author tacla
 */
public class Main
{
    public static void main(String args[])
    {
        ModelController.getInstance();
        BuscaController.getInstance();
        ViewController.getInstance();
        ExecController.getInstance();

        runAll();
    }

    public static void runAll()
    {
        ExecController.getInstance().run(TipoBusca.CUSTO_UNI);
        BuscaCega busca1 = BuscaController.getInstance().getBusca();
        ExecController.getInstance().run(TipoBusca.ESTRELA_1);
        BuscaCega busca2 = BuscaController.getInstance().getBusca();
        ExecController.getInstance().run(TipoBusca.ESTRELA_2);
        BuscaCega busca3 = BuscaController.getInstance().getBusca();

        ViewController.printDesempenho(busca1);
        ViewController.printDesempenho(busca2);
        ViewController.printDesempenho(busca3);
        ViewController.printSeparador();
    }

    public static void runInput()
    {
        ExecController.getInstance().run(ViewController.getInstance().printTipoBuscaOptions());

        ViewController.printDesempenho(BuscaController.getInstance().getBusca());
        ViewController.printSeparador();
    }

}
