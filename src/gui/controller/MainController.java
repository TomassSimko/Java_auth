package gui.controller;

import be.User;
import gui.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
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
//        fetchUsers();

        log_in.setOnAction(event -> {
            try {
                auth(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
//        log_in.setOnAction(event ->
//                DbUtilities.login(event,
//                        user_email.getText(),
//                        user_password.getText()
//                ));
    }


    private void auth(ActionEvent event) throws IOException {
        if(!user_email.getText().isBlank() || !user_password.getText().isBlank()) {
           //  utilities.login(event,user_email.getText(),user_password.getText());
            openDashboardWindow(event);
        }else {
            System.out.println("Username or Password cannot be empty");
        }
    }
    private void openDashboardWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Dashboard.fxml"));
        Parent root = loader.load();
        ((DashboardController)loader.getController()).setMainController(this);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome back");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void refresh() {
      //  result_table.getItems().clear();
        // result_table.setItems(userModel.getUserList());
    }


}
