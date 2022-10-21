package gui.controller;

import be.User;
import bll.IUserManager;
import dal.db.DbUtilities;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    @FXML
    private Button delete_user;
    @FXML
    private Button update_psw;
    @FXML
    private PasswordField user_new_psw;
    @FXML
    private Button btn_logout;
    @FXML
    private Button put_changes;
    @FXML
    private Label full_name;
    @FXML
    private TextField user_id;
    @FXML
    private TextField user_email;
    @FXML
    private PasswordField user_psw;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;

    private IUserManager userManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_logout.setOnAction(event ->
                DbUtilities.changeScene(event,
                        "/MainWindow.fxml", null));

    }

    @FXML
    public void setUser(User u){
      //  user_id.setText(u.getUserId());
        user_email.setText(u.getEmail());
        user_psw.setText(u.getEncryptedPassword());
        first_name.setText(u.getFirstName());
        last_name.setText(u.getLastName());
       //  full_name.setText(u.getFullName());
    }
}
