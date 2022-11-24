package gui;

import bll.utitls.HelperEventHandlers;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {



    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("views/DashboardMain.fxml")));
        Scene scene = new Scene(root);
        HelperEventHandlers handlers = new HelperEventHandlers();
        handlers.useEventHandlers(root,stage);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Welcome");
        stage.show();
    }
}