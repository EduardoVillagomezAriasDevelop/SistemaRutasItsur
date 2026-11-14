package mx.itsur.rutas;
/** Datos de demostración. Sustituye estas distancias por mediciones reales del campus. */
public final class MapaITSUR {
    private MapaITSUR() {}
    public static Grafo crear() {
        Grafo g=new Grafo();
        String[] lugares={"Biblioteca","Cafetería","Dirección","Edificio A","Edificio B","Edificio E",
          "Edificio de Vinculación","Palapas","Edificio de TICS","Cancha de Fútbol","Canchas de Basketball",
          "Estacionamiento","Auditorio de Vinculación","Auditorio Principal",
          "Caseta de vigilancia del estacionamiento","Caseta de vigilancia de la puerta principal",
          "Cocinas de Gastronomía","Cabaña de Gastronomía"};
        for(String l:lugares) g.agregarLugar(l);
        // Red de ejemplo conectada; distancias ilustrativas en metros, no mediciones oficiales.
        String[][] c={
          {"Caseta de vigilancia de la puerta principal","Dirección","55"},
          {"Dirección","Edificio A","45"},{"Edificio A","Biblioteca","60"},
          {"Biblioteca","Cafetería","35"},{"Cafetería","Palapas","40"},
          {"Palapas","Edificio B","65"},{"Edificio B","Edificio E","70"},
          {"Edificio E","Edificio de TICS","50"},{"Edificio de TICS","Cancha de Fútbol","85"},
          {"Cancha de Fútbol","Canchas de Basketball","45"},{"Canchas de Basketball","Estacionamiento","75"},
          {"Estacionamiento","Caseta de vigilancia del estacionamiento","30"},
          {"Edificio B","Edificio de Vinculación","55"},{"Edificio de Vinculación","Auditorio de Vinculación","25"},
          {"Auditorio de Vinculación","Auditorio Principal","80"},
          {"Cafetería","Cocinas de Gastronomía","50"},{"Cocinas de Gastronomía","Cabaña de Gastronomía","35"},
          {"Cabaña de Gastronomía","Auditorio Principal","90"},
          {"Dirección","Edificio de Vinculación","95"},{"Biblioteca","Edificio B","80"},
          {"Palapas","Cocinas de Gastronomía","75"},{"Edificio E","Estacionamiento","110"},
          {"Auditorio Principal","Caseta de vigilancia del estacionamiento","130"}
        };
        for(String[] x:c) g.agregarConexion(x[0],x[1],Integer.parseInt(x[2]));
        return g;
    }
}
