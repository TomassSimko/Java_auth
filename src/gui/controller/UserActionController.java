package gui.controller;

import be.User;
import bll.utitls.cryptography.CryptoEngine;
import bll.utitls.validations.ValidationHelper;
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

    private CryptoEngine cryptoEngine;

    private File sendFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isEditable = false;
        this.userModel = new UserModel();
        this.cryptoEngine = new CryptoEngine();
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


    // TODO : ADD GENERIC ALERT BOX
    @FXML
    private void createUserAction(ActionEvent event) {
        if (!isEditable) {
            if (!ValidationHelper.validateEmail(email.getText())) {
                System.out.println("Wong email");
            } else if (!ValidationHelper.validatePassword(password.getText())) {
                System.out.println("Wong password");
            } else {
                String hashedPassword = cryptoEngine.Hash(password.getText());
                userModel.createUser(
                        email.getText().trim(),
                        hashedPassword,
                        first_name.getText().trim(),
                        last_name.getText().trim(),
                        isActive.selectedProperty().getValue(),
                        sendFile
                );
                closeAndUpdate();
            }
        } else {
            userModel.updateUser(currentUser,
                    email.getText().trim(),
                    password.getText().trim(),
                    first_name.getText().trim(),
                    last_name.getText().trim(),
                    isActive.isSelected(),
                    sendFile
            );
            closeAndUpdate();
        }

    }

    private void closeAndUpdate() {
        parentController.refresh();
        Stage stage;
        stage = (Stage) confirm_action.getScene().getWindow();
        stage.close();
    }

    public void setEditableView(User user) {
        isEditable = true;
        currentUser = user;

        password.setText(currentUser.getPassword());
        password.setEditable(false);
        email.setText(currentUser.getEmail());
        first_name.setText(currentUser.getFirstName());
        last_name.setText(currentUser.getLastName());
        isActive.selectedProperty().set(currentUser.isActive());

        // not loading actual file when updating it does not fetches the same file
        file_absolute_path.setText(currentUser.getPictureURL());
        confirm_action.setText("UPDATE");
        labelUserAction.setText("Edit user");
        deleteOnAction.setDisable(false);
    }

    @FXML
    private void openFileChoose(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG Files", "*.png", "png")
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
