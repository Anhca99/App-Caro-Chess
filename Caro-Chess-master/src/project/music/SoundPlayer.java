/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.music;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author buiphukhuyen
 */
public class SoundPlayer {
    
    private FileInputStream FIS  ; 
    private Player myPlayer ; 
  
    public void playSound(String path ) { 
        try {                 
            FIS = new FileInputStream(path) ;  
            myPlayer = new Player(FIS) ; 
           
            new Thread (
                new Thread() {
                @Override
                public void run() {
                    try {
                        myPlayer.play();
                    }
                    catch(JavaLayerException e) {
                        System.out.print("Lỗi âm thanh: " + e);
                    }
                }
            }).start();    
        }
        catch(FileNotFoundException | JavaLayerException e ) { 
            JOptionPane.showInputDialog(e.getMessage()) ; 
        }  
    }
}
