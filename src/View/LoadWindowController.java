package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static View.OptionsWindowController.isMusic;

public class LoadWindowController  implements Initializable {
    public Stage currStage;
    public ListView listView;
    public Label errorLabel;
    public AnchorPane main_pane;
    public Button loadButton;
    public ImageView mainImageView;

    public void backToMenu(ActionEvent event) throws IOException {
        mouseAudio();
        Scene root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
        currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(root);
        currStage.show();
    }

    public void mouseAudio(){
        if (isMusic()){
            Media mouseClicked = new Media(getClass().getResource("/music/Click.mp3").toString());
            MediaPlayer mediaPlayer2 = new MediaPlayer(mouseClicked);
            mediaPlayer2.setCycleCount(1);
            mediaPlayer2.play();
            mediaPlayer2.setVolume(0.3);
        }
    }

    public void LoadMaze(ActionEvent event) throws IOException {
        mouseAudio();
        MultipleSelectionModel mazeFile = listView.getSelectionModel();
        String mazeName = mazeFile.getSelectedItems().toString();
        mazeName = mazeName.substring(1,mazeName.length()-1);
        if (mazeFile.getSelectedItems().size() == 0)
            errorLabel.setText("Please choose a maze to load");
        else{
            MyViewController.setLoadedName(mazeName);
            MyViewController.setMazeType(false);
            MainMenuController.mediaPlayer.stop();
            MainMenuController.playing=false;
            Parent root2 = FXMLLoader.load(getClass().getClassLoader().getResource("MyView.fxml"));
            loadButton.getScene().setRoot(root2);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File directory = new File(System.getProperty("user.dir")+"/Saved_Mazes");

        if (!directory.exists()){
            directory.mkdirs();
        }
        String[] files;
        File[] contents = directory.listFiles();
        if (contents.length!=0) {
            for (File f : contents) {
                files = f.getPath().split("\\\\");
                listView.getItems().add(files[files.length-1]);
            }
        }
        mainImageView.fitWidthProperty().bind(main_pane.widthProperty());
        mainImageView.fitHeightProperty().bind(main_pane.heightProperty());

    }
}

