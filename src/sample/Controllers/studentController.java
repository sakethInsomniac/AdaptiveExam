package sample.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.DAO.StudentDAO;

//import java.awt.*;
import javafx.scene.control.TextField;
import sample.DAO.studentDetails;
import sample.Main;
import sample.Utility.*;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class studentController implements Initializable {

    @FXML
    private TextField studentIDText;
    @FXML
    private TextField studentNameText;
    @FXML
    private TextField institutionNameText;
    @FXML
    private CheckBox termsBox;
    @FXML
    private Button insertStudentButton;
    @FXML
    private Button resetButton;

    private int OTP;

    @FXML
    private AnchorPane content2;

    @FXML
    private Label termslbl  ;


    @FXML
    private Label labelClose;

    @FXML
    private ComboBox<String> institutionName;

    ObservableList<String> list;

    private String stID;
    public void store()
    {
        stID=studentIDText.getText();
    }
    //Insert an employee to the DB
    @FXML
    private void insertStudent(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        try {
            Stage stage;
            Parent root;


            if(actionEvent.getSource()==insertStudentButton && termsBox.isSelected()==true ) {
                dbconnection connect=new dbconnection();
                store();
                try {
                    connect.connectiondb();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                studentDetails stu=new studentDetails(studentIDText.getText(), studentNameText.getText(), institutionName.getSelectionModel().getSelectedItem());
                StudentDAO.insertStu(studentIDText.getText(), studentNameText.getText(), institutionName.getSelectionModel().getSelectedItem());
               // OTP();
                //SwapScreen swap=new SwapScreen();
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("Views/OTP.fxml"));
                Parent pa = (AnchorPane) loader.load();
                OTPController out=new OTPController();
                out=loader.getController();
                out.vault(stID);
                Scene newScene = new Scene(pa);
                Stage newStage = (Stage) content2.getScene().getWindow();
                newStage.setScene(newScene);
                newStage.show();
                //swap.changeScene("Views/OTP.fxml",content2);



               // Label label = new Label("My Label");

            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please check the Terms and conditions : ", ButtonType.CLOSE);
                alert.showAndWait();
                return;

            }
        } catch (SQLException e) {
           //Show the error message
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unable to save Details : " + e.getMessage(), ButtonType.CLOSE);
            alert.showAndWait();
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //ResetFeilds
    @FXML
    private void ConditionsDisplay (MouseEvent mouseEvent)
    {

        if(mouseEvent.getSource()==termslbl) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "1.Please Complete All Sections \n " + "2.Please Write atleast one question to submit a section", ButtonType.CLOSE);
            alert.showAndWait();
            return;
        }
    }

    @FXML
    private void clearFeilds (ActionEvent actionEvent)
    {
        if(actionEvent.getSource()==resetButton)
        {
            studentIDText.clear();
            studentNameText.clear();
            //institutionNameText.clear();
            termsBox.setSelected(false);

        }
    }




    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        list = FXCollections.observableArrayList("Swinburne","Monash","CSU","CQU","VIT");
        institutionName.setItems(list);
    }




}//close controller
