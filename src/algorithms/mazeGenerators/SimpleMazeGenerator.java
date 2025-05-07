package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{
    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 2 || columns <= 2)
            throw new IllegalArgumentException();
        Maze newMaze = new Maze(rows,columns);
        int[][] matrix1 = newMaze.getMatrix();
        Random random = new Random();
        // for this implementation, 1 will be marked as path because 0 is the default
        matrix1[0][0] = 1;
        newMaze.setStartPosition(new Position(0,0));
        int row_index=0,col_index=0;
        int random_num;
        while(true){
            random_num = random.nextInt(2);
            if(random_num == 0) // go down
                row_index++;
            else // go right
                col_index++;
            matrix1[row_index][col_index] = 1;
            if(row_index == rows -1 || col_index == columns - 1) {
                newMaze.setGoalPosition(new Position(row_index,col_index));
                newMaze.setMatrix(matrix1);
                break;
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if(newMaze.getMatrix()[i][j] !=1){
                    random_num = random.nextInt(2);
                    if(random_num == 1)
                        newMaze.getMatrix()[i][j] = 1;
                    else
                        newMaze.getMatrix()[i][j] = 0;
                }
                else if(newMaze.getMatrix()[i][j] == 1){
                    newMaze.getMatrix()[i][j] = 0;
                }
            }
        }

        return newMaze;
    }
}
