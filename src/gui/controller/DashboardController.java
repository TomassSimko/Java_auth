package gui.controller;

import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import bll.exceptions.UserServiceException;
import gui.models.IUserModel;

import gui.models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML
    private BarChart<String, String> chart;
    @FXML
    private Label active_users;

    private UserModel userModel;

    public void setUserModel(UserModel model) {
        this.userModel = model;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        try {
//            userModel = ModelFactory.createModel(ModelFactory.ModelType.USER_MODEL);
//        } catch (UserServiceException | UserDAOException e) {
//            throw new RuntimeException(e);
//        }
        // UserModel userModel = new UserModel(); // how the fuck to get the Interface in here
        try {
            active_users.setText(String.valueOf(userModel.getUserList().size()));
        } catch (UserDAOException | UserServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
