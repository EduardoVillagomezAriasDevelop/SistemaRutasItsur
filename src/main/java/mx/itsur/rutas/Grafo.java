package mx.itsur.rutas;
import java.util.*;
public class Grafo {
    private final Map<Lugar, List<Arista>> adyacencias = new LinkedHashMap<>();
    public Lugar agregarLugar(String nombre) {
        Lugar lugar = new Lugar(nombre);
        adyacencias.putIfAbsent(lugar, new ArrayList<>());
        return lugar;
    }
    public void agregarConexion(String origen, String destino, int metros) {
        Lugar a = buscarLugar(origen), b = buscarLugar(destino);
        if (a == null || b == null) throw new IllegalArgumentException("Lugar no registrado.");
        adyacencias.get(a).add(new Arista(b, metros));
        adyacencias.get(b).add(new Arista(a, metros));
    }
    public Lugar buscarLugar(String nombre) {
        for (Lugar l : adyacencias.keySet()) if (l.getNombre().equals(nombre)) return l;
        return null;
    }
    public List<Lugar> getLugares() { return new ArrayList<>(adyacencias.keySet()); }
    public Map<Lugar,List<Arista>> getAdyacencias() { return Collections.unmodifiableMap(adyacencias); }
    public List<Arista> getConexiones(Lugar lugar) {
        return Collections.unmodifiableList(adyacencias.getOrDefault(lugar, List.of()));
    }
    public ResultadoRuta dijkstra(String origen, String destino) {
        Lugar inicio=buscarLugar(origen), fin=buscarLugar(destino);
        if (inicio==null || fin==null) return new ResultadoRuta(List.of(),0);
        Map<Lugar,Integer> dist=new HashMap<>();
        Map<Lugar,Lugar> previo=new HashMap<>();
        PriorityQueue<Lugar> cola=new PriorityQueue<>(Comparator.comparingInt(l -> dist.getOrDefault(l,Integer.MAX_VALUE)));
        for (Lugar l: adyacencias.keySet()) dist.put(l,Integer.MAX_VALUE);
        dist.put(inicio,0); cola.add(inicio);
        Set<Lugar> visitados=new HashSet<>();
        while(!cola.isEmpty()) {
            Lugar actual=cola.poll();
            if(!visitados.add(actual)) continue;
            if(actual.equals(fin)) break;
            for(Arista arista: adyacencias.get(actual)) {
                Lugar vecino=arista.getDestino();
                if(visitados.contains(vecino)) continue;
                int nueva=dist.get(actual)+arista.getDistancia();
                if(nueva<dist.get(vecino)) {
                    dist.put(vecino,nueva); previo.put(vecino,actual);
                    cola.remove(vecino); cola.add(vecino);
                }
            }
        }
        if(dist.get(fin)==Integer.MAX_VALUE) return new ResultadoRuta(List.of(),0);
        LinkedList<Lugar> camino=new LinkedList<>();
        for(Lugar p=fin;p!=null;p=previo.get(p)) camino.addFirst(p);
        return new ResultadoRuta(camino,dist.get(fin));
    }
}
