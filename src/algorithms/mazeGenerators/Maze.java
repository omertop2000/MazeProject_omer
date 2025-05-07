package algorithms.mazeGenerators;

import Server.Server;

import java.io.Serializable;
import java.nio.ByteBuffer;


public class Maze implements Serializable {
    private int[][] matrix;
    private Position startPosition;
    private Position goalPosition;
    private final int rows;
    private final int cols;

    public Maze(int rows, int cols){
        if (rows <= 2 || cols <= 2)
            throw new IllegalArgumentException();
        matrix = new int[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }





    public Maze(byte[] byte_arr){
        byte[] rows_bytes = new byte[4];
        byte[] cols_bytes = new byte[4];
        byte[] start_pos_row = new byte[4];
        byte[] start_pos_col = new byte[4];
        byte[] goal_pos_row = new byte[4];
        byte[] goal_pos_col = new byte[4];
        for(int i=0 ; i < 4 ; i++){
            rows_bytes[i] = byte_arr[i];
            cols_bytes[i] = byte_arr[i +4];
            start_pos_row[i] = byte_arr[i +8];
            start_pos_col[i] = byte_arr[i +12];
            goal_pos_row[i] = byte_arr[i +16];
            goal_pos_col[i] = byte_arr[i +20];
        }
        int maze_rows = ByteBuffer.wrap(rows_bytes).getInt();
        int maze_cols = ByteBuffer.wrap(cols_bytes).getInt();
        int start_row_pos_int = ByteBuffer.wrap(start_pos_row).getInt();
        int start_col_pos_int = ByteBuffer.wrap(start_pos_col).getInt();
        int goal_row_pos_int = ByteBuffer.wrap(goal_pos_row).getInt();
        int goal_col_pos_int = ByteBuffer.wrap(goal_pos_col).getInt();
        this.rows = maze_rows;
        this.cols = maze_cols;
        this.startPosition = new Position(start_row_pos_int, start_col_pos_int);
        this.goalPosition = new Position(goal_row_pos_int, goal_col_pos_int);
//        int j = 0 ; int k = 0 ;
//        this.matrix = new int[rows][cols];
//        for(int i =24; i < byte_arr.length ; i++){
//            if(k == cols){
//                k = 0;
//                j++;
//            }
//            if(byte_arr[i] == 0)
//                this.matrix[j][k] = 0;
//            else
//                this.matrix[j][k] = 1;
//            k++;
        int index = 24; // Start after metadata (6 ints = 24 bytes)
        this.matrix = new int[rows][cols];

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                this.matrix[r][c] = byte_arr[index++] == 0 ? 0 : 1;
            }
        }
    }


    public byte[] toByteArray(){
        // rows - 4 bytes cols - 4 bytes start_row - 4 bytes start_col - 4 bytes goal_row - 4 bytes goal_col - 4 bytes
        byte[] byte_arr = new byte[rows * cols + 8 + 8 + 8]; // 8 for rows and cols , 8 each for position -> x ->4 y->4
        byte[] rows_bytes = ByteBuffer.allocate(4).putInt(rows).array();
        byte[] cols_bytes = ByteBuffer.allocate(4).putInt(cols).array();
        byte[] start_pos_row = ByteBuffer.allocate(4).putInt(this.startPosition.getRowIndex()).array();
        byte[] start_pos_col = ByteBuffer.allocate(4).putInt(this.startPosition.getColumnIndex()).array();
        byte[] goal_pos_row = ByteBuffer.allocate(4).putInt(this.goalPosition.getRowIndex()).array();
        byte[] goal_pos_col = ByteBuffer.allocate(4).putInt(this.goalPosition.getColumnIndex()).array();
        for(int i=0 ; i < 4 ; i++){
            byte_arr[i] = rows_bytes[i];
            byte_arr[i +4] = cols_bytes[i];
            byte_arr[i +8] = start_pos_row[i];
            byte_arr[i +12] = start_pos_col[i];
            byte_arr[i +16] = goal_pos_row[i];
            byte_arr[i +20] = goal_pos_col[i];
        }
        int i =24;
        for(int j = 0 ; j < rows ; j++){
            for(int k = 0 ; k < cols ; k++){
                if(matrix[j][k] == 1)
                    byte_arr[i] = 1;
                else
                    byte_arr[i] = 0;
                i++;
            }
        }

        return byte_arr;
    }





    public Position getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(Position startPosition) {
        this.startPosition = startPosition;
    }

    public Position getGoalPosition() {
        return goalPosition;
    }

    public void setGoalPosition(Position endPosition) {
        this.goalPosition = endPosition;
    }

    public int getCols() {return cols;}

    public int getRows() {
        return rows;
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(int[][] matrix) {
        this.matrix = matrix;
    }

    public boolean equals(Maze other_maze){
        for(int i = 0 ; i < rows ; i++){
            for(int j = 0 ; j < cols ; j++){
                if(matrix[i][j] != other_maze.getMatrix()[i][j])
                    return false;
            }
        }
        return true;
    }

    public void print(){
        for (int i = 0; i < rows; i ++) {
            for (int j = 0; j < cols; j++) {
                if (this.getStartPosition().getRowIndex() == i && this.getStartPosition().getColumnIndex() == j){
                    System.out.print("S");
                }
                else if(this.getGoalPosition().getRowIndex() == i && this.getGoalPosition().getColumnIndex() == j){
                    System.out.print("E");
                }
                else
                    System.out.print(matrix[i][j] == 1 ? "1" : "0");
            }
            System.out.println();
        }
    }
}
