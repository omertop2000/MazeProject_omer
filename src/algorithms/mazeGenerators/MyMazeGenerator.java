package algorithms.mazeGenerators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class MyMazeGenerator extends AMazeGenerator {

    private static class Cell { // we will use static class of cell to represent a tile in the maze
        int x;
        int y;
        Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public Maze generate(int rows, int columns) {
        if (rows <= 2 || columns <= 2)
            throw new IllegalArgumentException("Invalid maze dimensions");
        Maze newMaze = new Maze(rows, columns); //creating new maze
        int[][] matrix1 = newMaze.getMatrix();
        for (int i = 0; i < rows; i++) {   //setting all walls
            for (int j = 0; j < columns; j++)
                matrix1[i][j] = 1;
        }
        Stack<Cell> stack = new Stack<>();  //data structure for the algorithm (stack)
        Cell start = new Cell(1, 1); // starting cell
        matrix1[start.y][start.x] = 0;  //setting a starting point
        stack.push(start);
        while (!stack.isEmpty()) {
            Cell current = stack.peek();
            List<Cell> neighbors = getUnvisitedNeighbors(current, matrix1, columns, rows); // function of getting valid neighbours
            if (!neighbors.isEmpty()) { // if it has neighbours
                Cell neighbor = neighbors.get((int) (Math.random() * neighbors.size()));   //getting random neighbour
                removeWall(current, neighbor, matrix1);    //removes wall
                if(neighbor.x == 0 || neighbor.x == columns - 1 || neighbor.y == 0 || neighbor.y == rows - 1) {
                    matrix1[neighbor.y][neighbor.x] = 2;
                }
                else{
                    matrix1[neighbor.y][neighbor.x] = 0;
                    stack.push(neighbor);
                }
            }
            else
                stack.pop();
        }
        for (int i = 0; i < rows; i++) {
            if (matrix1[i][0] == 2)
                matrix1[i][0] = 1;
        }
        for (int i = 0; i < rows; i++) {
            if (matrix1[i][columns - 1] == 2)
                matrix1[i][columns - 1] = 1;
        }
        for (int i = 0; i < columns; i++) {
            if (matrix1[0][i] == 2)
                matrix1[0][i] = 1;
        }
        for (int i = 0; i < columns; i++) {
            if (matrix1[rows - 1][i] == 2)
                matrix1[rows - 1][i] = 1;
        }
        //creating entrance and exit points for the maze
        int random1 = 1;
        int random2 = 1;
        Random random = new Random();
        while(random1 == random2) {
            random1 = random.nextInt(4);
            random2 = random.nextInt(4);
        } // getting 2 different random numbers from 0 to 3 which will determine the starting point and ending point edges
        newMaze.setStartPosition(createExits(newMaze, matrix1, rows, columns, random1));
        newMaze.setGoalPosition(createExits(newMaze, matrix1, rows, columns, random2));
        return newMaze;
    }

    private List<Cell> getUnvisitedNeighbors(Cell cell, int[][] maze, int width, int height) {
        List<Cell> neighbors = new ArrayList<>();

        if (cell.x > 1 && maze[cell.y][cell.x - 2] == 1)
            neighbors.add(new Cell(cell.x - 2, cell.y));

        if (cell.x < width - 2 && maze[cell.y][cell.x + 2] == 1)
            neighbors.add(new Cell(cell.x + 2, cell.y));

        if (cell.y > 1 && maze[cell.y - 2][cell.x] == 1)
            neighbors.add(new Cell(cell.x, cell.y - 2));

        if (cell.y < height - 2 && maze[cell.y + 2][cell.x] == 1)
            neighbors.add(new Cell(cell.x, cell.y + 2));

        Collections.shuffle(neighbors); // randomizing the neighbours order
        return neighbors;
    }

    private void removeWall(Cell current, Cell neighbor, int[][] maze) {
        int x = (current.x + neighbor.x) / 2;
        int y = (current.y + neighbor.y) / 2;
        maze[y][x] = 0;
    }

    public Position createExits(Maze maze, int[][] matrix, int rows, int cols, int randomWall){
        Position position = null;
        if (randomWall == 0){
            List<Integer> list_numbers = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                if(matrix[i][1] == 0)
                    list_numbers.add(i);
            }
            Collections.shuffle(list_numbers);
            int row_start = list_numbers.getFirst();
            matrix[row_start][0] = 0;
            position = new Position(row_start, 0);

        }
        else if (randomWall == 2){
            List<Integer> list_numbers = new ArrayList<>();
            for (int i = 0; i < rows; i++) {
                if(matrix[i][cols - 2] == 0)
                    list_numbers.add(i);
            }
            Collections.shuffle(list_numbers);
            int row_start = list_numbers.getFirst();
            matrix[row_start][cols - 1] = 0;
            position = new Position(row_start, cols - 1);

        }
        else if (randomWall == 1){
            List<Integer> list_numbers = new ArrayList<>();
            for (int i = 1; i < cols-1; i++) {
                if(matrix[rows - 2][i] == 0)
                    list_numbers.add(i);
            }
            Collections.shuffle(list_numbers);
            int col_start = list_numbers.getFirst();
            matrix[rows - 1][col_start] = 0;
            position = new Position(rows - 1, col_start);

        }
        else if (randomWall == 3){
            List<Integer> list_numbers = new ArrayList<>();
            for (int i = 1; i < cols-1; i++) {
                if (matrix[1][i] == 0)
                    list_numbers.add(i);
            }
            Collections.shuffle(list_numbers);
            int col_start = list_numbers.getFirst();
            matrix[0][col_start] = 0;
            position = new Position(0, col_start);

        }
        maze.setMatrix(matrix);
        return position;
    }
}



