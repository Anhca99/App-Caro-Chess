/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author buiphukhuyen
 */
public class Check {

    private int height;
    private int width;

    public Check(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public boolean checkIt(int row, int col, int[][] status, int player) {
        if (rowCheck(row, col, status, player)) {
            return true;
        }
        if (columnCheck(row, col, status, player)) {
            return true;
        }
        if (leftCheck(row, col, status, player)) {
            return true;
        }
        if (rightCheck(row, col, status, player)) {
            return true;
        }
        return false;
    }
    
    public boolean rowCheck(int row, int col, int[][] status, int player) {

        int count = 0;

        for (int i = 0; i < width; i++) {
            if (status[row][i] == player) {
                count++;
                if (count >= 5) {
                    return true;
                }
            } else {
                count = 0;
            }
        }
        return false;
    }

    public boolean columnCheck(int row, int col, int[][] status, int player) {

        int count = 0;

        for (int i = 0; i < height; i++) {
            if (status[i][col] == player) {
                count++;
                if (count >= 5) {
                    return true;
                }

            } else {
                count = 0;
            }
        }
        return false;

    }

    public boolean leftCheck(int row, int col, int[][] status, int player) {
        int count = 0;
        try {
            while (col > 0 && row > 0) {
                col--;
                row--;
            }
            while (col < width && row < height) {
                if (status[row][col] == player) {
                    count++;
                    if (count >= 5) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                col++;
                row++;
            }

        } catch (Exception e) {
        }
        return false;
    }

    public boolean rightCheck(int row, int col, int[][] status, int player) {

        int count = 0;
        try {
            while (col > 0 && row < 15) {
                col--;
                row++;

            }
            while (col <= 31 && row >= 0) {
                if (status[row][col] == player) {
                    count++;
                    if (count >= 5) {
                        return true;
                    }
                } else {
                    count = 0;
                }
                col++;
                row--;
            }

        } catch (Exception e) {}
        return false;
    }

    
}
