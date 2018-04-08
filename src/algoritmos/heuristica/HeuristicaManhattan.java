package algoritmos.heuristica;

import problema.Estado;

import static java.lang.Math.abs;

public class HeuristicaManhattan implements Heuristica
{

    @Override
    public float Hn(Estado current, Estado goal) {
        return(abs(goal.getLin() - current.getLin()) + abs(goal.getCol() - current.getCol()));
    }

    @Override
    public String toString() { return "MANHATTAN"; }
}
