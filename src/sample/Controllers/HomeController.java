package sample.Controllers;

import javafx.animation.Animation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.*;
import javafx.scene.text.*;
import javafx.scene.transform.*;
import javafx.animation.*;
import javafx.scene.shape.*;
// Java Legacy
import java.util.*;

import javafx.util.Duration;
import sample.Utility.*;



public class HomeController implements Initializable {

    @FXML
    private Label time,date;

    @FXML
    private ImageView img1,img2,img3,img4,img5;

    private int  second,minute,hour;

    @FXML
    private AnchorPane content;


    //Display Terms and Conditions
    @FXML
    private void ConditionsDisplay (MouseEvent mouseEvent)
    {

        if(mouseEvent.getSource()==img1 || mouseEvent.getSource()==img2 || mouseEvent.getSource()==img3 || mouseEvent.getSource()==img4||mouseEvent.getSource()==img5 )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "1.Write atleast one question \n " + "2.All the Best", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }

    }

    @FXML
    private void startExam(ActionEvent actionEvent)
    {
        SwapScreen swap=new SwapScreen();
        try {
            swap.changeScene("Views/Exam.fxml",content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void imageHover(MouseEvent mouseEvent)
    {
        //Reflection effect
        Reflection reflection = new Reflection();

        reflection.setFraction(0.7);
        if(mouseEvent.getSource()==img1)
        {
            img1.setEffect(reflection);
            img2.setEffect((null));
            img3.setEffect((null));
            img4.setEffect((null));
            img5.setEffect((null));

        }
        else if(mouseEvent.getSource()==img2)
        {
            img2.setEffect((reflection));
            img1.setEffect((null));
            img3.setEffect((null));
            img4.setEffect((null));
            img5.setEffect((null));
        }
        else if(mouseEvent.getSource()==img3)
        {
            img3.setEffect((reflection));
            img2.setEffect((null));
            img1.setEffect((null));
            img4.setEffect((null));
            img5.setEffect((null));
        }
        else if(mouseEvent.getSource()==img4)
        {
            img4.setEffect((reflection));
            img2.setEffect((null));
            img3.setEffect((null));
            img1.setEffect((null));
            img5.setEffect((null));
        }
        else if(mouseEvent.getSource()==img5)
        {
            img5.setEffect((reflection));
            img2.setEffect((null));
            img3.setEffect((null));
            img4.setEffect((null));
            img1.setEffect((null));
        }

    }



    //DigitalClock clock=new DigitalClock(450,100,25,25);


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            second = LocalDateTime.now().getSecond();
            minute = LocalDateTime.now().getMinute();
            hour = LocalDateTime.now().getHour();
            time.setText(hour + ":" + (minute) + ":" + second);
            date.setText(LocalDate.now().toString());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();


    }
}
