package sample.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.Main;
import sample.Utility.Helper;
import sample.Utility.SwapScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WritingExamController implements Initializable {

    private static final String QuestionLable_HARD = ",  Joe   waiting      While    reason     .      realized     the ";
    private static final String QuestionLable_HARD_ANSWER = "While waiting, Joe realized the reason.";
    private static final String QuestionLable_Medium = "arrived     at     Mary     the     before  me    and   station ,   left .";
    private static final String QuestionLable_Medium_ANSWER = "Mary arrived at the station, and left before me.";
    private static final String QuestionLable_Easy = "train    The    late  was  .";
    private static final String QuestionLable_Easy_ANSWER = "The train was late.";
    private static final String EASY = "Easy";
    private static final String MEDIUM = "Medium";
    private static final String HARD = "Hard";
    private static final String NEXT = "Next";
    private Timeline timeline;
    private Integer timeSeconds = Main.remainingTime;

    @FXML
    private Label qLabel, qType, scoreLbl, overallLbl, mathslbl, imgScoreLbl, ListeningLbl, spellingLbl, lblTimer;
    @FXML
    private AnchorPane content;
    private int score = 0;
    @FXML
    private TextArea answerTbox;
    @FXML
    private Button btnWritingSubmit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        overallLbl.setText(String.valueOf(Main.overallScoreGlobal));
        mathslbl.setText(String.valueOf(Main.mathsScoreGlobal));
        imgScoreLbl.setText(String.valueOf(Main.imageScoreGlobal));
        ListeningLbl.setText(String.valueOf(Main.listeningScoreGlobal));
        spellingLbl.setText(String.valueOf(Main.spellingScoreGlobal));
        lblTimer.setText(Helper.ConvertSecondToHHMMSSString(timeSeconds));
        EasyQuestion();

        getTimerData();
    }

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
                                System.out.println("timer stopped");
                            }
                        }));
        timeline.playFromStart();
    }

    @FXML
    private void EasyQuestion() {
        setQuestionOperationsParameters(QuestionLable_Easy, EASY, "Blue");
    }

    private void setQuestionOperationsParameters(String questionLable, String questionType, String difficultyColor) {
        qLabel.setText("Write a sentence from following Words:\n " + questionLable);
        qType.setText(questionType);
        qType.setTextFill(Color.web(difficultyColor));
        answerTbox.clear();
    }

    private boolean MatchAnswer(String questionLevel) {
        String answer = answerTbox.getText().toLowerCase().trim();
        boolean answerResult = false;
        switch (questionLevel) {
            case EASY:
                if (QuestionLable_Easy_ANSWER.trim().equalsIgnoreCase(answer)) {
                    answerResult = true;
                }
                break;
            case MEDIUM:
                if (QuestionLable_Medium_ANSWER.trim().equalsIgnoreCase(answer)) {
                    answerResult = true;
                }
                break;
            case HARD:
                if (QuestionLable_HARD_ANSWER.trim().equalsIgnoreCase(answer)) {
                    answerResult = true;
                }
                break;
            default:
                break;
        }
        return answerResult;
    }

    private void MediumQue() {
        setQuestionOperationsParameters(QuestionLable_Medium, MEDIUM, "Green");
    }

    private void HardQue() {
        setQuestionOperationsParameters(QuestionLable_HARD, HARD, "Red");
    }

    @FXML
    private void changeQuestion(ActionEvent actionEvent) {

        if (qType.getText().equals(EASY) && btnWritingSubmit.getText().equals(NEXT)) {
            if (MatchAnswer(EASY)) {
                score += 2;
                System.out.println("sentence matched..Score:" + score);
                MediumQue();
            } else {
                MediumQue();
                System.out.println("sentence not matched..Score:" + score);
            }
        } else if (qType.getText().equals(MEDIUM) && btnWritingSubmit.getText().equals(NEXT)) {
            if (MatchAnswer(MEDIUM)) {
                score += 5;
                System.out.println("sentence matched..Score:" + score);
                HardQue();
            } else {
                HardQue();
                System.out.println("sentence not matched..Score:" + score);
            }
        } else if (qType.getText().equals(HARD) && btnWritingSubmit.getText().equals(NEXT)) {
            if (MatchAnswer(HARD)) {
                score += 10;
                System.out.println("sentence matched..Score:" + score);
            } else {
                System.out.println("sentence not matched..Score:" + score);
            }
            qLabel.setText("Click on submit for next exam");
            answerTbox.setVisible(false);
            scoreLbl.setText(String.valueOf(score));
            btnWritingSubmit.setText("Submit");
        } else if (btnWritingSubmit.getText().equalsIgnoreCase("Submit")) {
            Main.overallScoreGlobal = Main.overallScoreGlobal + score;
            Main.writingScoreGlobal=score;
            SwapScreen swap = new SwapScreen();

            try {

               // Main.spellingScoreGlobal = score;
                //save timer current values to global variables and to the next screen
                Main.remainingTime = timeSeconds;

                timeline.stop();
               // Main.overallScoreGlobal=Main.overallScoreGlobal+score;

                swap.changeScene("Views/Scorecard.fxml", content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scoreLbl.setText(String.valueOf(score));
    }
}
