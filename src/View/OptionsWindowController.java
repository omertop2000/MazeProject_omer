package View;

import Server.Configurations;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import static View.MiddleSceneController.isNumeric;

public class OptionsWindowController implements Initializable, Observer {

    public Stage currStage;
    public AnchorPane mainPane;
    public ImageView mainImageView;
    public RadioButton Type1,Type2,Type3,Alg1,Alg2,Alg3;
    public TextField poolSize;

    public static boolean musicChecked = true;
    public CheckBox sKey;

    public static boolean isMusic(){
        return musicChecked;
    }

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

    public void ApplyButton(ActionEvent event) throws IOException {
        mouseAudio();
        Configurations config = Configurations.getInstance();
        if (isNumeric(poolSize.getText()) && Integer.parseInt(poolSize.getText()) >= 0)
            config.setProperty("threadPoolSize",poolSize.getText());
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("invalid Threadpool input");
            alert.setContentText("Please enter a whole number above 0 in the text field");
            alert.setGraphic(null);
            alert.setHeaderText("");
            alert.show();
            return;
        }
        if (Type1.isSelected())
            config.setProperty("mazeGeneratingAlgorithm","MyGenerator");
        else if (Type2.isSelected())
            config.setProperty("mazeGeneratingAlgorithm","Simple");
        else
            config.setProperty("mazeGeneratingAlgorithm","Empty");

        if (Alg1.isSelected())
            config.setProperty("mazeSearchingAlgorithm","BFS");
        else if (Alg2.isSelected())
            config.setProperty("mazeSearchingAlgorithm","DFS");
        else
            config.setProperty("mazeSearchingAlgorithm","Best");

        if (sKey.isSelected()){
            musicChecked = true;
        }
        else{
            MainMenuController.mediaPlayer.stop();
            musicChecked = false;
            MainMenuController.playing = false;
        }

        Scene root = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
        currStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currStage.setScene(root);
        currStage.show();
    }


    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Configurations config = Configurations.getInstance();

        String type = config.getmaze_gen_type();
        if (type.equals("MyGenerator")){
            Type1.setSelected(true);
        }
        else if(type.equals("Simple"))
            Type2.setSelected(true);
        else
            Type3.setSelected(true);

        String algo = config.getmaze_sol_type();
        if (algo.equals("BFS"))
            Alg1.setSelected(true);
        else if (algo.equals("DFS"))
            Alg2.setSelected(true);
        else
            Alg3.setSelected(true);

        String poolNum = config.get_t_pool_size();
        poolSize.setText(poolNum);

        if (musicChecked == true)
            sKey.setSelected(true);
        else
            sKey.setSelected(false);

        mainImageView.fitWidthProperty().bind(mainPane.widthProperty());
        mainImageView.fitHeightProperty().bind(mainPane.heightProperty());
    }
}
