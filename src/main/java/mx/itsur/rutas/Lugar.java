package mx.itsur.rutas;
import java.util.Objects;
public class Lugar {
    private final String nombre;
    public Lugar(String nombre) { this.nombre = Objects.requireNonNull(nombre); }
    public String getNombre() { return nombre; }
    @Override public String toString() { return nombre; }
    @Override public boolean equals(Object o) { return o instanceof Lugar l && nombre.equals(l.nombre); }
    @Override public int hashCode() { return nombre.hashCode(); }
}
