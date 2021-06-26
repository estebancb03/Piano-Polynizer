/*
    C01795 - Esteban Castañeda Blanco
    CI0112 - Programación I
    Tarea #1
*/

import java.io.File;
import java.nio.file.*;
import java.io.IOException;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;

public class Sonido {
    private byte[] datos;
    private final BasicPlayer reproductor = new BasicPlayer();

    public Sonido(){
        lectura();
    }
    //Método que lee el archivo binario
    public void lectura(){
        String archivoAudio = "src/Recursos/Avicii - Waiting for love (440 Hz).mp3";
        String archivoBinario = "src/Recursos/Avicii - Waiting for love (440 Hz).poly";
        Path path = Paths.get(archivoBinario);
        try{
            datos = Files.readAllBytes(path);
            reproductor.open(new File(archivoAudio));
        }
        catch(IOException | BasicPlayerException e) {
            System.err.println(e);
        }
    }
    //Getter que devuelve el arreglo datos
    public byte[] getDatos() { return datos; }
    public BasicPlayer getReproductor() { return reproductor; }
}
