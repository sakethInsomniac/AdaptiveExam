package sample.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.Main;
import sample.Utility.FileUtility;
import sample.Utility.Helper;
import sample.Utility.SwapScreen;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SpellingExamController implements Initializable {

    private static final String QuestionLable_HARD = "Write a word that starts with Z";
    private static final String QuestionLable_Medium = "Write a word that starts with J";
    private static final String QuestionLable_Easy = "Write a word that starts with G";
    private static final String EASY = "Easy";
    private static final String MEDIUM = "Medium";
    private static final String HARD = "Hard";
    private static final String NEXT = "Next";
    private Timeline tenSecondsHelpTimer;
    private boolean isHelpUsed = false;
    private Timeline timeline;
    private Integer timeSeconds = Main.remainingTime;


    @FXML
    private Label qLabel, qType, scoreLbl, lblSpellHint, overallLbl, mathslbl, imgScoreLbl, ListeningLbl, lblTimer;
    @FXML
    private AnchorPane content;
    private int score = 0, queCount = 0;
    @FXML
    private TextField answerTbox;
    @FXML
    private Button btnSpellSubmit;
    private ArrayList<String> spellWordsList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            overallLbl.setText(String.valueOf(Main.overallScoreGlobal));
            mathslbl.setText(String.valueOf(Main.mathsScoreGlobal));
            imgScoreLbl.setText(String.valueOf(Main.imageScoreGlobal));
            ListeningLbl.setText(String.valueOf(Main.listeningScoreGlobal));

            lblTimer.setText(Helper.ConvertSecondToHHMMSSString(timeSeconds));
            getTimerData();

            tenSecondsHelpTimer = new Timeline(new KeyFrame(Duration.seconds(20),
                    event -> {
                        System.out.println("this is 30 seconds help timer");
                        getSuggestion();
                    }));
            fetchDataFromFile();
            System.out.println("Spelling list \n" + spellWordsList);
            EasyQuestion();


        } catch (IOException e) {
            System.out.println("e = " + e);
        }
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
        lblSpellHint.setVisible(false);
        qLabel.setText(questionLable);
        qType.setText(questionType);
        qType.setTextFill(Color.web(difficultyColor));
        answerTbox.clear();
        queCount++;
        isHelpUsed = false;
        tenSecondsHelpTimer.setCycleCount(Timeline.INDEFINITE);
        tenSecondsHelpTimer.stop();
        tenSecondsHelpTimer.playFromStart();
    }

    private boolean MatchAnswer(String questionLevel) {
        String answer = answerTbox.getText().toLowerCase().trim();
        boolean answerResult = false;
        switch (questionLevel) {
            case EASY:
                for (String spellWord : spellWordsList)
                    if (spellWord.toLowerCase().trim().charAt(0) == 'g' && spellWord.trim().equalsIgnoreCase(answer)) {
                        answerResult = true;
                    }
                break;
            case MEDIUM:
                for (String spellWord : spellWordsList)
                    if (spellWord.toLowerCase().trim().charAt(0) == 'j' && spellWord.trim().equalsIgnoreCase(answer)) {
                        answerResult = true;
                    }
                break;
            case HARD:
                for (String spellWord : spellWordsList)
                    if (spellWord.toLowerCase().trim().charAt(0) == 'z' && spellWord.trim().equalsIgnoreCase(answer)) {
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

        if (qType.getText().equals(EASY) && btnSpellSubmit.getText().equals(NEXT)) {
            if (MatchAnswer(EASY)) {
                score += isHelpUsed ? 1 : 2;
                System.out.println("word matched..Score:" + score);
                MediumQue();
            } else {
                MediumQue();
                System.out.println("word not found..Score:" + score);
            }
        } else if (qType.getText().equals(MEDIUM) && btnSpellSubmit.getText().equals(NEXT)) {
            if (MatchAnswer(MEDIUM)) {
                score += isHelpUsed ? 5 : 2;
                System.out.println("word matched..Score:" + score);
                HardQue();
            } else {
                HardQue();
                System.out.println("word not found..Score:" + score);
            }
        } else if (qType.getText().equals(HARD) && btnSpellSubmit.getText().equals(NEXT)) {
            if (MatchAnswer(HARD)) {
                score += isHelpUsed ? 10 : 5;
                System.out.println("word matched..Score:" + score);
            } else {
                System.out.println("word not found..Score:" + score);
            }
            qLabel.setText("Click on submit for next exam");
            lblSpellHint.setText("");
            answerTbox.setVisible(false);
            scoreLbl.setText(String.valueOf(score));
            btnSpellSubmit.setText("Submit");
        } else if (btnSpellSubmit.getText().equalsIgnoreCase("Submit")) {
            System.out.println("timer stopped");
            tenSecondsHelpTimer.stop();

            SwapScreen swap = new SwapScreen();
            try {
                Main.overallScoreGlobal = Main.overallScoreGlobal + score;
                Main.spellingScoreGlobal = score;
                //save timer current values to global variables and to the next screen
                Main.remainingTime=timeSeconds;
                timeline.stop();
                swap.changeScene("Views/writing.fxml", content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        scoreLbl.setText(String.valueOf(score));
    }

    private void getSuggestion() {

        String hint = "";

        if (qType.getText().equalsIgnoreCase(EASY)) {
            hint = "gue_ _ :(a person who is invited to visit someone's home or attend a particular social occasion.)";
        } else if (qType.getText().equalsIgnoreCase(MEDIUM)) {
            hint = "Jok_ _ :(a person who is fond of joking.)";
        } else if (qType.getText().equalsIgnoreCase(HARD)) {
            hint = "Zebra_ _ :(an African wild horse with black-and-white stripes)";
        }
        lblSpellHint.setVisible(true);
        lblSpellHint.setText("Hint: " + hint);
        isHelpUsed = true;
        tenSecondsHelpTimer.stop();
        System.out.println("timer excuted");
    }


    /**
     * fetches all information from files provided. if file is customer.txt then all information about customer is fetched
     * and stored in customer array list
     * if its vehicle then first type is checked and then depending on type, vehicle data is stored.
     *
     * @throws IOException {@link IOException} is thrown if any file reading error occurred.
     */
    private void fetchDataFromFile() throws IOException {
        ArrayList<String> data = FileUtility.readFromFile("SpellWords.txt");
        this.spellWordsList.addAll(data);
    }
}
