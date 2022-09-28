package auth.base.auth.gui;

import auth.base.db.DbUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Button log_in;
    public TextField user_email;
    public PasswordField user_password;
    @FXML
    private Label lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log_in.setOnAction(event ->
                DbUtils.login(event,
                        user_email.getText(),
                        user_password.getText()
                ));
    }
}
