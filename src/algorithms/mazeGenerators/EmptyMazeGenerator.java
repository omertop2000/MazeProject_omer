package algorithms.mazeGenerators;

public class EmptyMazeGenerator extends AMazeGenerator{

    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 2 || columns <= 2)
            throw new IllegalArgumentException();
        Maze newMaze = new Maze(rows, columns);
        int[][] matrix1 = newMaze.getMatrix();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix1[i][j] = 0;
            }
        }
        newMaze.setMatrix(matrix1);
        newMaze.setStartPosition(new Position(0,0));
        newMaze.setGoalPosition(new Position(rows - 1, columns - 1));
        return newMaze;
    }
}
