/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import project.ImagePanel;
import project.Main;
import project.board.BackButton;
import project.music.SoundPlayer;

/**
 *
 * @author buiphukhuyen
 */
public class NetworkPanel extends Panel {

    public static Client myClient;
    public BackButton myBackButton;
    public String host;
    public int port;
    public static Server myServer;

    public static JButton joinButton;

    public ImagePanel waitingPanel;

    ImagePanel background = new ImagePanel("src/project/images/background.png", 0, 0, 1200, 700);

    public SoundPlayer mySoundPlayer = new SoundPlayer();

    public NetworkPanel() {
        myServer = new Server();
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        setBounds(0, 0, 1200, 700);
                        setLayout(null);

                        // button to return start menu
                        myBackButton = new BackButton("NetworkPanel");
                        add(myBackButton);

                        /*----------button to join to host---------- */
                        addJoinButton();

                        /*----------- Picture of backround----------- */
                        add(background);

                    }
                }
        ).start();

        //  ServerThread.run(); 
    }

    public void addJoinButton() {
        Icon bug1 = new ImageIcon("src/project/images/btn_ready.png");
        joinButton = new JButton(bug1);
        joinButton.setBorderPainted(false);
        joinButton.setBounds(330, 300, 550, 200);
        joinButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                //T???o 1 ti???u tr??nh thread ????? qu?? tr??nh k???t n???i song song v???i game
                new Thread(new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            String serverAddress;
                            try {
                                //L???p InetAddress ph????ng th???c t??nh getLocalHost -> L???y hostname (tr??? IP n???u kh??ng c??)
                                serverAddress = InetAddress.getLocalHost().getHostAddress();

                                //Set m???c ?????nh IP ???? l???y tr?????c ????, n???u kh??ng c?? th??? nh???p IP b???t k???
                                serverAddress = (String) JOptionPane.showInputDialog(null, "Nh???p ?????a ch??? IP", "Th??ng tin",
                                        JOptionPane.INFORMATION_MESSAGE, null, null, serverAddress);

                                System.out.println("Server address: " + serverAddress);

                                myClient = new Client(serverAddress);

                                JOptionPane.showMessageDialog(null, "????? k???t n???i, ng?????i ch??i c???a b???n c???n nh???p IP : " + serverAddress);

                                Main.myFrame.remove(Main.myNetworkPanel);
                                Main.twoLanPlayerPanel = myClient.background;
                                Main.myFrame.add(Main.twoLanPlayerPanel);
                                Main.myFrame.repaint();
                                myClient.play();

                                if (myClient.wantsToPlayAgain()) {
                                    Main.myFrame.remove(Main.twoLanPlayerPanel);

                                } else {
                                    Main.myFrame.remove(Main.twoLanPlayerPanel);
                                    Main.myFrame.add(Main.myStartPanel);

                                    break;
                                }
                            } catch (Exception ex) {}

                        }
                    }

                }).start();
                joinButton.setEnabled(false);

            }
        });
        add(joinButton);
    }

    

}
