package gui.controller;

import gui.models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable
{
    @FXML
    private BarChart<String,String> chart;
    @FXML
    private Label active_users;

    private UserModel userModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // testing for now just return length of user list
//        UserModel userModel = new UserModel(); // how the fuck to get the Interface in here
//        active_users.setText(String.valueOf(userModel.getUserList().size()));
    }
    // do stuff for dashboard
}
