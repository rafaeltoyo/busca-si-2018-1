package algoritmos.heuristica;

import problema.Estado;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class HeuristicaEuclidiana implements Heuristica
{

    @Override
    public float Hn(Estado current, Estado goal) {
        return (float) sqrt(pow((goal.getLin() - current.getLin()), 2) + pow((goal.getCol() - current.getCol()), 2));
    }

    @Override
    public String toString() { return "EUCLIDIANA"; }
}