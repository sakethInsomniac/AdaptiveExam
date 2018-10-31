package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.Utility.*;
import sample.DAO.*;
import sample.Controllers.*;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField stdIDTxt;
    @FXML
    private TextField OTPtxt;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private AnchorPane contentlogin;
    @FXML
    private Label lbl1;
    @FXML
    private Label lbl2;


    public void LoginCheck(ActionEvent actionEvent) {
        SwapScreen swap=new SwapScreen();


        //get the button object on which user clicks and change scene acoording to that
        if(actionEvent.getSource()==loginButton){
            try {
                studentDetails stu =new studentDetails();
                System.out.println(lbl1.getText());
                System.out.println(lbl2.getText());
                System.out.println(stdIDTxt.getText());
                System.out.println(OTPtxt.getText());
                String stID=stdIDTxt.getText();
                String stlbl=lbl1.getText();
                String otpID=OTPtxt.getText();
                String otplbl=lbl2.getText();

                if(stID.equals(stlbl) && otpID.equals(otplbl))
                {

                    swap.changeScene("Views/Home.fxml", contentlogin);
                }
                else { Alert alert = new Alert(Alert.AlertType.ERROR, "User ID or Password Wrong" , ButtonType.CLOSE);
                    alert.showAndWait();
                    return;}

            } catch (IOException e) { Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to save Details : " + e.getMessage(), ButtonType.CLOSE);
                alert.showAndWait();
                return;
                //e.printStackTrace();
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        // TODO
    }
    public void vault(String ID,String OTP)
    {

        lbl1.setText(ID);
        lbl2.setText(OTP);
        System.out.println(ID);
        System.out.println(OTP);
    }
}
