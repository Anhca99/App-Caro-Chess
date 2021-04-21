/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import project.board.BackButton;
import project.board.StatusBoard;
import project.computer.Computer;
import project.music.SoundPlayer;

/**
 *
 * @author buiphukhuyen
 */

public class GamePanel extends JPanel {

    public static int winner;       //Player 1 win -> 1;  Player 2 win -> 2 ; Computer win -> 3 
    public int height = 16 ;        //Chiều cao bàn cờ (Mặc định là 16 ô)
    public int width  = 16 ;        //Chiều rộng bàn cờ (Mặc định là 16 ô)
    public int numberPlayer;        //Tổng số người chơi
    public int player;              //Lượt người chơi hiện hành : 1 -> Player 1 ; 2 -> Computer || Player 2 
    public StatusBoard myStatus;    //Bảng trạng thái gồm các giá trị: 0 chưa đánh, 1|2 đã đánh
    public int address;             //Địa chỉ ô được click chuột
  
    public SoundPlayer mySound = new SoundPlayer(); // đối tượng chơi âm thanh 
    public static boolean canPlaySound = true; //Biến điều kiện xem có thể chơi âm thanh
    
     
    public static ImagePanel backgroundPanel; // JPanel chính 
    public ImagePanel tablePanel;   // JPanel chứa các ô vuông 
    public BackButton myBackButton; // JButton quay về màn hình chính 
   
    public Computer myComputer;     // Đối tượng computer có khả năng tính toán nước đi
    public Check myCheck;           // Đối tượng kiểm tra xem có ai thắng hay hòa? 
  
 
    public MouseAdapter myAction;   // Đối tượng tạo hành động cho các ô 
 

    public GamePanel() {
        /* Khởi tạo các giá trị ban đầu cần thiết  */
        winner = -1 ; 
        setBounds(0, 0, 1200, 700);
        setLayout(null);

        height = 16;
        width = 16;

        myCheck = new Check(height, width);
        myStatus = new StatusBoard(height, width);
        myComputer = new Computer(height, width);

        //Thêm Button quay về
        myBackButton = new BackButton("GamePanel");
        add(myBackButton);

        
        //Background
        backgroundPanel = new ImagePanel("src/project/images/background_player.png", 0, 0, 1200, 700);

        //Panel chứa bàn cờ 
        tablePanel = new ImagePanel("src/project/images/table.png", 280, 20, 643, 643);

        //Tạo các ô cờ 
        ImagePanel[][] mySquare = new ImagePanel[16][16];
        
        //Hàm xử lý Game
        normalGame();
       
        //Vẽ các ô cờ lên Panel bàn cờ
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                myStatus.statusBoard[i][j] = 0;
                mySquare[i][j] = new ImagePanel("src/project/images/frame_empty.png", i * 40, j * 40, 40, 40);
                tablePanel.add(mySquare[i][j]);
                // action of normal game
                mySquare[i][j].addMouseListener(myAction);
            }
        }
        
        repaint();

        //
        add(tablePanel);

        add(backgroundPanel);

    }

    public void normalGame() {
        player = 1;
        myAction = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (canPlaySound) {
                    mySound.playSound("sounds/click.mp3");
                }
                if (Main.startGame == true) {
                    Main.myFrame.repaint();
                    ImagePanel a = (ImagePanel) e.getComponent();
                   // System.out.println("a= " + a );
                    address = tablePanel.getComponentZOrder(a);  //Lấy địa chỉ ô ấn vào từ 0 đến (max -1) theo cột dọc 
                    System.out.println("address " + address );
                    int row = address % 16;
                    int col = address / 16;

                    if (myStatus.statusBoard[row][col] == 0) {
                        if (player == 1) {
                            //Vẽ ảnh X
                            a.setPicture("src/project/images/frame_x.png");
                            
                            //Đánh dấu X đã đánh trong bàn cờ
                            myStatus.setStatus(row, col, player);
                            repaint();

                            System.out.println("Trạng thái X:" + row + " " + col + " " + myStatus.statusBoard[row][col]);
                            
                            //Kiểm tra chiến thắng 
                            if (myCheck.checkIt(row, col, myStatus.statusBoard, player) == true) {
                                Main.startGame = false;
                                winner = 1;
                                Winner myWinnerFrame = new Winner(1);

                                System.out.println("X thắng!");
                            }
                            //Cho phép người chơi 2 đi (Người chơi 1 đóng)
                            player = 2;

                            if (numberPlayer == 1) { //Chế độ mội người chơi (Chơi với máy)
                                //myComputer.calculateEvalBoard(player, myStatus.statusBoard);
                                //Computer tính toán và đi
                                do {
                                    Computer myComputer = new Computer(height, width);
                                   // myComputer.calculateEvalBoard(player, myStatus.statusBoard);
                                    myComputer.FindMove(myStatus.statusBoard);
                                    row = myComputer.optimalX;
                                    col = myComputer.optimalY;

                                } while (myStatus.statusBoard[row][col] != 0);
                                
                                System.out.println("Trạng thái máy: " + row + " " + col + " " + myStatus.statusBoard[row][col]);
                                
                                ImagePanel b = new ImagePanel("src/project/images/frame_o.png", col * 40, row * 40, 40, 40);
                                tablePanel.add(b);
                                repaint();
                                myStatus.statusBoard[row][col] = 2;
                                if (myCheck.checkIt(row, col, myStatus.statusBoard, player) == true) {
                                    Main.startGame = false;
                                    winner = 3;
                                    Winner myWinnerFrame = new Winner(3);

                                    System.out.println("Máy thắng!");
                                }
                                player = 1;

                            }

                        } else if (player == 2) {
                            a.setPicture("src/project/images/frame_o.png");
                            myStatus.setStatus(row, col, player);
                            repaint();
                            System.out.println("Người chơi O " + row + " " + col + " " + myStatus.statusBoard[row][col]);
                            
                            //Kiểm tra nước đi thắng?
                            if (myCheck.checkIt(row, col, myStatus.statusBoard, player) == true) {
                                Main.startGame = false;
                                winner = 2;
                                Winner myWinnerFrame = new Winner(2);
                                System.out.println("O thắng!");

                            }
                            player = 1;
                        }
                    }
                }
            }
        };
    }
}
