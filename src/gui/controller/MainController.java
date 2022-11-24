package gui.controller;

import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import bll.exceptions.UserServiceException;
import gui.models.IUserModel;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private Button usersRoute;
    @FXML
    private Label lbl;
    @FXML
    private AnchorPane header_menu;
    @FXML
    private StackPane app_content;

    private UserModel userModel;

    // THIS SHOULD BRING THE MODEL AND SHOULD BE CREATED

    // Take a look at the user model factory
    // fix sending user model from one class to other
    // Implement IUserModel to work across the layered methods
    // implement fetching all roles from the database

    // introduce session user singleton that stores the currently signed user with given access roles

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.userModel =  new UserModel();
        } catch (UserManagerException e) {
            throw new RuntimeException(e);
        } catch (UserDAOException e) {
            throw new RuntimeException(e);
        }

        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardBase.fxml");
       // ((DashboardController)fxmlLoader.getController()).setUserModel(userModel);
    }
    public void btnDashboard(ActionEvent event) {
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardBase.fxml");
       // ((DashboardController)fxmlLoader.getController()).setUserModel(userModel);
    }
    public void btnUsersOnClick(ActionEvent event) {
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardUsers.fxml");
      // ((UsersController)fxmlLoader.getController()).setUserModel(userModel);
    }
    public void btnLogOutOnClick(ActionEvent event) {
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardBase.fxml");
      // ((DashboardController)fxmlLoader.getController()).setUserModel(userModel);
    }

    private FXMLLoader loadFxmlPage(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(Objects.requireNonNull(getClass().getResource(path)).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane root = fxmlLoader.getRoot();
        app_content.getChildren().clear();
        app_content.getChildren().add(root);

        return fxmlLoader;
    }
}
