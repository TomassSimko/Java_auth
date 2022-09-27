package auth.base.auth.gui;

import auth.base.db.DbUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Button btn_logout;
    @FXML
    private TextField user_id;
    @FXML
    private TextField user_email;
    @FXML
    private PasswordField user_psw;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_logout.setOnAction(event ->
                DbUtils.changeScene(event,
                        "/MainWindow.fxml",
                        null,
                        null));

    }

    @FXML
    public void setUser(String email,String password){
       //  user_id.setText(id);
        user_email.setText(email);
        user_psw.setText(password);
    }
}
