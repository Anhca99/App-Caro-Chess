/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.music;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import project.GamePanel;

/**
 *
 * @author buiphukhuyen
 */

public class SoundPanel extends JPanel {

    JButton soundButton;

    public SoundPanel() {
        setLayout(null);
        setBounds(1040, 10, 60, 60);

        Icon bug1 = new ImageIcon("src/project/images/sound_on.jpg");
        soundButton = new JButton(bug1);
        soundButton.setBorderPainted(false);
        soundButton.setBounds(0, 0, 60, 60);
        soundButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
              if (GamePanel.canPlaySound) {
                        GamePanel.canPlaySound = false;
                        Icon bug1 = new ImageIcon("src/project/images/sound_off.jpg");
                        soundButton.setIcon(bug1);
                } else {
                    GamePanel.canPlaySound = true;
                    Icon bug1 = new ImageIcon("src/project/images/sound_on.jpg");
                    soundButton.setIcon(bug1);
                } 
            }
        });
        add(soundButton);
    }
}