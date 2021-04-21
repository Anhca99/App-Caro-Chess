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

/*
   Lớp đánh giá trạng thái cho mỗi ô trong bàn cờ
 */

public class EvalBoard {

    public int height, width;
    public int[][] EBoard;
    public int max ; 

    public EvalBoard(int height, int width) {
        this.height = height;
        this.width = width;
        EBoard = new int[height][width];

    }

    public void ResetBoard() {
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                EBoard[r][c] = 0;
            }
        }
    }
    
    public Point MaxPos() {
        max = 0;
        Point p = new Point();
        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                if (EBoard[r][c] > max) {
                    p.x = r;
                    p.y = c;
                    max = EBoard[r][c];
                }

            }
        }
        return p;
    }
}
