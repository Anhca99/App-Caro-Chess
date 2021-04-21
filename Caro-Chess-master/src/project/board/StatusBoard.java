/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project.board;

/**
 *
 * @author buiphukhuyen
 */

public class StatusBoard {
    int height ;                        //Chiều cao bàn cờ
    private  int width ;                //Chiều rộng bàn cờ
    public  int[][] statusBoard;        //Mảng 2 chiều lưu trạng thái đánh
    private int [][] saveBoard  ;       
    
    public StatusBoard(int height, int width) {
        this.height = height;
        this.width = width;
        statusBoard = new int[height][width] ; 
        saveBoard = new int[height][width] ; 
    }
    
    //Khởi tạo ban đầu
    public void initilizeStatus() { 
        for(int i = 0 ; i < height ; i++) {
            for(int j = 0 ; j < width ; j++) {
                statusBoard[i][j] = 0 ; 
            }
        }
    }
    
    //Thiết lập một trạng thái
    public void setStatus(int row , int col , int player ) { 
        statusBoard[row][col] = player ; 
    }
    
    //Lưu trạng thái
    public void saveStatus() { 
       for(int row = 0 ; row < height ; row++) {
           for(int col = 0 ; col < width ; col++) {
               saveBoard[row][col] = statusBoard[row][col] ; 
           }
       }
    }
    
    
    public void loadStatus() { 
       for(int row = 0 ; row < height ; row++) {
           for(int col = 0 ; col < width ; col++) {
               statusBoard[row][col] = saveBoard[row][col] ; 
           }
       }
   }
}
