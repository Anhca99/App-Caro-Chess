/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import project.GamePanel;
import project.ImagePanel;
import project.Main;
import project.board.BackButton;
import project.music.SoundPlayer;

/**
 *
 * @author buiphukhuyen
 */
public class Client {
     /*
     * Các thuộc tính đồ họa của game
     *
     *
     */
    private String URLIcon;
    private String URLOpenentICon;
    public ImagePanel background = new ImagePanel("src/project/images/background_waiting.png", 0, 0, 1200, 700);
    public ImagePanel waitingPicture = new ImagePanel("src/project/images/waitingO.png", 0, 150, 280, 500);
    public ImagePanel attackPicture = new ImagePanel("src/project/images/attackO.png", 920, 150, 280, 500);
    private ImagePanel[][] board = new ImagePanel[16][16];
    private ImagePanel currentSquare;
    public static BackButton myBackButton;

    public SoundPlayer mySoundPlayer = new SoundPlayer();
    /*
      Các thuộc tính kết nối server:
     
     */
    private static int PORT = 8901;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    /*
     * Các thuộc tính xét trạng thái game
     *
     */
    public static boolean isStartGame;
    char mark;

    public Client(String serverAddress) {
        try {
            isStartGame = false;
            // Setup networking
            socket = new Socket(serverAddress, PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (Exception ex) {
            
        }

        myBackButton = new BackButton("towLanPlayerPanel");
        myBackButton.setEnabled(false);
        background.add(myBackButton);
        background.repaint();
        
        ImagePanel boardPanel  = new ImagePanel("src/project/images/table.png", 280, 20, 643, 643);

        boardPanel.setLayout(null);
        
        for (int row = 0; row < 16; row++) {
            for (int col = 0; col < 16; col++) {
                final int k = row;
                final int l = col;
                board[row][col] = new ImagePanel("src/project/images/frame_empty.png", col * 40, row * 40, 40, 40);
                board[row][col].repaint();
                background.repaint();

                board[row][col].addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (GamePanel.canPlaySound) {
                            mySoundPlayer.playSound("sounds/click.mp3");
                        }

                        currentSquare = board[k][l];
                        
                        System.out.println("Client : isStartGame = " + isStartGame);
                        if (isStartGame) {
                            System.out.println("MOVE " + (k * 16 + l));
                            output.println("MOVE " + (k * 16 + l));
                        }
                    }
                });
                boardPanel.add(board[row][col]);
                board[row][col].repaint();
                background.repaint();

            }

        }
        background.add(boardPanel);

    }

    /* phương thức của Client để lắng nghe tin nhắn từ Server.
     *Tin nhắn đầu tiên là "WELCOME" và đồng thời nhận được kí tự đánh dấu.
      *Sử dụng vòng lặp vô hạn để lắng nghe các tin nhắn "VALID_MOVE",
      "OPPONENT_MOVED", "VICTORY", "DEFEAT", "OPPONENT_QUIT hoặc "MESSAGE".
     *Các tin "VICTORY", "DEFEAT" là các tính hiệu kết thúc game. Khi đó, 
    chương trình hỏi người chơi có muốn chơi lại hay không. Nếu không thì Server
    gửi tin nhắn "QUIT" để thoát game và "OPPONENT_QUIT" để đối thủ cũng thoát game.       
    */
    
    public void play() {
        String response;
        try {
            response = input.readLine();
            if (response.startsWith("WELCOME")) {
                mark = response.charAt(8);
                URLIcon = (mark == 'X' ? "src/project/images/frame_x.png" : "src/project/images/frame_o.png");
                URLOpenentICon = (mark == 'X' ? "src/project/images/frame_o.png" : "src/project/images/frame_x.png");

                if (mark == 'X') {
                    waitingPicture.setPicture("src/project/images/waitingO.png");
                    attackPicture.setPicture("src/project/images/attackX.png");
                } else if (mark == 'O') {
                    waitingPicture.setPicture("src/project/images/waitingX.png");
                    attackPicture.setPicture("src/project/images/attackO.png");
                    isStartGame = true;
                    background.add(waitingPicture);
                }
            }
            while (true) {
                response = input.readLine();
                background.repaint();
                Main.myFrame.repaint();
                if (response.startsWith("VALID_MOVE")) {

                    currentSquare.setPicture(URLIcon);

                    background.remove(attackPicture);

                    background.add(waitingPicture);

                    background.repaint();
                    Main.myFrame.repaint();

                } else if (response.startsWith("OPPONENT_MOVED")) {
                    //Đối thủ đã đi
                    background.remove(waitingPicture);
                    background.add(attackPicture);

                    int location = Integer.parseInt(response.substring(15));
                    int row = location / 16;
                    int col = location % 16;
                    board[row][col].setPicture(URLOpenentICon);
                    background.repaint();
                    Main.myFrame.repaint();
                    myBackButton.setEnabled(true); // Kích hoạt nút thoát ra 



                } else if (response.startsWith("VICTORY")) {
                    //Âm thanh 
                    if (GamePanel.canPlaySound) {
                        mySoundPlayer.playSound("sounds/win.mp3");
                    }
                    
                    //Bảng thông báo
                    Icon myIcon = new ImageIcon("src/project/images/winner" + (mark == 'X' ? '1' : '2') + "_resize.gif");
                    JOptionPane.showMessageDialog(null, null, "Chiến thắng!", JOptionPane.INFORMATION_MESSAGE, myIcon);
                    Main.myFrame.repaint();
                    break;
                    
                } 
                //Trường hợp thua
                else if (response.startsWith("DEFEAT")) {
                    //Âm thanh 
                    if (GamePanel.canPlaySound) {
                        mySoundPlayer.playSound("sounds/gameover.mp3");
                    }
                    
                    //Bảng thông báo
                    Icon myIcon = new ImageIcon("src/project/images/winner" + (mark == 'X' ? '2' : '1') + "_resize.gif");
                    JOptionPane.showMessageDialog(null, null, "Thua!", JOptionPane.INFORMATION_MESSAGE, myIcon);

                    Main.myFrame.repaint();
                    break;
                } 
                
                //Trường hợp nhận thông báo
                else if (response.startsWith("MESSAGE")) {
                    ////Game bắt đầu, người chơi 1 được phép chơi
                    if ("MESSAGE First turn".equals(response)) {
                        background.add(attackPicture);  
                    } 
                    //Trường hợp đã kết nối toàn bộ
                    else if ("MESSAGE All is connected".equals(response)) {
                        isStartGame = true;
                    } 
                    Main.myFrame.repaint();
                }
            }
            output.println("QUIT");
        } catch (Exception ex) {

        } finally {
            try {
                socket.close();
            } catch (Exception ex) {
            }

        }
    }

    public boolean wantsToPlayAgain() {

        int response = JOptionPane.showConfirmDialog(null,
                "Bạn muốn chơi lại lần nữa không ?",
                "Game Carô",
                JOptionPane.YES_NO_OPTION);
        isStartGame = true;
        return response == JOptionPane.YES_OPTION;

    }
}
