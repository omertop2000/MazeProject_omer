package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;

import java.util.Observer;

public interface IModel {
    void generateMaze(int rows, int cols);
    Maze getMaze();
    void solveMaze();
    Solution getSolution();
    void updatePlayerLocation(MovementDirection direction);
    int getPlayerRow();
    int getPlayerCol();
    void assignObserver(Observer o);
    void saveMaze(String name);
    void loadMaze(String mazeName);
    void setPlayerCol(int playerCol);
    void setPlayerRow(int playerRow);
    void dragMoving(double deltaX, double deltaY);
}
