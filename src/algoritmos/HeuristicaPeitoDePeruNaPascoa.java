package algoritmos;

import problema.Estado;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class HeuristicaPeitoDePeruNaPascoa implements Heuristica
{

    @Override
    public float Hn(Estado current, Estado goal) {
        return (float) sqrt(pow((goal.getLin() - current.getLin()), 2) + pow((goal.getCol() - current.getCol()), 2));
    }
}