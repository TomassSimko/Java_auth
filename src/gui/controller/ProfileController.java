package gui.controller;

import gui.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {

    @FXML
    private TextField file_absolute_path;
    private UserModel userModel;

    UsersController parentController;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }
    public void setController(UsersController usersController) {
        this.parentController = usersController;
    }

    @FXML
    private void createUserAction(ActionEvent event) {
      userModel.addUser("email","password");
      parentController.refresh();
   }
   @FXML
   private void openFileChoose(ActionEvent event){
       FileChooser fileChooser = new FileChooser();
       fileChooser.getExtensionFilters().addAll(
               new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"),
               new FileChooser.ExtensionFilter("PNG Files", "*.png")
       );

       File file = fileChooser.showOpenDialog(null);
       if (file != null) {
           String path = file.getAbsolutePath();
           file_absolute_path.setText(path);
       }
   }
}
