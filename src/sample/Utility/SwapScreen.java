package sample.Utility;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class SwapScreen {
    public  void changeScene (String sceneName, AnchorPane anchor) throws IOException
    {

        Parent parent = (AnchorPane) FXMLLoader.load(Main.class.getResource(sceneName));
        Scene newScene = new Scene(parent);
        Stage newStage = (Stage) anchor.getScene().getWindow();
        newStage.setScene(newScene);
        newStage.show();


    }
}
