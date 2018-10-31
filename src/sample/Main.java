package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static int STARTTIME = 900;           //15 minute = 900 seconds
    public static int remainingTime = 0;
    public static int mathsScoreGlobal;
    public static int imageScoreGlobal;
    public static int listeningScoreGlobal;
    public static int writingScoreGlobal;
    public static int spellingScoreGlobal;
    public static int overallScoreGlobal;
    static Stage stage = null;

    public static void main(String[] args) {


        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Views/ListeningExam.fxml"));
        primaryStage.setTitle("Adaptive Exam  Assignment 1");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


}
