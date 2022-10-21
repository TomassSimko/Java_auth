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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {

    public TextField search_field;
    @FXML
    private TextField password_input;
    @FXML
    private TextField email_input;
    @FXML
    private TableView<User> result_table;

    @FXML
    private TableColumn<User, Integer> id_col;
    @FXML
    private TableColumn<User, String> email_col;

    private UserModel userModel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userModel = new UserModel();
        id_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));       // setUsers();
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        result_table.setItems(userModel.getUserList());

    }

    @FXML
    private void addUserAction(ActionEvent event) throws IOException {
        Parent root;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/UserAction.fxml"));
        root = (Parent) fxmlLoader.load();
        ProfileController controller =  (ProfileController)fxmlLoader.getController();
        controller.setController(this);
       // controller.editMode(selectedSong); //set mode to edit
        Stage actionStage = new Stage();
        Scene actionScene = new Scene(root);
        actionStage.setScene(actionScene);
        actionStage.show();
    }

    public void refresh() {

    }


    // TODO: should not be here just for testing cnn
    //    private void setUsers() {
//        fetchUsers();
//        // TODO: Not working binding only some of the values
//        Task<ObservableList<User>> allUsers = new Task<ObservableList<User>>() {
//            @Override
//            protected ObservableList<User> call() {
//                return FXCollections.observableArrayList(userModel.getUserList());
//            }
//        };
//        result_table.itemsProperty().bind(allUsers.valueProperty());
//        new Thread(allUsers).start();
//    }




}
