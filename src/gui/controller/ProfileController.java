package gui.controller;

import bll.db.DbUtils;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btn_logout.setOnAction(event ->
                DbUtils.changeScene(event,
                        "/MainWindow.fxml",
                        null,
                        null,
                        null,
                        null,
                        null));
        put_changes.setOnAction(event ->
                DbUtils.updateUser(event,user_id.getText(),user_email.getText(),first_name.getText(),last_name.getText()));

        delete_user.setOnAction(event ->
                DbUtils.deleteUser(event,user_id.getText()));
        update_psw.setOnAction(event ->
                DbUtils.updatePsw(event,user_id.getText(),user_psw.getText(),user_new_psw.getText()));

    }

    @FXML
    public void setUser(String id,String email,String password,String firstName,String lastName){
        String fullName = firstName + " " + lastName;
        user_id.setText(id);
        user_email.setText(email);
        user_psw.setText(password);
        first_name.setText(firstName);
        last_name.setText(lastName);
        full_name.setText(fullName);

    }
}
