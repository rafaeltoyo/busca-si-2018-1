package controller;

import ambiente.Model;

public class ModelController
{
    private Model model;

    private static ModelController ourInstance = new ModelController();

    public static ModelController getInstance() {
        return ourInstance;
    }

    private ModelController()
    {
        model = null;
    }

    public Model make()
    {
        model = new Model(9, 9);
        model.labir.porParedeVertical(0, 1, 0);
        model.labir.porParedeVertical(0, 0, 1);
        model.labir.porParedeVertical(5, 8, 1);
        model.labir.porParedeVertical(5, 5, 2);
        model.labir.porParedeVertical(8, 8, 2);
        model.labir.porParedeHorizontal(4, 7, 0);
        model.labir.porParedeHorizontal(7, 7, 1);
        model.labir.porParedeHorizontal(3, 5, 2);
        model.labir.porParedeHorizontal(3, 5, 3);
        model.labir.porParedeHorizontal(7, 7, 3);
        model.labir.porParedeVertical(6, 7, 4);
        model.labir.porParedeVertical(5, 6, 5);
        model.labir.porParedeVertical(5, 7, 7);
        // seta a posição inicial do agente no ambiente - nao interfere no
        // raciocinio do agente, somente no amibente simulado
        model.setPos(8, 0);
        model.setObj(2, 8);
        return model;
    }

    public Model getModel() {
        if (model == null) {
            make();
        }
        return model;
    }

}
