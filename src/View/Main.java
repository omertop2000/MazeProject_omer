package View;

import Server.*;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Main extends Application {

    static boolean playing = false;
    @Override
    public void start(Stage primaryStage) throws Exception{
        if (MainMenuController.mediaPlayer != null)
            playing = MainMenuController.mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);
        Scene scene = FXMLLoader.load(getClass().getClassLoader().getResource("MainMenu.fxml"));
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        Configurations config = Configurations.getInstance();
        config.setConfigPath("resources/config.properties");
        Server mazeGeneratingServer = new Server(5400, 1000, new ServerStrategyGenerateMaze());
        Server solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
        launch(args);
        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();

    }
}
