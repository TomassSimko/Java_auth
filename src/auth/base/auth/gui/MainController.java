package auth.base.auth.gui;

import auth.base.db.DbUtils;
import auth.base.models.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public Hyperlink sign_up;
    @FXML
    private Button log_in;
    @FXML
    private TextField user_email;
    @FXML
    private  PasswordField user_password;
    @FXML
    private Label lbl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log_in.setOnAction(event ->
                DbUtils.login(event,
                        user_email.getText(),
                        user_password.getText()
                ));
        sign_up.setOnAction(event ->
                DbUtils.changeScene(event,
                        "/SignUpWindow.fxml",
                        new User(null,null,null,null,null)));
    }
}
