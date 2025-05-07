package algorithms.search;
import algorithms.mazeGenerators.Maze;

public class MazeState {
    private Maze maze;
    private AState[][] mazestate;

    public AState[][] getMazestate() {
        return mazestate;
    }



    public void setMazestate(AState[][] mazestate) {
        this.mazestate = mazestate;
    }

    public MazeState (Maze maze){
        this.maze = maze;
        build_unlinked_maze();
    }

    public void build_unlinked_maze(){
        int rows = maze.getRows();
        int cols = maze.getCols();
        AState[][] new_maze_state = new AState[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(maze.getMatrix()[i][j] == 0)
                    new_maze_state[i][j] = new AState(i,j);
            }
        }
        setMazestate(new_maze_state);
    }

}
