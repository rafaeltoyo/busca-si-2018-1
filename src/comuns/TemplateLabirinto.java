package comuns;

import ambiente.Model;
import problema.Problema;

public class TemplateLabirinto
{

    protected static void makeLab1(Labirinto labirinto)
    {
        labirinto.porParedeVertical(0, 1, 0);
        labirinto.porParedeVertical(0, 0, 1);
        labirinto.porParedeVertical(5, 8, 1);
        labirinto.porParedeVertical(5, 5, 2);
        labirinto.porParedeVertical(8, 8, 2);
        labirinto.porParedeHorizontal(4, 7, 0);
        labirinto.porParedeHorizontal(7, 7, 1);
        labirinto.porParedeHorizontal(3, 5, 2);
        labirinto.porParedeHorizontal(3, 5, 3);
        labirinto.porParedeHorizontal(7, 7, 3);
        labirinto.porParedeVertical(6, 7, 4);
        labirinto.porParedeVertical(5, 6, 5);
        labirinto.porParedeVertical(5, 7, 7);
    }

    public static Model makeModelLab1()
    {
        Model model = new Model(9,9);
        makeLab1(model.labir);
        // Estado inicial, objetivo e atual
        model.setPos(8, 0);
        model.setObj(2, 8);
        return model;
    }

    public static Problema makeProblemLab1()
    {
        Problema prob = new Problema();
        prob.criarLabirinto(9, 9);
        makeLab1(prob.crencaLabir);
        // Estado inicial, objetivo e atual
        prob.defEstIni(8, 0);
        prob.defEstObj(2, 8);
        return prob;
    }

    protected static void makeLab2(Labirinto labirinto)
    {
        labirinto.porParedeHorizontal(0, 1, 1);
        labirinto.porParedeHorizontal(7, 8, 0);
        labirinto.porParedeHorizontal(3, 5, 1);
        labirinto.porParedeHorizontal(3, 6, 2);
        labirinto.porParedeHorizontal(3, 5, 3);
        labirinto.porParedeVertical(3, 4, 1);
    }

    public static Model makeModelLab2()
    {
        Model model = new Model(5,9);
        makeLab2(model.labir);
        // Estado inicial, objetivo e atual
        model.setPos(4, 0);
        model.setObj(2, 8);
        return model;
    }

    public static Problema makeProblemLab2()
    {
        Problema prob = new Problema();
        prob.criarLabirinto(5, 9);
        makeLab2(prob.crencaLabir);
        // Estado inicial, objetivo e atual
        prob.defEstIni(4, 0);
        prob.defEstObj(2, 8);
        return prob;
    }

}
