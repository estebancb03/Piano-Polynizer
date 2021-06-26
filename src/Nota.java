/*
    C01795 - Esteban Castañeda Blanco
    CI0112 - Programación I
    Tarea #1
*/

import javax.swing.*;
import java.awt.*;

public class Nota {
    //Propiedades
    private Color color;
    private int posicionX;
    private int posicionY;
    private int intensidad;
    public JPanel panelNota;

    //Constructor
    public Nota(JPanel panelNota, int posicionX, int posicionY, Color color){
        this.panelNota = panelNota;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.intensidad = 0;
        this.color = color;
    }
    //Getters
    public Color getColor() { return this.color; }
    public int getPosicionX() { return this.posicionX; }
    public int getPosicionY() { return this.posicionY; }
    public int getIntensidad() { return this.intensidad; }
    public JPanel getPanelNota() { return this.panelNota; }
    //Setters
    public void setColor(Color color){
        this.color = color;
    }
    public void setIntensidad(int intensidad) { this.intensidad = intensidad; }

}
