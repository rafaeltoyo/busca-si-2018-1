package algoritmos;

import problema.Estado;

public class HeuristicaDummy implements Heuristica
{

    @Override
    public float Hn(Estado current, Estado goal) {
        return 0;
    }
}
