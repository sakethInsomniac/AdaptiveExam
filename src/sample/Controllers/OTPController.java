package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.*;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.scene.layout.AnchorPane;
import sample.Main;
import sample.Utility.*;
public class OTPController implements Initializable {

    private int OTP;

    @FXML
    private Label OTPlbl;
    @FXML
    private Label test;


    @FXML
    private Button ProcButuuon;

    @FXML
    private Button backButton;
    @FXML
    private AnchorPane content;

private String studentID;
private String otpst;
private  String stIDlog;

    public void store()
    {
        otpst=OTPlbl.getText();
        stIDlog=test.getText();
    }
//Screen Swaps for OTP Page
    public void swapScreensOTP(ActionEvent actionEvent) {
        SwapScreen swap=new SwapScreen();


        //get the button object on which user clicks and change scene acoording to that
        if(actionEvent.getSource().equals(ProcButuuon)){
            try {
                store();
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/Login.fxml"));
                Parent pa = (AnchorPane) loader.load();
                LoginController out=new LoginController();
                out=loader.getController();
                out.vault(stIDlog,otpst);
                Scene newScene = new Scene(pa);
                Stage newStage = (Stage) content.getScene().getWindow();
                newStage.setScene(newScene);
                newStage.show();
                //swap.changeScene("Views/Login.fxml",content);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(actionEvent.getSource().equals(backButton)){
            try {
                swap.changeScene("Views/Register.fxml",content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
/*
    public void LoginCheck(ActionEvent actionEvent) {
        SwapScreen swap=new SwapScreen();


        //get the button object on which user clicks and change scene acoording to that
        if(actionEvent.getSource().equals(loginButton)){
            try {
                studentDetails stu =new studentDetails();

                if(stu.getStudentID().equals(stdIDTxt.getText()) && OTP==Integer.parseInt(OTPtxt.getText())) {
                    swap.changeScene("Views/Home.fxml", contentlogin);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(actionEvent.getSource().equals(registerButton)){
            try {
                swap.changeScene("Views/Register.fxml",contentlogin);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


*/

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Random num=new Random();
        OTP=num.nextInt(9999999);
        OTPlbl.setText(String.valueOf(OTP));

        // TODO
    }

    public void vault(String ID)
    {
        studentID=ID;
        test.setText(ID);
        System.out.println(studentID);
    }
}

