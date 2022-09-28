package auth.base.db;

import auth.base.auth.gui.ProfileController;
import auth.base.models.User;
import auth.base.models.UserDto;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class DbUtils {
    public static void changeScene(ActionEvent event, String fxmlFile, UserDto user){
        Parent root = null;

        // null pointer exception
        if(user.email != null && user.passwordHash
                != null){
            try {
                FXMLLoader loader = new FXMLLoader(DbUtils.class.getResource(fxmlFile));
                root = loader.load();
                ProfileController pc = loader.getController();
                pc.setUser(new User(user.id,user.email,user.passwordHash, user.firstName, user.lastName));
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
        stage.setResizable(false);
        stage.show();
    }

    public static void login(ActionEvent event,String email,String password){
        DbConnection cnn = new DbConnection();
        Connection connection = null;
        PreparedStatement uExist = null;
        ResultSet set = null;
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
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
                    UserDto user = new UserDto(set.getString("id"),set.getString("email"),set.getString("password"),set.getString("first_name"), set.getString("last_name"));
                    if(user.email.equals(email) && encoder.matches(password, user.passwordHash)){
                        changeScene(event,"/Profile.fxml",user);
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

    private static User getUserByEmail(String email) {
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
                    return new User(set.getString("id"),set.getString("email"),set.getString("password"),set.getString("first_name"),set.getString("last_name"));
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
        return null;
    }



    // TODO: sort event and sort refreshing of GUI
    public static void updateUser(ActionEvent event, String id, String email, String firstName, String lastName) {
        DbConnection cnn = new DbConnection();
        Connection connection = null;
        PreparedStatement uExist = null;
        int set;
        try{
            connection = cnn.getConnection();
            uExist = connection.prepareStatement("UPDATE user SET email = ?,first_name = ?,last_name = ? WHERE id = ?");
            uExist.setString(1,email);
            uExist.setString(2,firstName);
            uExist.setString(3,lastName);
            uExist.setString(4,id);

            set = uExist.executeUpdate();

            if(set > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully updated user");
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Could not update user !");
                alert.show();
            }
        }catch(SQLException e){
            e.printStackTrace();
        } finally {
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
    public static void deleteUser(ActionEvent event, String id) {
        DbConnection cnn = new DbConnection();
        Connection connection = null;
        PreparedStatement uExist = null;
        boolean set;

        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setContentText("Are you sure you want to proceed");
        Optional<ButtonType> option = alertConfirm.showAndWait();
        if(ButtonType.OK.equals(option.get())){
        try{
            connection = cnn.getConnection();
            uExist = connection.prepareStatement("DELETE FROM user WHERE id = ?");
            uExist.setString(1,id);
            set = uExist.execute();

            if(!set) {
                changeScene(event,"/MainWindow.fxml",new UserDto(null,null,null,null,null));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Could not delete user!");
                alert.show();
            }
        }catch(SQLException e){
            e.printStackTrace();
        } finally {
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
    public static void updatePsw(ActionEvent event,String id, String oldPsw, String newPsw) {
         Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
         DbConnection cnn = new DbConnection();
         Connection connection = null;
         PreparedStatement uExist = null;
         int set;
         User user = getUser(id);

         if(oldPsw.isEmpty() || newPsw.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Neither old password or new password cannot be empty !");
            alert.show();
        }else if (encoder.matches(newPsw, user.getEncryptedPassword())){
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setContentText("New password cannot be same as the old one ");
             alert.show();
         }else if (!encoder.matches(oldPsw, user.getEncryptedPassword())){
             Alert alert = new Alert(Alert.AlertType.WARNING);
             alert.setContentText("Current password does not match you input");
             alert.show();
         }
         else {
             var hashedPassword = encoder.encode(newPsw);
            try{
                connection = cnn.getConnection();
                uExist = connection.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
                uExist.setString(1,hashedPassword);
                uExist.setString(2,id);
                set = uExist.executeUpdate();

                if(set > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Successfully updated password");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Could not update password !");
                    alert.show();
                }
            }catch(SQLException e){
                e.printStackTrace();
            } finally {
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
    public static User getUser(String id){
        DbConnection cnn = new DbConnection();
        Connection connection = null;
        PreparedStatement uExist = null;
        ResultSet set = null;

        try{
            connection = cnn.getConnection();
            uExist = connection.prepareStatement("SELECT * FROM user as u WHERE u.id = ?");
            uExist.setString(1,id);
            set = uExist.executeQuery();

            if(!set.isBeforeFirst()){
                System.out.print("User does not exists");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User does not exist");
                alert.show();
            }else{
                while(set.next()){
                    return new User(set.getString("id"),set.getString("email"),set.getString("password"),set.getString("first_name"),set.getString("last_name"));
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
        return null;
    }
}

