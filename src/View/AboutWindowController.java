package View;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
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

import static View.OptionsWindowController.isMusic;

public class AboutWindowController implements Initializable, Observer {
    public Stage currStage;
    public AnchorPane mainPane;
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

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mainImageView.fitWidthProperty().bind(mainPane.widthProperty());
        mainImageView.fitHeightProperty().bind(mainPane.heightProperty());
    }
}
