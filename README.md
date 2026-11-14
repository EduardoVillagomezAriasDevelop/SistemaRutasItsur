# Sistema de rutas inteligentes ITSUR

Aplicación de escritorio Java Swing que modela el campus como un grafo ponderado no dirigido y calcula rutas mínimas con Dijkstra.

## Requisitos
- JDK 17 o superior.
- Apache NetBeans con soporte para proyectos Maven.

## Abrir en NetBeans
1. Descomprime `SistemaRutasITSUR.zip`.
2. Abre NetBeans.
3. Selecciona **File > Open Project**.
4. Elige la carpeta `SistemaRutasITSUR` (la que contiene `pom.xml`).
5. Espera a que NetBeans termine de cargar el proyecto.
6. Abre `src/main/java/mx/itsur/rutas/Main.java`.
7. Clic derecho en el proyecto > **Run** (o presiona F6).

## Ejecutar desde terminal
Desde la carpeta del proyecto:
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="mx.itsur.rutas.Main"
```
Si no tienes el plugin exec configurado, compila desde NetBeans con Run Project (F6). También puedes ejecutar:
```bash
javac -d target/classes src/main/java/mx/itsur/rutas/*.java
java -cp target/classes mx.itsur.rutas.Main
```

## Qué incluye
- `Lugar`: representa cada vértice.
- `Arista`: representa una conexión y su distancia en metros.
- `Grafo`: listas de adyacencia y algoritmo de Dijkstra.
- `ResultadoRuta`: contiene el camino y la distancia total.
- `MapaITSUR`: crea los 18 lugares y conexiones de demostración.
- `MapaPanel`: dibuja el grafo y resalta la ruta calculada.
- `VentanaPrincipal`: interfaz gráfica, selectores, resultados, conexiones y cinco pruebas.
- `Main`: punto de entrada.

## Importante: distancias y mapa
El documento del proyecto enumera los lugares y pide que los alumnos elaboren el mapa, definan las conexiones y midan/asignen distancias. No proporciona un plano oficial ni distancias reales. Por eso, esta versión usa **conexiones y distancias ilustrativas** para que la aplicación funcione desde el primer inicio. El mapa visual es esquemático, no representa la escala ni la distribución oficial del campus. Antes de entregar, sustituyan las conexiones y metros del método `MapaITSUR.crear()` por los datos levantados por su equipo.

## Cambiar conexiones o distancias
Abre `MapaITSUR.java`. Cada conexión tiene este formato:
```java
{"Biblioteca", "Cafetería", "35"}
```
Cambia los nombres o el número de metros. Las conexiones son bidireccionales. Todos los nombres deben coincidir exactamente con los lugares registrados.

Rama destinada al desarrollo de la estructura del grafo del ITSUR.
- Representación gráfica de la ruta.

## Evidencias de GitHub
El documento también solicita repositorio, al menos tres ramas, commits individuales y pull requests. Eso se realiza desde Git/GitHub y no se crea automáticamente al ejecutar la aplicación.
