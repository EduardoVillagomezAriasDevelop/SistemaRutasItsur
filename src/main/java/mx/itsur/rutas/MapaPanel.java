package mx.itsur.rutas;
import javax.swing.*; import java.awt.*; import java.awt.geom.*; import java.util.*;
public class MapaPanel extends JPanel {
    private final Grafo grafo;
    private java.util.List<Lugar> ruta=java.util.List.of();
    private final Map<String,Point> puntos=new LinkedHashMap<>();
    private final Image logo;
    private final Color teal=new Color(34,211,190), muted=new Color(126,148,170);
    public MapaPanel(Grafo grafo) {
        this.grafo=grafo;
        java.net.URL recurso=getClass().getResource("/mx/itsur/rutas/logo.png");
        logo=recurso==null?null:new ImageIcon(recurso).getImage();
        setBackground(new Color(12,25,43)); setPreferredSize(new Dimension(720,510));
        String[] nombres={"Caseta de vigilancia de la puerta principal","Dirección","Edificio A","Biblioteca","Cafetería","Palapas","Edificio B","Edificio E","Edificio de TICS","Cancha de Fútbol","Canchas de Basketball","Estacionamiento","Caseta de vigilancia del estacionamiento","Edificio de Vinculación","Auditorio de Vinculación","Auditorio Principal","Cocinas de Gastronomía","Cabaña de Gastronomía"};
        int[][] xy={{65,245},{165,245},{265,175},{365,120},{465,175},{565,235},{465,300},{365,365},{265,430},{165,430},{65,365},{65,80},{165,80},{565,350},{650,350},{650,120},{465,65},{565,65}};
        for(int i=0;i<nombres.length;i++) puntos.put(nombres[i],new Point(xy[i][0]+20,xy[i][1]+25));
    }
    public void setRuta(java.util.List<Lugar> ruta){this.ruta=ruta; repaint();}
    private boolean enRuta(String a,String b){for(int i=0;i<ruta.size()-1;i++) if((ruta.get(i).getNombre().equals(a)&&ruta.get(i+1).getNombre().equals(b))||(ruta.get(i).getNombre().equals(b)&&ruta.get(i+1).getNombre().equals(a))) return true; return false;}
    @Override protected void paintComponent(Graphics gg){
        super.paintComponent(gg); Graphics2D g=(Graphics2D)gg.create(); g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(new Color(25,43,63)); g.setStroke(new BasicStroke(1f)); for(int x=0;x<getWidth();x+=32)g.drawLine(x,0,x,getHeight()); for(int y=0;y<getHeight();y+=32)g.drawLine(0,y,getWidth(),y);
        for(Lugar l:grafo.getLugares()) for(Arista a:grafo.getConexiones(l)){String n=l.getNombre(),m=a.getDestino().getNombre(); if(n.compareTo(m)<0){Point p=puntos.get(n),q=puntos.get(m); if(p!=null&&q!=null){g.setColor(enRuta(n,m)?teal:new Color(65,88,111));g.setStroke(new BasicStroke(enRuta(n,m)?4f:1.5f));g.drawLine(p.x,p.y,q.x,q.y);}}}
        for(Lugar l:grafo.getLugares()){
            Point p=puntos.get(l.getNombre());if(p==null)continue; boolean active=ruta.contains(l);
            // Halo turquesa para destacar los puntos que forman parte de la ruta.
            if(active){g.setColor(new Color(34,211,190,65));g.fill(new Ellipse2D.Double(p.x-17,p.y-17,34,34));g.setColor(teal);g.setStroke(new BasicStroke(2f));g.draw(new Ellipse2D.Double(p.x-17,p.y-17,34,34));}
            // El logo oficial de ITSUR se utiliza como marcador de cada vértice.
            if(logo!=null)g.drawImage(logo,p.x-15,p.y-10,30,20,this);
            else {g.setColor(active?teal:new Color(46,75,101));g.fill(new Ellipse2D.Double(p.x-9,p.y-9,18,18));}
            g.setFont(new Font("SansSerif",Font.PLAIN,10));g.setColor(new Color(224,235,245));String label=abreviar(l.getNombre());int w=g.getFontMetrics().stringWidth(label);g.drawString(label,p.x-w/2,p.y+22);
        }
        g.dispose();
    }
    private String abreviar(String s){return switch(s){case "Caseta de vigilancia de la puerta principal"->"Entrada principal";case "Caseta de vigilancia del estacionamiento"->"Caseta estacionamiento";case "Edificio de Vinculación"->"Vinculación";case "Auditorio de Vinculación"->"Aud. Vinculación";case "Auditorio Principal"->"Aud. Principal";case "Canchas de Basketball"->"Basketball";case "Cancha de Fútbol"->"Fútbol";case "Cocinas de Gastronomía"->"Cocinas";case "Cabaña de Gastronomía"->"Cabaña";default->s;};}
}
