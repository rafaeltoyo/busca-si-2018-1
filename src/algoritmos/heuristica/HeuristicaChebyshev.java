package algoritmos.heuristica;

import problema.Estado;

import static java.lang.Math.abs;
import static java.lang.Math.max;

public class HeuristicaChebyshev implements Heuristica
{

    @Override
    public float Hn(Estado current, Estado goal) {
        return max(abs(goal.getCol() - current.getCol()), abs(goal.getLin() - current.getLin()));
    }

    @Override
    public String toString() { return "CHEBYSHEV"; }
}
