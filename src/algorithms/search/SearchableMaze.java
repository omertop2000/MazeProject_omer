package algorithms.search;
import algorithms.mazeGenerators.Maze;
import java.util.ArrayList;
import java.util.Collections;

public class SearchableMaze implements ISearchable{
    private final Maze maze;
    private final MazeState mazestate;
    private int diagonal_value;

    public SearchableMaze(Maze maze){
        this.maze = maze;
        diagonal_value = 10;
        this.mazestate = new MazeState(maze);
        link_searchable_maze();
    }

    public Maze getMaze() {
        return maze;
    }

    public SearchableMaze(SearchableMaze searchableMaze){
        this.maze = searchableMaze.getMaze();
        this.diagonal_value = searchableMaze.get_diagonal_value();
        this.mazestate = new MazeState(maze);
        link_searchable_maze();
    }

    public ISearchable get_copy() {
        return new SearchableMaze(this);
    }


    public int get_diagonal_value(){
        return this.diagonal_value;
    }

    public void set_diagonal_value(int diagonal_value){
        this.diagonal_value = diagonal_value;
    }


    public void link_searchable_maze(){
        for (int i = 0; i < maze.getRows(); i++) {
            for (int j = 0; j < maze.getCols(); j++) {
                AState cur_astate =  mazestate.getMazestate()[i][j];
                if(cur_astate != null)
                    cur_astate.setNeighbours(getAllPossibleStates(cur_astate));
            }
        }
    }

    public AState get_start_point(){
        int x = this.maze.getStartPosition().getRowIndex();
        int y = this.maze.getStartPosition().getColumnIndex();
        return this.mazestate.getMazestate()[x][y];
    }

    public AState get_goal_point(){
        int x = this.maze.getGoalPosition().getRowIndex();
        int y = this.maze.getGoalPosition().getColumnIndex();
        return this.mazestate.getMazestate()[x][y];
    }

    public ArrayList<AState> getAllPossibleStates(AState current_state) {
        ArrayList<AState> new_possible_states = new ArrayList<>();
        int row_pos,col_pos;
        boolean upperPath,lowerPath,leftPath,rightPath;
        col_pos = current_state.getCol_pos();
        row_pos = current_state.getRow_pos();
        upperPath = false;
        lowerPath = false;
        leftPath = false;
        rightPath = false;
        int[][] maze_matrix = this.maze.getMatrix();

        //upper
        if(in_boundries(row_pos - 1,col_pos)){
            if(maze_matrix[row_pos - 1][col_pos] == 0) {
                new_possible_states.add(this.mazestate.getMazestate()[row_pos -1][col_pos]);
                upperPath = true;
            }
        }

        //right
        if(in_boundries(row_pos,col_pos + 1))
            {if(maze_matrix[row_pos][col_pos + 1] == 0) {
                new_possible_states.add(this.mazestate.getMazestate()[row_pos][col_pos + 1]);
                    rightPath = true;
                }
            }

        //left
        if(in_boundries(row_pos,col_pos - 1)){
            if(maze_matrix[row_pos][col_pos - 1] == 0) {
                new_possible_states.add(this.mazestate.getMazestate()[row_pos][col_pos - 1]);
                leftPath = true;
            }
        }

        //lower
        if(in_boundries(row_pos + 1,col_pos)){
            if(maze_matrix[row_pos + 1][col_pos] == 0) {
                new_possible_states.add(this.mazestate.getMazestate()[row_pos + 1][col_pos]);
                lowerPath = true;
            }
        }

        //lowerLeft
        if(in_boundries(row_pos + 1,col_pos - 1)){
            if (leftPath || lowerPath) {
                if (maze_matrix[row_pos + 1][col_pos - 1] == 0)
                    new_possible_states.add(this.mazestate.getMazestate()[row_pos + 1][col_pos - 1]);
            }
        }

        //upperLeft
        if(in_boundries(row_pos - 1,col_pos - 1)){
            if (upperPath || leftPath) {
                if (maze_matrix[row_pos - 1][col_pos - 1] == 0)
                    new_possible_states.add(this.mazestate.getMazestate()[row_pos - 1][col_pos - 1]);
            }
        }

        //upperRight
        if(in_boundries(row_pos - 1,col_pos + 1)){
            if (upperPath || rightPath) {
                if (maze_matrix[row_pos - 1][col_pos + 1] == 0)
                    new_possible_states.add(this.mazestate.getMazestate()[row_pos - 1][col_pos + 1]);
            }
        }

        //lowerRight
        if(in_boundries(row_pos + 1,col_pos + 1)){
            if (lowerPath || rightPath) {
                if (maze_matrix[row_pos + 1][col_pos + 1] == 0)
                    new_possible_states.add(this.mazestate.getMazestate()[row_pos + 1][col_pos + 1]);
            }
        }
        Collections.shuffle(new_possible_states); // shuffling all neighbours of current state
        return new_possible_states;
    }

    public boolean in_boundries(int row_pos,int col_pos){
        return row_pos < this.maze.getRows() && row_pos >= 0 && col_pos < this.maze.getCols() && col_pos >= 0;
    }
}
