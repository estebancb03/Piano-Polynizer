/*
    C01795 - Esteban Castañeda Blanco
    CI0112 - Programación I
    Tarea #1
*/

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graficos extends JFrame{
    //Propiedades
    private final JFrame frame;
    private final int ancho = 8;
    private final int altura = 10;
    private final JButton btnStep;
    private final JButton btnStart;
    private final JButton btnPause;
    private final JButton btnReset;
    private final JPanel panelBase;

    //Constructor
    public Graficos(JFrame frame, JPanel panelBase, JButton btnStart, JButton btnStep, JButton btnPause, JButton btnReset){
        this.frame = frame;
        this.btnStep = btnStep;
        this.btnStart = btnStart;
        this.btnPause = btnPause;
        this.btnReset = btnReset;
        this.panelBase = panelBase;
    }
    //Método que establece las propiedades gráficas del Jframe
    public void instanciaFrame(){
        frame.setVisible(true);
        frame.setBackground(Color.black);
        frame.setResizable(false);
        frame.setTitle("Piano");
        frame.setSize(740,755);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setIconImage(new ImageIcon(getClass().getResource("Recursos/notamusical.png")).getImage());
    }
    //Método que instancia las propiedades gráficas del panel de fondo
    public void instanciaBasico(){
        JLabel imagen = new JLabel();
        frame.add(panelBase);
        panelBase.add(imagen);
        panelBase.setVisible(true);
        panelBase.setBackground(Color.black);
        imagen.setBounds(0, 0, frame.getWidth(),150);
        panelBase.setBounds(0,0,frame.getWidth(), 500);
        ImageIcon pianoImage = new ImageIcon(getClass().getResource("Recursos/piano.png"));
        imagen.setIcon(new ImageIcon(pianoImage.getImage().getScaledInstance(740, 135, Image.SCALE_SMOOTH)));
    }
    //Método que instancia las propiedades gráficas de los botones
    public void instanciaBotones(){
        ArrayList<JButton> listaBotones = new ArrayList<>(4);
        listaBotones.add(btnStart);
        listaBotones.add(btnPause);
        listaBotones.add(btnStep);
        listaBotones.add(btnReset);
        btnStep.setText("Step");
        btnStart.setText("Start");
        btnPause.setText("Pause");
        btnReset.setText("Reset");
        int x = 0;
        //Como se establecen propiedades comunes en todos los botones, se emplea un ciclo para ahorrar lineas de código
        for (JButton btn : listaBotones) {
            frame.add(btn);
            btn.setBorder(null);
            btn.setVisible(true);
            btn.setFocusable(false);
            btn.setBackground(Color.black);
            btn.setForeground(Color.white);
            btn.setBounds(x,675,181,40);
            btn.setFont(new Font("Agency FB", Font.BOLD, 16));
            btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            x += 181;
        }
    }
    //Método que agrega la nota a la matriz y la muestra en pantalla
    public void agregarNota(Nota[][] matriz, int fila, int columna, int x, int y, Color color){
        frame.add(matriz[fila][columna].getPanelNota());
        matriz[fila][columna].getPanelNota().setVisible(true);
        matriz[fila][columna].getPanelNota().setBackground(color);
        matriz[fila][columna].getPanelNota().setBounds(x, y, this.ancho, this.altura);
    }
    //Método que agrega la nota a la matriz y la muestra en pantalla
    public void agregarBarraIntensidad(Nota[] arreglo, int columna, int x, Color color){
        frame.add(arreglo[columna].getPanelNota());
        arreglo[columna].getPanelNota().setVisible(true);
        arreglo[columna].getPanelNota().setBackground(color);
        arreglo[columna].getPanelNota().setBounds(x, 0, this.ancho, 25);
    }
    //Método que devuelve los valores de una nota a como eran en el inicio
    public void resetearNotas(Nota[][] matriz, int fila, int columna, Color color){
        matriz[fila][columna].setColor(color);
        matriz[fila][columna].setIntensidad(0);
        matriz[fila][columna].getPanelNota().setBackground(color);
    }
    //Método que resetea la barra de intensidad
    public void resetearBarraIntensidad(Nota[] arreglo, int columna){
        arreglo[columna].setIntensidad(0);
        arreglo[columna].setColor(Color.black);
        arreglo[columna].getPanelNota().setBackground(Color.black);
        arreglo[columna].getPanelNota().setSize(this.ancho, 25);
        arreglo[columna].getPanelNota().setLocation(arreglo[columna].getPosicionX(), 0);
    }
    //Método que establece un color aleatorio a las notas de la primera fila
    public void cambiarColorNotas(Nota[][] matriz, byte binario, int fila, int columna, boolean tecla){
        Color bg;
        int numero = binario & 0b11111111; //Se le agrega la mascara al número binario
        if(tecla) bg = new Color(240 * numero / 256, 128 * numero /256, 128 * numero / 256);
        else bg = new Color(0 / 256, 191 * numero /256, 255 * numero / 256);
        matriz[fila][columna].setColor(bg);
        matriz[fila][columna].setIntensidad(numero);
        matriz[fila][columna].getPanelNota().setBackground(bg);
    }
    //Metodo que copia la propiedad color de una nota en una posición Y menos
    public void heredaColorNota(Nota[][] matriz, int fila, int columna){
        matriz[fila][columna].setColor(matriz[fila-1][columna].getColor());
        matriz[fila][columna].setIntensidad(matriz[fila-1][columna].getIntensidad());
        matriz[fila][columna].getPanelNota().setBackground(matriz[fila-1][columna].getColor());
    }
    //Método que cambia continuamente la apariencia de la barra de intensidad
    public void manejaIntensidad(Nota[] arreglo, Nota[][] matriz, int columna, int fila) {
        int altura = 40 * matriz[fila][columna].getIntensidad() / 255;
        arreglo[columna].getPanelNota().setSize(ancho, altura);
        arreglo[columna].setColor(matriz[fila][columna].getColor());
        arreglo[columna].setIntensidad(matriz[fila][columna].getIntensidad());
        arreglo[columna].getPanelNota().setBackground(arreglo[columna].getColor());
        arreglo[columna].getPanelNota().setLocation(arreglo[columna].getPosicionX(),arreglo[columna].getPosicionY() - altura);
    }
}
