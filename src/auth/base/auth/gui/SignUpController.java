package auth.base.auth.gui;

import auth.base.db.DbUtils;
import auth.base.models.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private TextField first_name;
    @FXML
    private Button create_user;
    @FXML
    private Hyperlink back;
    @FXML
    private PasswordField user_password;
    @FXML
    private TextField last_name;
    @FXML
    private TextField new_email;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        create_user.setOnAction(event ->
                DbUtils.signUp(event,new_email.getText(),user_password.getText(),first_name.getText(),last_name.getText()));

        back.setOnAction(event ->
                DbUtils.changeScene(event,
                        "/MainWindow.fxml",
                        new User(null,null,null,null,null)));
    }


}
