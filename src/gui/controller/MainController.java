package gui.controller;

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
    private AnchorPane acDashBord;
    @FXML
    private Label lbl;
    @FXML
    private AnchorPane header_menu;
    @FXML
    private StackPane app_content;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        fetchUsers();
//

//        log_in.setOnAction(event ->
//                DbUtilities.login(event,
//                        user_email.getText(),
//                        user_password.getText()
//                ));
//        FXMLLoader fxmlLoader = loadFxmlPage("/view/admin/pages/home/home.fxml");
//        HomeController homeController = fxmlLoader.getController();
//        homeController.getDashboardProdCount();
//        homeController.getDashboardCostCount();
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardBase.fxml");
        DashboardController dashboardController = fxmlLoader.getController();
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


//    private void auth(ActionEvent event) throws IOException {
//        if(!user_email.getText().isBlank() || !user_password.getText().isBlank()) {
//           //  utilities.login(event,user_email.getText(),user_password.getText());
//            openDashboardWindow(event);
//        }else {
//            System.out.println("Username or Password cannot be empty");
//        }
//    }
    private void openDashboardWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/pages/DashboardUsers.fxml"));
        Parent root = loader.load();
        ((UsersController)loader.getController()).setMainController(this);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome back");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void refresh() {
      //  result_table.getItems().clear();
        // result_table.setItems(userModel.getUserList());
    }

    // switch to dashboard page
    public void btnDashboard(ActionEvent event) {
    }

    // switch to users page
    public void btnUsersOnClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = loadFxmlPage("../views/pages/DashboardUsers.fxml");
        UsersController controller = fxmlLoader.getController();
        // controller.setMainController(this);
    }

    // log out user
    public void btnLogOutOnClick(ActionEvent event) {
    }


}
