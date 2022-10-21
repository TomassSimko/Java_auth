package gui.controller;

import be.User;
import gui.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    @FXML
    private Button add;
    @FXML
    private TextField password_input;
    @FXML
    private TextField email_input;
    @FXML
    private TableView<User> result_table;

    private UserModel userModel;
//    private MainController mainController;
//
//    public void setMainController(MainController mainController) {
//        this.mainController = mainController;
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
         this.userModel = new UserModel();
      //   result_table.setItems(userModel.getUserList());
        setUsers();
       // setUsers();
//        add.setOnAction(this::createUser);
    }

    private void setUsers() {
        fetchUsers();
        // TODO: Not working binding only some of the values
        // result_table.setItems(userModel.getUserList());
        Task<ObservableList<User>> allUsers = new Task<ObservableList<User>>() {
            @Override
            protected ObservableList<User> call() {
                return FXCollections.observableArrayList(userModel.getUserList());
            }
        };
        result_table.itemsProperty().bind(allUsers.valueProperty());
        new Thread(allUsers).start();
    }

    private void addUserAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/pages/AddUser.fxml"));
        Parent root = loader.load();
       //  ((UserEditController)loader.getController()).setMainController(this);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome back");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void createUser(ActionEvent event) {
        userModel.addUser(email_input.getText(),password_input.getText());
       //  mainController.refresh();
    }


    // TODO: should not be here just for testing cnn
    private void fetchUsers() {
        var test = userModel.getUserList();
        for (User u : test){
            System.out.println(u.getUserId());
            System.out.println(u.getEmail());

        }
    }



}
