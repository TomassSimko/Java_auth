package gui.controller;

import be.User;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardBase.fxml");
       // DashboardController dashboardController = fxmlLoader.getController();
    }
    public void btnDashboard(ActionEvent event) {
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardBase.fxml");
    }
    public void btnUsersOnClick(ActionEvent event) {
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardUsers.fxml");
    }
    public void btnLogOutOnClick(ActionEvent event) {
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardBase.fxml");
    }

    private FXMLLoader loadFxmlPage(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        try {
            fxmlLoader.load(getClass().getResource(path).openStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnchorPane root = fxmlLoader.getRoot();
        app_content.getChildren().clear();
        app_content.getChildren().add(root);

        return fxmlLoader;
    }






}
