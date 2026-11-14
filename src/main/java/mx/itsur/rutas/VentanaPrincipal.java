package mx.itsur.rutas;
import javax.swing.*; import javax.swing.border.*; import java.awt.*; import java.util.*;
public class VentanaPrincipal extends JFrame {
    private final Grafo grafo=MapaITSUR.crear();
    private final JComboBox<String> origen=new JComboBox<>(), destino=new JComboBox<>();
    private final JLabel distancia=new JLabel("—"), estado=new JLabel("Selecciona dos ubicaciones para comenzar");
    private final DefaultListModel<String> pasos=new DefaultListModel<>();
    private final JList<String> listaPasos=new JList<>(pasos);
    private final MapaPanel mapa=new MapaPanel(grafo);
    private final Color bg=new Color(9,18,32), panel=new Color(17,32,51), teal=new Color(34,211,190), white=new Color(235,243,250);
    public VentanaPrincipal(){
        setTitle("Rutas ITSUR | Sistema inteligente");setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(1180,790);setMinimumSize(new Dimension(1000,700));setLocationRelativeTo(null);
        JPanel root=new JPanel(new BorderLayout(18,18));root.setBackground(bg);root.setBorder(new EmptyBorder(22,24,22,24));setContentPane(root);
        root.add(encabezado(),BorderLayout.NORTH);
        JPanel center=new JPanel(new BorderLayout(18,0));center.setOpaque(false);center.add(panelMapa(),BorderLayout.CENTER);center.add(panelLateral(),BorderLayout.EAST);root.add(center,BorderLayout.CENTER);
        root.add(pie(),BorderLayout.SOUTH);
        for(Lugar l:grafo.getLugares()){origen.addItem(l.getNombre());destino.addItem(l.getNombre());}
        origen.setSelectedItem("Caseta de vigilancia de la puerta principal");destino.setSelectedItem("Biblioteca");
        calcular();
    }
    private JPanel encabezado(){
        JPanel p=new JPanel(new BorderLayout());p.setOpaque(false);
        JPanel tit=new JPanel();tit.setOpaque(false);tit.setLayout(new BoxLayout(tit,BoxLayout.Y_AXIS));
        JLabel eyebrow=new JLabel("INSTITUTO TECNOLÓGICO SUPERIOR DEL SUR DE GUANAJUATO");eyebrow.setForeground(teal);eyebrow.setFont(new Font("SansSerif",Font.BOLD,11));
        JLabel h=new JLabel("Sistema de rutas inteligentes");h.setForeground(white);h.setFont(new Font("SansSerif",Font.BOLD,28));
        JLabel sub=new JLabel("Encuentra el camino más corto dentro del campus mediante el algoritmo de Dijkstra.");sub.setForeground(new Color(151,172,193));sub.setFont(new Font("SansSerif",Font.PLAIN,13));
        tit.add(eyebrow);tit.add(Box.createVerticalStrut(7));tit.add(h);tit.add(Box.createVerticalStrut(5));tit.add(sub);p.add(tit,BorderLayout.WEST);
        JLabel tag=new JLabel("  DIJKSTRA  ");tag.setOpaque(true);tag.setBackground(new Color(20,63,69));tag.setForeground(teal);tag.setFont(new Font("SansSerif",Font.BOLD,12));tag.setBorder(new EmptyBorder(9,12,9,12));p.add(tag,BorderLayout.EAST);return p;
    }
    private JPanel panelMapa(){
        JPanel p=card();p.setLayout(new BorderLayout(0,10));JPanel top=new JPanel(new BorderLayout());top.setOpaque(false);
        JLabel t=label("Mapa del campus",18,true);JLabel hint=label("Esquema ilustrativo · La ruta calculada se resalta en turquesa",11,false);hint.setForeground(new Color(145,166,187));
        top.add(t,BorderLayout.NORTH);top.add(hint,BorderLayout.SOUTH);p.add(top,BorderLayout.NORTH);p.add(mapa,BorderLayout.CENTER);return p;
    }
    private JPanel panelLateral(){
        JPanel wrap=new JPanel();wrap.setOpaque(false);wrap.setPreferredSize(new Dimension(330,0));wrap.setLayout(new BoxLayout(wrap,BoxLayout.Y_AXIS));
        JPanel selector=card();selector.setLayout(new BoxLayout(selector,BoxLayout.Y_AXIS));selector.add(label("Planifica tu recorrido",17,true));selector.add(Box.createVerticalStrut(18));
        selector.add(label("PUNTO DE ORIGEN",10,true));selector.add(Box.createVerticalStrut(6));estilizar(origen);selector.add(origen);selector.add(Box.createVerticalStrut(14));
        selector.add(label("PUNTO DE DESTINO",10,true));selector.add(Box.createVerticalStrut(6));estilizar(destino);selector.add(destino);selector.add(Box.createVerticalStrut(17));
        JButton btn=new JButton("⌁  CALCULAR RUTA MÁS CORTA");btn.setFocusPainted(false);btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));btn.setBackground(teal);btn.setForeground(new Color(7,25,34));btn.setFont(new Font("SansSerif",Font.BOLD,12));btn.setBorder(new EmptyBorder(13,10,13,10));btn.setAlignmentX(Component.LEFT_ALIGNMENT);btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,46));btn.addActionListener(e->calcular());selector.add(btn);
        wrap.add(selector);wrap.add(Box.createVerticalStrut(14));
        JPanel result=card();result.setLayout(new BoxLayout(result,BoxLayout.Y_AXIS));result.add(label("DISTANCIA TOTAL",10,true));distancia.setForeground(teal);distancia.setFont(new Font("SansSerif",Font.BOLD,34));result.add(Box.createVerticalStrut(5));result.add(distancia);result.add(label("metros",11,false));result.add(Box.createVerticalStrut(10));estado.setForeground(new Color(165,187,205));estado.setFont(new Font("SansSerif",Font.PLAIN,12));result.add(estado);wrap.add(result);wrap.add(Box.createVerticalStrut(14));
        JPanel route=card();route.setLayout(new BorderLayout(0,9));route.add(label("Pasos del recorrido",16,true),BorderLayout.NORTH);listaPasos.setBackground(panel);listaPasos.setForeground(white);listaPasos.setFont(new Font("SansSerif",Font.PLAIN,12));listaPasos.setFixedCellHeight(29);JScrollPane sc=new JScrollPane(listaPasos);sc.setBorder(BorderFactory.createEmptyBorder());sc.getViewport().setBackground(panel);route.add(sc,BorderLayout.CENTER);route.setPreferredSize(new Dimension(320,230));route.setMaximumSize(new Dimension(Integer.MAX_VALUE,260));wrap.add(route);
        wrap.add(Box.createVerticalStrut(12));JButton conexiones=new JButton("Ver conexiones registradas");conexiones.setFocusPainted(false);conexiones.setBackground(new Color(31,51,72));conexiones.setForeground(white);conexiones.setBorder(new EmptyBorder(11,12,11,12));conexiones.setAlignmentX(Component.LEFT_ALIGNMENT);conexiones.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));conexiones.addActionListener(e->mostrarConexiones());wrap.add(conexiones);
        JButton pruebas=new JButton("Ejecutar 5 pruebas");pruebas.setFocusPainted(false);pruebas.setBackground(new Color(31,51,72));pruebas.setForeground(white);pruebas.setBorder(new EmptyBorder(11,12,11,12));pruebas.setAlignmentX(Component.LEFT_ALIGNMENT);pruebas.setMaximumSize(new Dimension(Integer.MAX_VALUE,42));pruebas.addActionListener(e->ejecutarPruebas());wrap.add(Box.createVerticalStrut(8));wrap.add(pruebas);
        return wrap;
    }
    private JPanel pie(){JPanel p=new JPanel(new BorderLayout());p.setOpaque(false);JLabel l=label("ITSUR  •  Estructuras de datos  •  Grafo ponderado no dirigido",10,false);l.setForeground(new Color(107,132,155));p.add(l,BorderLayout.WEST);JLabel r=label("18 ubicaciones  |  Distancias en metros",10,false);r.setForeground(new Color(107,132,155));p.add(r,BorderLayout.EAST);return p;}
    private JPanel card(){JPanel p=new JPanel();p.setBackground(panel);p.setBorder(new CompoundBorder(new LineBorder(new Color(37,58,79),1,true),new EmptyBorder(17,17,17,17)));p.setAlignmentX(Component.LEFT_ALIGNMENT);return p;}
    private JLabel label(String s,int size,boolean bold){JLabel l=new JLabel(s);l.setForeground(white);l.setFont(new Font("SansSerif",bold?Font.BOLD:Font.PLAIN,size));return l;}
    private void estilizar(JComboBox<String> c){c.setBackground(new Color(27,47,68));c.setForeground(white);c.setFont(new Font("SansSerif",Font.PLAIN,12));c.setMaximumSize(new Dimension(Integer.MAX_VALUE,39));c.setAlignmentX(Component.LEFT_ALIGNMENT);}
    private void calcular(){
        String a=(String)origen.getSelectedItem(),b=(String)destino.getSelectedItem();if(a==null||b==null)return;
        if(a.equals(b)){ResultadoRuta r=grafo.dijkstra(a,b);mapa.setRuta(r.camino());distancia.setText("0");estado.setText("Ya te encuentras en el destino.");pasos.clear();pasos.addElement("1. "+a);return;}
        ResultadoRuta r=grafo.dijkstra(a,b);pasos.clear();
        if(!r.existeRuta()){distancia.setText("—");estado.setText("No existe una ruta disponible.");mapa.setRuta(java.util.List.of());return;}
        distancia.setText(String.valueOf(r.distanciaTotal()));estado.setText("Ruta más corta encontrada correctamente.");int i=1;for(Lugar l:r.camino())pasos.addElement((i++)+".  "+l.getNombre());mapa.setRuta(r.camino());
    }
    private void mostrarConexiones(){
        StringBuilder s=new StringBuilder("CONEXIONES REGISTRADAS\n\n");Set<String> vistas=new HashSet<>();
        for(Lugar l:grafo.getLugares())for(Arista a:grafo.getConexiones(l)){String k=l.getNombre().compareTo(a.getDestino().getNombre())<0?l.getNombre()+"|"+a.getDestino().getNombre():a.getDestino().getNombre()+"|"+l.getNombre();if(vistas.add(k))s.append(l.getNombre()).append("  ↔  ").append(a.getDestino().getNombre()).append("   ").append(a.getDistancia()).append(" m\n");}
        JTextArea area=new JTextArea(s.toString(),22,48);area.setEditable(false);area.setBackground(panel);area.setForeground(white);area.setFont(new Font("Monospaced",Font.PLAIN,12));JScrollPane sp=new JScrollPane(area);JOptionPane.showMessageDialog(this,sp,"Conexiones del grafo",JOptionPane.INFORMATION_MESSAGE);
    }
    private void ejecutarPruebas(){
        String[][] casos={{"Caseta de vigilancia de la puerta principal","Biblioteca"},{"Biblioteca","Cancha de Fútbol"},{"Cafetería","Auditorio Principal"},{"Estacionamiento","Dirección"},{"Cocinas de Gastronomía","Edificio de TICS"}};
        StringBuilder s=new StringBuilder("RESULTADOS DE LAS 5 PRUEBAS\n\n");int ok=0;
        for(int i=0;i<casos.length;i++){ResultadoRuta r=grafo.dijkstra(casos[i][0],casos[i][1]);boolean bien=r.existeRuta();if(bien)ok++;s.append("Prueba ").append(i+1).append(": ").append(casos[i][0]).append(" → ").append(casos[i][1]).append("\n").append(bien?"Correcta · "+r.distanciaTotal()+" m":"Sin ruta").append("\n\n");}
        s.append("Resultado: ").append(ok).append("/5 pruebas con ruta encontrada.");JOptionPane.showMessageDialog(this,s.toString(),"Pruebas del sistema",JOptionPane.INFORMATION_MESSAGE);
    }
}
