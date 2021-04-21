/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import project.music.SoundPlayer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import socket.NetworkPanel;

/**
 *
 * @author buiphukhuyen
 */
public class StartPanel extends JPanel {

    public StartPanel() {
        setLayout(null);
        setBounds(0, 0, 1200, 700);

        //Hình nền cho Game: Đối tượng tự tạo ImagePanel - Một panel có chức năng Load ảnh
        ImagePanel background = new ImagePanel("src/project/images/background.png", 0, 0, 1200, 700);

        //Thêm các button chức năng
        //Button 1: Chế độ 1 người chơi (Chơi với máy)
        Icon bug1 = new ImageIcon("src/project/images/btn_1player.jpg");
        JButton oneButton = new JButton(bug1);
        
        //Button 2: Chế độ 2 người chơi 
        Icon bug2 = new ImageIcon("src/project/images/btn_2players.jpg");
        JButton twoButton = new JButton(bug2);
        
        //Button 3: Chế độ chơi Socket
        Icon bug3 = new ImageIcon("src/project/images/btn_socket.jpg");
        JButton socketButton = new JButton(bug3);

        //Định vị trí các button 
        oneButton.setBounds(475, 280, 250, 75);
        twoButton.setBounds(475, 360, 250, 75);
        socketButton.setBounds(475, 440, 250, 75);
        
        add(oneButton);
        add(twoButton);
        add(socketButton);

        add(background);

        //Thêm hành động cho button "1 player": người chơi đấu với máy 
        oneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Chương trình gỡ bỏ chế độ START MENU, thay vào là chế độ GAME 1 người chơi
                Main.myFrame.remove(Main.myStartPanel);
                Main.myGamePanel = new GamePanel();
                Main.myGamePanel.numberPlayer = 1; // Số người chơi là một 
                Main.myFrame.add(Main.myGamePanel);
                Main.myFrame.repaint();
            }
        });
        
        //Thêm hành động cho button "2 player" : Chế độ 2 người chơi trên cùng một máy 
        twoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Chương trình gỡ bỏ chế độ START MENU, thay vào là chế độ GAME 2 người chơi
                Main.myFrame.remove(Main.myStartPanel);
                Main.myGamePanel = new GamePanel();
                Main.myGamePanel.numberPlayer = 2;
                Main.myFrame.add(Main.myGamePanel);
                Main.myFrame.repaint();

            }
        });
        
        /* thêm hành động cho LAN GAME button : 
         2 người chơi kết nối 2 máy thông qua Socket */
        socketButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //Chương trình gỡ bỏ chế độ START MENU, thay vào là menu kết nối thông qua Socket
                Main.myFrame.remove(Main.myStartPanel);
                NetworkPanel.joinButton.setEnabled(true);
                Main.myFrame.add(Main.myNetworkPanel);
                Main.myFrame.repaint();
            }
        });
        
    }

}