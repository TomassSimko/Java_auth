package gui.controller;

import be.Role;
import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import bll.exceptions.UserServiceException;
import bll.utitls.NotificationHelper;
import bll.utitls.validations.ValidationErrorType;
import bll.utitls.validations.ValidationHelper;
import gui.models.RoleModel;
import gui.models.UserModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserActionController implements Initializable {
    @FXML
    private MenuButton menu_test;
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
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField email;

    private UserModel userModel;

    private RoleModel roleModel;
    UsersController parentController;

    private boolean isEditable;
    private User currentUser;

    private boolean isValidated;

    private List<Role> rolesList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isEditable = false;
        isValidated = false;
        try {
            this.userModel = new UserModel();
            this.roleModel = new RoleModel();
            this.rolesList = roleModel.getRolesList();
        } catch (UserManagerException | UserDAOException e) {
            throw new RuntimeException(e);
        }
        EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                labelUserAction.setText(((MenuItem)e.getSource()).getText()+ " selected");
            }
        };

        for (Role role : rolesList) {
             CheckBox cb0 = new CheckBox(role.getName());
             CustomMenuItem item0 = new CustomMenuItem(cb0);
             item0.setHideOnClick(false);
             item0.setOnAction(event1);
             menu_test.getItems().addAll(item0);
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
    private void createUserAction(ActionEvent event) throws Exception {
        if(!isEditable) {
            if(validateUserInput()){
               List<String> roles =  new ArrayList<>();// <- create here roles from input
                roles.add("User");
                roles.add("Administrator");

                userModel.createUser(
                        email.getText().trim(),
                        password.getText().trim(),
                        username.getText().trim(),
                        isActive.selectedProperty().getValue(),
                        roles
                );
                closeAndUpdate();
            }
        }else if(validateUserInput()){
            userModel.updateUser(currentUser,
                    email.getText().trim(),
                    password.getText().trim(),
                    username.getText().trim(),
                    isActive.isSelected()
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
        username.setText(currentUser.getUserName());
        isActive.selectedProperty().set(currentUser.isActive());
        //roles_box.setValue(currentUser.getRoles());
        confirm_action.setText("UPDATE");
        labelUserAction.setText("Edit user");
        deleteOnAction.setDisable(false);
    }



    // TODO: This validation sucks must be repaired
    private boolean validateUserInput(){
        if(!username.getText().isEmpty()  || !password.getText().isEmpty() || !email.getText().isEmpty()){
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
