package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;

public interface IView {
    void goalReached();
    void setMazeViewModel(MyViewModel myViewModel);
    void solveMaze(ActionEvent actionEvent);
    void SaveMaze(ActionEvent actionEvent);
    void setPlayerPosition(int row, int col);
    void setUpdatePlayerRow(int updatePlayerRow);
    void setUpdatePlayerCol(int updatePlayerCol);
    void generateNewMaze();
}
