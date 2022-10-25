package gui.controller;

import be.User;
import gui.models.UserModel;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UsersController implements Initializable {
    @FXML
    private Button search_btn;
    @FXML
    private TableColumn<User, String[]> role;
    @FXML
    private TableColumn<User, String> blob;

    @FXML
    private TableColumn<User, String> password_col;
    @FXML
    private TableColumn<User, String> first_name;
    @FXML
    private TableColumn<User, String> last_name;
    @FXML
    private TableView<User> result_table;
    @FXML
    private TableColumn<User, Integer> id_col;
    @FXML
    private TableColumn<User, String> email_col;
    @FXML
    private TableColumn<User, Boolean> isActive;

    @FXML
    private TextField search_field;

    private UserModel userModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.userModel = new UserModel();
        setTable();
        handleClick();
        setSearch();
    }

    private void setTable() {
        id_col.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        email_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        first_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        last_name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        password_col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        blob.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPictureURL()));
        isActive.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().isActive()));

        result_table.setItems(userModel.getUserList());
    }

    @FXML
    private void addUserAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/UserAction.fxml"));
        Parent root = fxmlLoader.load();
        UserActionController controller = fxmlLoader.getController();
        controller.setController(this);
        Stage actionStage = new Stage();
        actionStage.setScene(new Scene(root));
        actionStage.show();
    }

    @FXML
    private void handleClick() {
        result_table.setRowFactory(new Callback<>() {
            @Override
            public TableRow<User> call(TableView<User> param) {
                TableRow<User> row = new TableRow<>();
                row.setOnMouseClicked(new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (event.getClickCount() == 2 && (!row.isEmpty())) {
                            Parent root = null;
                            // System.out.println(row.getIndex());
                            User serialData = row.getItem();
                            // System.out.println(serialData.getId());
                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/UserAction.fxml"));
                                root = loader.load();
                                UserActionController controller = loader.getController();
                                controller.setController(UsersController.this);
                                controller.setEditableView(serialData);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));
                            stage.setResizable(false);
                            stage.show();
                        }
                    }
                });
                return row;
            }
        });
    }

    public void refresh() {
        result_table.getItems().clear();
        result_table.setItems(userModel.getUserList());
    }

    private void setSearch() {
        search_field.textProperty().addListener((obs, oldVal, newVal) -> {
            userModel.filteredTableOfUsers(newVal);
        });
    }
}
