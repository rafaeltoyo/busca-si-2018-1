package algoritmos.heuristica;

import problema.Estado;

public class HeuristicaDummy implements Heuristica
{

    @Override
    public float Hn(Estado current, Estado goal) {
        return 0;
    }

    @Override
    public String toString() { return "DUMMY"; }
}
