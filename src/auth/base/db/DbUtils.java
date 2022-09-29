package auth.base.db;

import auth.base.auth.gui.ProfileController;
import auth.base.models.User;
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
import java.util.UUID;

public class DbUtils {

    // dependency injection ?
    // inject db connection to use it all across the class ?
    // create password hashed in another class ?

    public DbUtils(){

    }

    public static void changeScene(ActionEvent event, String fxmlFile, User user){
        Parent root = null;

        if(user.getEmail() != null && user.getEncryptedPassword()
                != null){
            try {
                FXMLLoader loader = new FXMLLoader(DbUtils.class.getResource(fxmlFile));
                root = loader.load();
                ProfileController pc = loader.getController();
                pc.setUser(new User(user.getUserId(),user.getEmail(),user.getEncryptedPassword(), user.getFirstName(), user.getLastName()));
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

    public static void signUp(ActionEvent event,String email,String password,String firstName,String lastName){
        DbConnection cnn = new DbConnection();
        Connection connection = null;
        PreparedStatement uExist = null;
        int set;
        Alert alert;
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);

        var hashedPassword = encoder.encode(password);
        User user = new User(UUID.randomUUID().toString(),email,hashedPassword,firstName,lastName);

        try{
            connection = cnn.getConnection();
            uExist = connection.prepareStatement("INSERT INTO user (id,email,password,first_name,last_name) VALUES (?,?,?,?,?);");
            uExist.setString(1, user.getUserId());
            uExist.setString(2,user.getEmail());
            uExist.setString(3,user.getEncryptedPassword());
            uExist.setString(4,user.getFirstName());
            uExist.setString(5,user.getLastName());

            set = uExist.executeUpdate();

            if(set > 0){
                    Alert alertConfirm = new Alert(Alert.AlertType.INFORMATION);
                    alertConfirm.setContentText("Successfully created user");
                    Optional<ButtonType> option = alertConfirm.showAndWait();

                    if(ButtonType.OK.equals(option.get())){
                        changeScene(event, "/MainWindow.fxml", new User(null,null,null,null,null));
                    }
            }else{
                System.out.print("Could not create user");
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Could not create user");
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

    public static void login(ActionEvent event,String email,String password){
        DbConnection cnn = new DbConnection();
        Connection connection = null;
        PreparedStatement uExist = null;
        ResultSet set = null;
        Alert alert;
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);

        try{
             connection = cnn.getConnection();
             uExist = connection.prepareStatement("SELECT * FROM user as u WHERE u.email = ?");
             uExist.setString(1,email);
             set = uExist.executeQuery();

             if(!set.isBeforeFirst()){
                 System.out.print("User does not exists");
                 alert = new Alert(Alert.AlertType.ERROR);
                 alert.setContentText("User does not exist");
                 alert.show();
             }else{
                while(set.next()){
                    User user = new User(set.getString("id"),set.getString("email"),set.getString("password"),set.getString("first_name"), set.getString("last_name"));

                    if(user.getEmail().equals(email) && encoder.matches(password, user.getEncryptedPassword())){
                        changeScene(event,"/Profile.fxml",user);
                    } else {
                        System.out.print("Psw do not match");
                        alert = new Alert(Alert.AlertType.ERROR);
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

    // TODO: sort event and sort refreshing of GUI
    public static void updateUser(ActionEvent event, String id, String email, String firstName, String lastName) {
        DbConnection cnn = new DbConnection();
        Connection connection = null;
        PreparedStatement uExist = null;
        int set;
        Alert alert;

        try{
            connection = cnn.getConnection();
            uExist = connection.prepareStatement("UPDATE user SET email = ?,first_name = ?,last_name = ? WHERE id = ?");
            uExist.setString(1,email);
            uExist.setString(2,firstName);
            uExist.setString(3,lastName);
            uExist.setString(4,id);

            set = uExist.executeUpdate();
            User user = getUser(id);
            if(set > 0) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully updated user");
                alert.show();
                if(user != null){
                    // this is shitty propably does not update gui
                    // set user ?
//                    FXMLLoader loader = new FXMLLoader(DbUtils.class.getResource("/Profile.fxml"));
//                    root = loader.load();
//                    ProfileController pc = loader.getController();
//                    pc.setUser(new User(user.getUserId(),user.getEmail(),user.getEncryptedPassword(), user.getFirstName(), user.getLastName()));
                }

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
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
                changeScene(event,"/MainWindow.fxml",new User(null,null,null,null,null));
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
         Alert alert;

         if(oldPsw.isEmpty() || newPsw.isEmpty()){alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Neither old password or new password cannot be empty !");
            alert.show();
        }else if (user != null && encoder.matches(newPsw, user.getEncryptedPassword())){
             alert = new Alert(Alert.AlertType.WARNING);
             alert.setContentText("New password cannot be same as the old one ");
             alert.show();
         }else if (user != null && !encoder.matches(oldPsw, user.getEncryptedPassword())){
             alert = new Alert(Alert.AlertType.WARNING);
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
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Successfully updated password");
                    alert.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
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

