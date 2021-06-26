/*
    C01795 - Esteban Castañeda Blanco
    CI0112 - Programación I
    Tarea #1
*/

import java.awt.*;
import javax.swing.*;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Entorno extends JFrame {
    //Propiedades
    private int control = 0;
    private boolean estado = false;
    private final int filas = 55;
    private final int columnas = 88;
    private final int anchoNota = 8;
    private final JPanel panelBase = new JPanel();
    private final JButton btnStep = new JButton();
    private final JButton btnStart = new JButton();
    private final JButton btnPause = new JButton();
    private final JButton btnReset = new JButton();
    private final Nota[] intensidad = new Nota[columnas];
    private final int[] notasNegras = estableceNotasNegras();
    private final Nota[][] notas = new Nota[filas][columnas];

    Sonido Sonido = new Sonido();
    private final byte[] notasBinario = Sonido.getDatos();
    Timer Temporizador = new Timer(20, e -> paso());
    Graficos Graficos = new Graficos(this, panelBase, btnStart, btnStep, btnPause, btnReset);

    //Método constructor
    public Entorno(){
        step();
        pausa();
        inicio();
        reseteo();
        distribuyeNotas();
        distribuyeIntensidad();
        Graficos.instanciaBotones();
        Graficos.instanciaBasico();
        Graficos.instanciaFrame();
    }

    //Método que crea una nota y la inicializa
    public void crearNota(int fila, int columna, int x, int y, Color color) {
        notas[fila][columna] = new Nota(new JPanel(), x, y, color);
        Graficos.agregarNota(notas, fila, columna, x, y, color);
    }
    //Método que llena el frame con notas
    public void distribuyeNotas(){
        int x = 10;
        int y = 663;
        int alturaNota = 10;
        for(int j = 0; j < this.filas; j++){
            for(int i = 0; i < this.columnas; i++){
                crearNota(j, i, x, y, Color.black);
                x += anchoNota;
            }
            x = 10;
            y -= alturaNota;
        }
    }
    //Método que crea la barra de intensidad
    public void distribuyeIntensidad(){
        int x = 10;
        int y = 25;
        for(int i = 0; i < this.columnas; i++){
            intensidad[i] = new Nota(new JPanel(), x, y, Color.black);
            Graficos.agregarBarraIntensidad(intensidad, i, x, Color.black);
            x += anchoNota;
        }
    }
    //Método que devuelve las notas a sus valores iniciales
    public void resetearEntorno() {
        Temporizador.stop();
        this.estado = false;
        this.control = 0;
        for(int j = 0; j < this.filas; j++){
            for(int i = 0; i < this.columnas; i++){
                Graficos.resetearNotas(notas,j,i,Color.black);
                Graficos.resetearBarraIntensidad(intensidad,i);
            }
        }
    }
    //Método que retorna un arreglo con las posiciones de las notas negras
    public int[] estableceNotasNegras() {
        return new int[]{
                1,4,6,9,11,13,16,18,21,23,25,28,30,33,35,37,40,42,45,
                47,49,52,54,57,59,61,64,66,69,71,73,76,78,81,83,85
        };
    }
    //Método que valida que las notas sean o blancas o negras
    public boolean validaNotas(int[] arreglo, int columna){
        boolean respuesta = false;
        for (int i : arreglo) {
            if (i == columna) {
                respuesta = true;
                break;
            }
        }
        return respuesta;
    }
    //Método que cambia el color de las notas progresivamente
    public void paso(){
        for(int i = 0; i < this.columnas; i++) {
            for (int j = this.filas - 1; j > 0; j--) {
                Graficos.heredaColorNota(notas, j, i);
                Graficos.manejaIntensidad(intensidad, notas, i, this.filas - 1);
            }
            try {
                if(this.control >= notasBinario.length){
                    ImageIcon icono = new ImageIcon(getClass().getResource("Recursos/notamusical.png"));
                    JOptionPane.showMessageDialog(null, "The song is over, press start to play again",
                            "Information",JOptionPane.INFORMATION_MESSAGE, icono);
                    resetearEntorno();
                    break;
                }
                else{
                    ++this.control;
                    Graficos.cambiarColorNotas(notas, notasBinario[this.control], 0, i, validaNotas(notasNegras, i));
                }
            }
            catch(Exception ignored) {}
        }
    }
    //Métodos que manejan el uso de botones
    public void step() { btnStep.addActionListener(e -> paso()); }
    public void pausa() {
        btnPause.addActionListener(e -> {
            try {
                Temporizador.stop();
                Sonido.getReproductor().pause();
                estado = true;
            } catch (BasicPlayerException basicPlayerException) {
                basicPlayerException.printStackTrace();
            }
        });
    }
    public void inicio() {
        btnStart.addActionListener(e -> {
            try {
                Temporizador.start();
                if(!estado) Sonido.getReproductor().play();
                else Sonido.getReproductor().resume();
            } catch (BasicPlayerException basicPlayerException) {
                basicPlayerException.printStackTrace();
            }
        });
    }
    public void reseteo(){
        btnReset.addActionListener(e -> {
            try {
                resetearEntorno();
                Sonido.getReproductor().stop();
            } catch (BasicPlayerException basicPlayerException) {
                basicPlayerException.printStackTrace();
            }
        });
    }
}
