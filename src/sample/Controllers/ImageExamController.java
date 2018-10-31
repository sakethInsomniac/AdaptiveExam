package sample.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.Main;
import sample.Utility.Helper;
import sample.Utility.SwapScreen;
import sample.Utility.dbconnection;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ImageExamController implements Initializable {

    private String url1, url2;
    @FXML
    private Label qLabel;
    @FXML
    private Label qType;
    @FXML
    private Label qCount, scoreLbl, overAllScoreLbl, mathsScoreLbl, lblTimer;
    @FXML
    private AnchorPane content;
    private String answer;
    private int score = 0, queCount = 1, matScore, ImgScore;
    @FXML
    private ImageView Imageview1, Imageview2;

    private String QueryMed = "Select Top 1 * from ImageExam where QType='Medium' order by newid()";
    private String QueryEasy = "Select Top 1 * from ImageExam where QType='Easy' order by newid()";
    private String QueryHard = "Select Top 1 * from ImageExam where QType='Hard' order by newid()";
    private ResultSet rs;
    private Timeline timeline;
    private Integer timeSeconds = Main.remainingTime;


    public void store() {

        Main.overallScoreGlobal = Main.overallScoreGlobal + score;
        Main.mathsScoreGlobal = Integer.parseInt(mathsScoreLbl.getText());
        Main.imageScoreGlobal = Integer.parseInt(scoreLbl.getText());
    }

    public void vault(int overallScore, int mathsScore) {
        Main.mathsScoreGlobal = Main.overallScoreGlobal;
        overAllScoreLbl.setText(String.valueOf(Main.overallScoreGlobal));
        mathsScoreLbl.setText(String.valueOf(Main.mathsScoreGlobal));

        System.out.println(overallScore);
    }

    @FXML
    private void MediumQue() {
        if (queCount == 5) {
            Main.overallScoreGlobal = Main.overallScoreGlobal + score;
            Main.mathsScoreGlobal = Integer.parseInt(mathsScoreLbl.getText());
            Main.imageScoreGlobal = score;

            SwapScreen swap = new SwapScreen();
            try {
                swap.changeScene("Views/ListeningExam.fxml", content);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        dbconnection db = new dbconnection();
        try {

            rs = db.dbExecuteQuery(QueryMed);
            if (rs.next()) {

                qLabel.setText(rs.getString("Question"));
                url1 = rs.getString("url1");
                url2 = rs.getString("url2");
                Image image1 = new Image(url1);
                // Setting the image view
                Imageview1.setImage(image1);

                Image image2 = new Image(url2);
                // Setting the image view
                Imageview2.setImage(image2);
                qType.setText(rs.getString("QType"));
                qType.setTextFill(Color.web("Blue"));

                qCount.setText("Question." + String.valueOf(queCount) + ":");
                scoreLbl.setText(String.valueOf(score));
                queCount++;

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void EasyQue() {
        if (queCount == 5) {
            Main.overallScoreGlobal = Main.overallScoreGlobal + score;
            Main.mathsScoreGlobal = Integer.parseInt(mathsScoreLbl.getText());
            Main.imageScoreGlobal = score;

            SwapScreen swap = new SwapScreen();
            try {
                swap.changeScene("Views/ListeningExam.fxml", content);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        dbconnection db = new dbconnection();
        try {

            rs = db.dbExecuteQuery(QueryEasy);
            if (rs.next()) {

                qLabel.setText(rs.getString("Question"));
                url1 = rs.getString("url1");
                url2 = rs.getString("url2");
                Image image1 = new Image(url1);
                // Setting the image view
                Imageview1.setImage(image1);
                Image image2 = new Image(url2);
                // Setting the image view
                Imageview2.setImage(image2);
                qType.setText(rs.getString("QType"));
                qType.setTextFill(Color.web("Green"));

                qCount.setText("Question." + String.valueOf(queCount) + ":");
                scoreLbl.setText(String.valueOf(score));
                queCount++;

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void HardQue() {
        if (queCount == 5) {
            Main.overallScoreGlobal = Main.overallScoreGlobal + score;
            Main.mathsScoreGlobal = Integer.parseInt(mathsScoreLbl.getText());
            Main.imageScoreGlobal = score;

            //save timer current values to global variables and to the next screen
            Main.remainingTime = timeSeconds;
            timeline.stop();

            SwapScreen swap = new SwapScreen();
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/ListeningExam.fxml"));
                Parent pa = (AnchorPane) loader.load();
                ListeningExam out = new ListeningExam();
                out = loader.getController();
                ImgScore = score - matScore;
                out.vault(Main.overallScoreGlobal, Main.mathsScoreGlobal, Main.imageScoreGlobal);
                Scene newScene = new Scene(pa);
                Stage newStage = (Stage) content.getScene().getWindow();
                newStage.setScene(newScene);
                newStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        dbconnection db = new dbconnection();
        try {

            rs = db.dbExecuteQuery(QueryHard);
            if (rs.next()) {

                qLabel.setText(rs.getString("Question"));
                url1 = rs.getString("url1");
                url2 = rs.getString("url2");
                Image image1 = new Image(url1);
                // Setting the image view
                Imageview1.setImage(image1);
                Image image2 = new Image(url2);
                // Setting the image view
                Imageview2.setImage(image2);
                qType.setText(rs.getString("QType"));
                qType.setTextFill(Color.web("RED"));

                qCount.setText("Question." + String.valueOf(queCount) + ":");
                scoreLbl.setText(String.valueOf(score));
                queCount++;

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void changeQuestion(String check) {
        if (qType.getText().equals("Easy") && queCount < 6) {
            if (MatchAnswer(check)) {
                score += 2;
                MediumQue();
            } else {
                EasyQue();
            }
        } else if (qType.getText().equals("Medium") && queCount < 6) {
            if (MatchAnswer(check)) {
                score += 5;
                HardQue();
            } else {
                EasyQue();
            }
        } else if (qType.getText().equals("Hard") && queCount < 6) {
            if (MatchAnswer(check)) {
                score += 10;
                HardQue();
            } else {
                MediumQue();
            }

        }


    }

    private boolean MatchAnswer(String check) {
        dbconnection db = new dbconnection();
        try {
            String query = "select * from imageExam where Question='" + qLabel.getText() + "'";
            rs = db.dbExecuteQuery(query);
            if (rs.next()) {


                return check.equals(rs.getString("Answer"));


            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    //ImageClick
    @FXML
    private void AnswerClick(MouseEvent mouseEvent) {


        if (mouseEvent.getSource() == Imageview1) {
            answer = "1";
        } else if (mouseEvent.getSource() == Imageview2) {
            answer = "2";
        }
        changeQuestion(answer);


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
