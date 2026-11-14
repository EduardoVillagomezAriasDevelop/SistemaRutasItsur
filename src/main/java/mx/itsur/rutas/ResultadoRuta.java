package mx.itsur.rutas;
import java.util.List;
public record ResultadoRuta(List<Lugar> camino, int distanciaTotal) {
    public boolean existeRuta() { return !camino.isEmpty(); }
}
