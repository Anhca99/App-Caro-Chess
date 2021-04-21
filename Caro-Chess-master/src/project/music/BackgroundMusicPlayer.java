/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.music;

import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.JPanel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author buiphukhuyen
 */
public class BackgroundMusicPlayer extends JPanel {

    private int total;   //Tổng số byte của file nhạc  
    private int stop;    //Số byte chưa đọc

    private FileInputStream FIS;
    private Player myPlayer;

    public boolean isStopping = false;
    public boolean isPlay = true;

    //Phương thức khởi tạo
    public BackgroundMusicPlayer() {
        playMusic(-1);
        isStopping = false;
        repeatMusic();
    }
    
    //Phương thức Chạy Nhạc
    @SuppressWarnings("empty-statement")
    public void playMusic(final int pos) {
        new Thread(new Thread() {
            @Override
            public void run() {
                try {
                    FIS = new FileInputStream("sounds/background_1.mp3");
                    total = FIS.available();
                    if (pos > -1) {
                        FIS.skip(pos);
                    }
                    myPlayer = new Player(FIS);
                    myPlayer.play();
                } catch (IOException | JavaLayerException e) {}
            }
        }).start();;
    }
    
    //Phương thức Dừng Nhạc
    public void stopMusic() {
        try {
            stop = FIS.available();
            myPlayer.close();
        } catch (IOException e) { }
    }

    //Phương thức Lặp lại Nhạc
    public void repeatMusic() {
        new Thread(new Thread() {
            @Override
            public void run() {
                while (isStopping == false) {
                    try {
                        Thread.sleep(100);
                        int now = FIS.available();
                        if (now == 0) {
                            playMusic(-1);
                        }
                    } catch (IOException | InterruptedException e) {}
                }
            }
        }).start();;
    }

    //Phương thức Chạy lại Nhạc
    public void resumeMusic() {
        try {
            if (stop == 0) {
                playMusic(-1);
            } else {
                playMusic(total - stop);
            }
        } catch (Exception e) {}

    }

}
