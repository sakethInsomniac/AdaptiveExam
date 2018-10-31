package sample.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.Utility.Helper;
import sample.Utility.dbconnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MathsExamController implements Initializable {

    @FXML
    private Label qLabel, qType, qCount, scoreLbl, lblTimer;
    @FXML
    private AnchorPane content;
    private int score = 0, mathsScore = 0, queCount = 0, overallScore = 0;
    @FXML
    private TextField answerTbox;
    @FXML
    private Button nxtButton;
    private String QueryMed = "Select Top 1 * from Maths where QType='Medium' order by newid()";
    private String QueryEasy = "Select Top 1 * from Maths where QType='Easy' order by newid()";
    private String QueryHard = "Select Top 1 * from Maths where QType='Hard' order by newid()";
    private ResultSet rs;
    private Timeline timeline;
    private Integer timeSeconds = Main.STARTTIME;


    public void store() {
        Main.overallScoreGlobal = Integer.parseInt(scoreLbl.getText());
        Main.mathsScoreGlobal = Integer.parseInt(scoreLbl.getText());
    }

    @FXML
    private void MediumQue() {
        dbconnection db = new dbconnection();
        try {

            rs = db.dbExecuteQuery(QueryMed);
            if (rs.next()) {

                qLabel.setText(rs.getString("Question"));
                qType.setText(rs.getString("QType"));
                qType.setTextFill(Color.web("Blue"));
                answerTbox.clear();
                queCount++;
                qCount.setText("Question." + String.valueOf(queCount) + ":");
                scoreLbl.setText(String.valueOf(score));
                if (queCount == 4) {
                    nxtButton.setText("Submit");
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean MatchAnswer() {
        dbconnection db = new dbconnection();
        try {
            String query = "select * from Maths where Question='" + qLabel.getText() + "'";
            rs = db.dbExecuteQuery(query);
            if (rs.next()) {

                return answerTbox.getText().equals(rs.getString("Answer"));


            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void EasyQue() {
        dbconnection db = new dbconnection();
        try {

            rs = db.dbExecuteQuery(QueryEasy);
            if (rs.next()) {

                qLabel.setText(rs.getString("Question"));
                qType.setText(rs.getString("QType"));
                qType.setTextFill(Color.web("Green"));
                answerTbox.clear();
                queCount++;
                qCount.setText("Question." + String.valueOf(queCount) + ":");
                scoreLbl.setText(String.valueOf(score));
                if (queCount == 4) {
                    nxtButton.setText("Submit");
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void HardQue() {
        dbconnection db = new dbconnection();
        try {

            rs = db.dbExecuteQuery(QueryHard);
            if (rs.next()) {

                qLabel.setText(rs.getString("Question"));
                qType.setText(rs.getString("QType"));
                qType.setTextFill(Color.web("Red"));
                answerTbox.clear();
                queCount++;
                qCount.setText("Question." + String.valueOf(queCount) + ":");
                scoreLbl.setText(String.valueOf(score));
                if (queCount == 4) {
                    nxtButton.setText("Submit");
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void changeQuestion(ActionEvent actionEvent) {
        if (qType.getText().equals("Easy") && nxtButton.getText().equals("Next")) {
            if (MatchAnswer()) {
                score += 2;

                MediumQue();
            } else {
                EasyQue();
            }
        } else if (qType.getText().equals("Medium") && nxtButton.getText().equals("Next")) {
            if (MatchAnswer()) {
                score += 5;

                HardQue();
            } else {
                EasyQue();
            }
        } else if (qType.getText().equals("Hard") && nxtButton.getText().equals("Next")) {
            if (MatchAnswer()) {
                score += 10;

                HardQue();
            } else {
                MediumQue();
            }

        } else if (nxtButton.getText().equals("Submit")) {
            switch (qType.getText()) {
                case "Easy":
                    if (MatchAnswer()) {
                        score += 2;
                    }
                    break;
                case "Medium":
                    if (MatchAnswer()) {
                        score += 5;
                    }
                    break;
                case "Hard":
                    if (MatchAnswer()) {
                        score += 10;
                    }
                    break;
            }
            Main.overallScoreGlobal = score;
            Main.mathsScoreGlobal = score;
            //SwapScreen swap=new SwapScreen();

            //save timer current values to global variables and to the next screen
            Main.remainingTime = timeSeconds;
            timeline.stop();
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/ImageExam.fxml"));
                Parent pa = (AnchorPane) loader.load();
                ImageExamController out = new ImageExamController();
                out = loader.getController();
                out.vault(Main.overallScoreGlobal, Main.mathsScoreGlobal);
                Scene newScene = new Scene(pa);
                Stage newStage = (Stage) content.getScene().getWindow();
                newStage.setScene(newScene);
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lblTimer.setText(Helper.ConvertSecondToHHMMSSString(timeSeconds));
        MediumQue();
        getTimerData();
    }

    /**
     * properties for timer
     */
    private void getTimerData() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        // KeyFrame event handler
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            timeSeconds--;
                            // update timerLabel
                            lblTimer.setText(Helper.ConvertSecondToHHMMSSString(timeSeconds));
                            if (timeSeconds <= 0) {
                                timeline.stop();
                                System.out.println("main timer stopped");
                            }
                        }));
        timeline.playFromStart();
    }
}
