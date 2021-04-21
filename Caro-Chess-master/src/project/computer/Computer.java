/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.computer;

import project.Point;

/**
 *
 * @author buiphukhuyen
 */


public class Computer {

    private int height;
    private int width;
    public int optimalX; //Toạ độ x - nước đi tối ưu
    public int optimalY; //Toạ độ y - nước đi tối ưu


    private EvalBoard myEvalBoard; //Điểm trạng thái bàn cờ

    public Computer(int height, int width) {
        this.height = height;
        this.width = width;
        myEvalBoard = new EvalBoard(height, width);
    }

    //Phương thức tìm nước đi cho máy
    public void FindMove(int[][] status) {
        calculateEvalBoard(2, status);      //Đánh giá điểm với người chơi hiện tại là PC
        Point temp =  myEvalBoard.MaxPos();
        optimalX = temp.x;
        optimalY = temp.y;
    }
    
     public void calculateEvalBoard(int player, int[][] status) {
         
        int[] DScore = new int[]{0, 1, 9, 81, 729};         //Mảng điểm phòng ngự
        int[] AScore = new int[]{0, 2, 18, 162, 1458};      //Mảng điểm tấn công

        int row, col, ePC, eHuman;

        myEvalBoard.ResetBoard();   //Reset toàn bộ trạng thái bàn cờ
        
        //Đánh giá theo hàng ngang
        for (row = 0; row < height; row++) {
            for (col = 0; col < width - 4; col++) {
                ePC = 0;
                eHuman = 0;
                
                for (int i = 0; i < 5; i++) {
                    if (status[row][col + i] == 1) {    //Nếu quân đó là của Human
                        eHuman++;
                    }
                    if (status[row][ col + i] == 2) {   //Nếu quân đó là của Computer
                        ePC++;
                    }
                }

                if (eHuman * ePC == 0 && eHuman != ePC) //Một trong 2 bằng 0 và không đồng thời bằng 0
                {
                    for (int i = 0; i < 5; i++) {
                        if (status[row][col + i] == 0) // Nếu ô chưa đánh
                        {
                            if (eHuman == 0) { //ePC != 0
                                if (player == 1) { 
                                    myEvalBoard.EBoard[row][col + i] += DScore[ePC]; //Cộng điểm phòng ngự
                                } else {
                                    myEvalBoard.EBoard[row][col + i] += AScore[ePC]; //Cộng điểm tấn công
                                }
                            }
                            else if (ePC == 0) { //eHuman != 0
                                if (player == 2) {
                                    myEvalBoard.EBoard[row][col + i] += DScore[eHuman];
                                } else {
                                    myEvalBoard.EBoard[row][col + i] += AScore[eHuman];
                                }
                            }
                            
                        }
                    }
                }
            }
        }
        
        //Đánh giá theo cột
        for (col = 0; col < width; col++) {
            for (row = 0; row < height - 4; row++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (status[row + i][col] == 1) {
                        eHuman++;
                    }
                    if (status[row + i][col] == 2) {
                        ePC++;
                    }
                }

                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (status[row + i][col] == 0)  //Các ô chưa đánh
                        {
                            if (eHuman == 0) { 
                                if (player == 1) {
                                    myEvalBoard.EBoard[row + i][col] += DScore[ePC];
                                } else {
                                    myEvalBoard.EBoard[row + i][col] += AScore[ePC];
                                }
                            }
                            if (ePC == 0) {
                                if (player == 2) {
                                    myEvalBoard.EBoard[row + i][ col] += DScore[eHuman];
                                } else {
                                    myEvalBoard.EBoard[row + i][col] += AScore[eHuman];
                                }
                            }
                            
                        }
                    }

                }
            }
        }

        //Đánh giá theo đường chéo chính
        for (col = 0; col < width - 4; col++) {
            for (row = 0; row < height - 4; row++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (status[row + i][col + i] == 1) {
                        eHuman++;
                    }
                    if (status[row + i][col + i] == 2) {
                        ePC++;
                    }
                }

                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (status[row + i][col + i] == 0) // Neu o chua duoc danh
                        {
                            if (eHuman == 0) {
                                if (player == 1) {
                                    myEvalBoard.EBoard[row + i][col + i] += DScore[ePC];
                                } else {
                                    myEvalBoard.EBoard[row + i][col + i] += AScore[ePC];
                                }
                            }
                            if (ePC == 0) {
                                if (player == 2) {
                                    myEvalBoard.EBoard[row + i][col + i] += DScore[eHuman];
                                } else {
                                    myEvalBoard.EBoard[row + i][col + i] += AScore[eHuman];
                                }
                            }
                            
                        }
                    }

                }
            }
        }

        //Đánh giá theo đường chéo phụ
        for (row = 4; row < width; row++) {
            for (col = 0; col < height - 4; col++) {
                ePC = 0;
                eHuman = 0;
                for (int i = 0; i < 5; i++) {
                    if (status[row - i][col + i] == 1) {
                        eHuman++;
                    }
                    if (status[row - i][col + i] == 2) {
                        ePC++;
                    }
                }

                if (eHuman * ePC == 0 && eHuman != ePC) {
                    for (int i = 0; i < 5; i++) {
                        if (status[row - i][col + i] == 0) // Neu o chua duoc danh
                        {
                            if (eHuman == 0) {
                                if (player == 1) {
                                    myEvalBoard.EBoard[row - i][col + i] += DScore[ePC];
                                } else {
                                    myEvalBoard.EBoard[row - i][col + i] += AScore[ePC];
                                }
                            }
                            if (ePC == 0) {
                                if (player == 2) {
                                    myEvalBoard.EBoard[row - i][col + i] += DScore[eHuman];
                                } else {
                                    myEvalBoard.EBoard[row - i][ col + i] += AScore[eHuman];
                                }
                            }
                           
                        }
                    }

                }
            }
        }
    }
}

