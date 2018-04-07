package algoritmos.busca;

import problema.Plano;

/**
 * Busca: classe base para todas buscas que controem um plano para um agente
 */
public interface Busca
{
    Plano exec();

    void printArvoreBusca();

    void printDesempenho();
}
