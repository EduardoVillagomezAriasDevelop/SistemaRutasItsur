package mx.itsur.rutas;
public class Arista {
    private final Lugar destino;
    private final int distancia;
    public Arista(Lugar destino, int distancia) {
        if (distancia <= 0) throw new IllegalArgumentException("La distancia debe ser mayor que cero.");
        this.destino = destino; this.distancia = distancia;
    }
    public Lugar getDestino() { return destino; }
    public int getDistancia() { return distancia; }
}
