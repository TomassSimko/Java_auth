package gui.controller;

import be.User;
import dal.db.DbUtilities;
import gui.models.UserModel;
import javafx.event.ActionEvent;
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

    private DbUtilities utilities;

    // testing model
    private UserModel userModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userModel = new UserModel();
        var test = userModel.getUserList();
        for (User u : test){
            System.out.println(u);
        }

        utilities = new DbUtilities();
//        log_in.setOnAction(event ->
//                DbUtilities.login(event,
//                        user_email.getText(),
//                        user_password.getText()
//                ));
        log_in.setOnAction(this::auth);
    }

    private void auth(ActionEvent event) {
        if(!user_email.getText().isBlank() || !user_password.getText().isBlank()) {
            utilities.login(event,user_email.getText(),user_password.getText());
        }else {
            System.out.println("Username or Password cannot be empty");
        }
    }

}
