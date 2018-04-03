package algoritmos.heuristica;

import problema.Estado;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class HeuristicaColunas implements Heuristica
{

    @Override
    public float Hn(Estado current, Estado goal) {
        return abs(goal.getCol() - current.getCol());
    }

    @Override
    public String toString() { return "COLUNA"; }
}
