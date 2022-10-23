package gui.controller;

import be.User;
import gui.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class UserActionController implements Initializable {
    @FXML
    private CheckBox isActive;
    @FXML
    private Button deleteOnAction;
    @FXML
    private Label labelUserAction;
    @FXML
    private Button cancelOnAction;
    @FXML
    private Button confirm_action;
    @FXML
    private TextField first_name;
    @FXML
    private TextField last_name;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private TextField file_absolute_path;
    private UserModel userModel;

    UsersController parentController;

    private boolean isEditable;
    private User currentUser;


    // testing

    private File sendFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isEditable = false;
        this.userModel = new UserModel();
        cancelOnAction.setOnAction(this::closeWindow);

    }
    private void closeWindow(ActionEvent event) {
        Stage stage;
        stage = (Stage) cancelOnAction.getScene().getWindow();
        stage.close();
    }

    public void setController(UsersController usersController) {
        this.parentController = usersController;
    }

    @FXML
    private void createUserAction(ActionEvent event) {
        if (!isEditable) {
            // no validation yet
            userModel.createUser(
                    email.getText().trim(),
                    password.getText().trim(),
                    first_name.getText().trim(),
                    last_name.getText().trim(),
                    isActive.selectedProperty().getValue(),
                    sendFile
                   //  file_absolute_path.getText().trim()
            );
        } else {
            userModel.updateUser(currentUser,
                    email.getText().trim(),
                    password.getText().trim(),
                    first_name.getText().trim(),
                    last_name.getText().trim(),
                    isActive.isSelected(),
                    sendFile
            );
        }
        parentController.refresh();
        Stage stage;
        stage = (Stage) confirm_action.getScene().getWindow();
        stage.close();
    }

    public void setEditableView(User user) {
        isEditable = true;
        currentUser = user;

        password.setText(currentUser.getPassword());
        email.setText(currentUser.getEmail());
        first_name.setText(currentUser.getFirstName());
        last_name.setText(currentUser.getLastName());
        isActive.selectedProperty().set(currentUser.isActive());
        file_absolute_path.setText(currentUser.getPictureURL());
        confirm_action.setText("UPDATE");
        labelUserAction.setText("Edit user");
        deleteOnAction.setDisable(false);
    }
    @FXML
    private void openFileChoose(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png","png")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            String path = file.getAbsolutePath();
            file_absolute_path.setText(path);
            sendFile = file;
        }
    }

    public void deleteUserOnAction(ActionEvent event) {
        System.out.println("deleting user ...");
        userModel.deleteUser(currentUser);
        parentController.refresh();
        Stage stage;
        stage = (Stage) deleteOnAction.getScene().getWindow();
        stage.close();
    }
}
