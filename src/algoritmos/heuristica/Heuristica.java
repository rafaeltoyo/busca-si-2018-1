package algoritmos.heuristica;

import problema.Estado;

public interface Heuristica
{

    float Hn(Estado current, Estado goal);

    String toString();
}
