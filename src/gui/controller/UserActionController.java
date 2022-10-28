package gui.controller;

import be.Role;
import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import bll.exceptions.UserServiceException;
import bll.utitls.cryptography.CryptoEngine;
import bll.utitls.validations.NotificationHelper;
import bll.utitls.validations.ValidationErrorType;
import bll.utitls.validations.ValidationHelper;
import gui.models.RoleModel;
import gui.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserActionController implements Initializable {
    @FXML
    private ChoiceBox<Role> roles_box;
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

    private RoleModel roleModel;
    UsersController parentController;

    private boolean isEditable;
    private User currentUser;

    private File sendFile;

    private boolean isValidated;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isEditable = false;
        isValidated = false;
        try {
            this.userModel = new UserModel();
            this.roleModel = new RoleModel();
        } catch (UserManagerException e) {
            throw new RuntimeException(e);
        }

        List<Role> rolesList = roleModel.getRolesList();
        for (Role role : rolesList) {
            roles_box.getItems().add(role);
        }
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
    private void createUserAction(ActionEvent event) throws UserDAOException {
        if(!isEditable) {
            if(validateUserInput()){
                userModel.createUser(
                        email.getText().trim(),
                        password.getText().trim(),
                        first_name.getText().trim(),
                        last_name.getText().trim(),
                        isActive.selectedProperty().getValue(),
                        sendFile
                );
                closeAndUpdate();
            }
        }else if(validateUserInput()){
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

    private void closeAndUpdate() throws UserDAOException {
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
        file_absolute_path.setText(currentUser.getPictureURL());
       // roles_box.setValue(currentUser.getRoles());
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

    // TODO: This validation sucks must be repaired
    private boolean validateUserInput(){
        if(!first_name.getText().isEmpty() || !last_name.getText().isEmpty() || !password.getText().isEmpty() || !email.getText().isEmpty() || sendFile != null){
            if(!ValidationHelper.validateEmail(email.getText())){
                NotificationHelper.displayAlert(ValidationErrorType.BADLY_FORMATTED_EMAIL, Alert.AlertType.ERROR);
            }else if(!ValidationHelper.validatePassword(password.getText())){
                NotificationHelper.displayAlert(ValidationErrorType.BADLY_FORMATTED_PASSWORD, Alert.AlertType.ERROR);
            }
        }else {
            isValidated = true;
        }
        return isValidated;
    }

    public void deleteUserOnAction(ActionEvent event) throws UserServiceException, UserDAOException {
        System.out.println("deleting user ...");
        userModel.deleteUser(currentUser);
        closeAndUpdate();
    }
}
