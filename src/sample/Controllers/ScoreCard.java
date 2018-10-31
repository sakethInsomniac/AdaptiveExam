package sample.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import sample.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class ScoreCard implements Initializable
{
    @FXML
    private Label matLbl,imgLbl,overAllscoreLbl,ListeningLbl,spellLbl,writelbl;

    @FXML
    private BarChart<?, ?> barChart;

    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;


    public void vault()
    {

        overAllscoreLbl.setText(String.valueOf(Main.overallScoreGlobal));
        matLbl.setText(String.valueOf(Main.mathsScoreGlobal));
        imgLbl.setText(String.valueOf(Main.imageScoreGlobal));
        ListeningLbl.setText(String.valueOf(Main.listeningScoreGlobal));
        spellLbl.setText(String.valueOf(Main.spellingScoreGlobal));
        writelbl.setText(String.valueOf(Main.writingScoreGlobal));
        //System.out.println(overallScore);
        XYChart.Series series1 = new XYChart.Series<>();
        series1.setName("Exam Scores");
        series1.getData().add(new XYChart.Data("Maths", Integer.valueOf(matLbl.getText())));
        series1.getData().add(new XYChart.Data("Image",Integer.valueOf( imgLbl.getText())));
        series1.getData().add(new XYChart.Data("Listening", Integer.valueOf(ListeningLbl.getText())));
        series1.getData().add(new XYChart.Data("Writing", Integer.valueOf(spellLbl.getText())));
        series1.getData().add(new XYChart.Data("Spelling", Integer.valueOf(writelbl.getText())));
        barChart.getData().addAll(series1);



    }



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        vault();
        System.out.println("sample");
    }
}
