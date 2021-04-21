/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import project.Main;

/**
 *
 * @author buiphukhuyen
 */
public class BackButton extends JButton {

    String presentPanel;

    public BackButton(String presnetPanel) {
        this.presentPanel = presnetPanel;
        Icon myIcon = new ImageIcon("src/project/images/BackButton.jpg");
        setIcon(myIcon);
        setBorderPainted(false);
        setBounds(1120, 10, 60, 60);
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if ("GamePanel".equals(presentPanel)) {
                    Main.myFrame.remove(Main.myGamePanel);
                }

                if ("NetworkPanel".equals(presentPanel) ) {
                        Main.myFrame.remove(Main.myNetworkPanel);
                    /* Khi trở về startMenu, nếu là host thì tất cả kết nối của server bị ngắt*/
                   
                }
                if("towLanPlayerPanel".equals(presentPanel)) { 
                    Main.myFrame.remove(Main.twoLanPlayerPanel);
                    try {
                        
                    } catch (Exception ex) {
                       
                    }
                }
                
                Main.myFrame.add(Main.myStartPanel);
                Main.startGame = true;
                Main.myFrame.repaint();

            }
        });

    }
}
