/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static project.Main.myFrame;
import project.music.SoundPlayer;

/**
 *
 * @author buiphukhuyen
 */

public class Winner extends JFrame{
    Winner(int winner) {
        setLayout(null);
        setVisible(true);
        setBounds(200, 200, 400, 612);
        setAlwaysOnTop(true);
        setResizable(false);
        setLocationRelativeTo(null); //Giữa màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        ImagePanel  winnerPicture ; 
        
        if(winner == 3) { //Thua máy tính
               winnerPicture = new ImagePanel("src/project/images/winner2_resize.gif", 0, 0, 400, 612) ; 
        } 
        else { //Thắng
              winnerPicture = new ImagePanel("src/project/images/winner"+winner+"_resize.gif", 0, 0, 400, 612) ; 
        }
        
        JButton acceptButton = new JButton("Xác nhận") ; 
        acceptButton.setBounds(300, 500, 90, 25);
        
        SoundPlayer mySound = new SoundPlayer() ; //Âm thanh
        if(GamePanel.winner==3 && GamePanel.canPlaySound)  { //Máy Win
              mySound.playSound("sounds/gameover.mp3" );
        }
        else if(  (GamePanel.winner == 1 || GamePanel.winner == 2) && GamePanel.canPlaySound )
             mySound.playSound("sounds/win.mp3" );
       
        
        acceptButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Main.myFrame.remove(Main.myGamePanel);
                Main.myFrame.add(Main.myStartPanel) ; 
                Main.myFrame.repaint();
                Main.startGame = true ; 
                dispose(); 
            
                 
            }
        });
        
        winnerPicture.add(acceptButton) ; 
        repaint() ;
          
        add(winnerPicture) ; 
    }
}
