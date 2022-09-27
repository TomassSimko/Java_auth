package auth.base.db;

import auth.base.auth.gui.ProfileController;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

// import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class DbUtils {
    public static void changeScene(ActionEvent event,String fxmlFile,String email,String password){
        Parent root = null;

        if(email != null && password != null){
            try {
                FXMLLoader loader = new FXMLLoader(DbUtils.class.getResource(fxmlFile));
                root = loader.load();
                ProfileController pc = loader.getController();
                pc.setUser(email,password);
            }catch(IOException e){
                e.printStackTrace();
            }
        } else {
            try{
                root = FXMLLoader.load(Objects.requireNonNull(DbUtils.class.getResource(fxmlFile)));
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void login(ActionEvent event,String email,String password){
        DbConnection cnn = new DbConnection();
        Connection connection = null;
        PreparedStatement uExist = null;
        ResultSet set = null;

        try{
             connection = cnn.getConnection();
             uExist = connection.prepareStatement("SELECT * FROM user as u WHERE u.email = ?");
             uExist.setString(1,email);
             set = uExist.executeQuery();

             if(!set.isBeforeFirst()){
                 System.out.print("User does not exists");
                 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setContentText("User does not exist");
                 alert.show();
             }else{
                while(set.next()){
                    String retrievedEmail = set.getString("email");
                    String retrievedPassword = set.getString("password");
                    if(retrievedEmail.equals(email) && retrievedPassword.equals(password)){
                        changeScene(event,"/Profile.fxml",retrievedEmail,retrievedPassword);
                    } else {
                        System.out.print("Psw do not match");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Password do not match");
                        alert.show();
                    }
                }
             }
        }catch(SQLException e){
            e.printStackTrace();
        } finally {
            if(set != null){
                try {
                    // result , preparedStatement, connection
                    set.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(uExist != null){
                try {
                    uExist.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

