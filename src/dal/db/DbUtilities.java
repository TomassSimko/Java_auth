package dal.db;

import gui.controller.UserActionController;
import be.User;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import java.io.IOException;
import java.util.Objects;

public class DbUtilities {

    public DbUtilities(){
    }

    private final static Argon2PasswordEncoder encoder =
            new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

    public static void changeScene(ActionEvent event, String fxmlFile, User user) {
        Parent root = null;

        if (user.getEmail() != null && user.getPassword() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DbUtilities.class.getResource(fxmlFile));
                root = loader.load();
                UserActionController pc = loader.getController();
               // pc.setUser(user);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                root = FXMLLoader.load(Objects.requireNonNull(DbUtilities.class.getResource(fxmlFile)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Welcome back");
        stage.setScene(new Scene(root));
        stage.show();
    }
}



//    public void login(ActionEvent event, String email, String password) {
//        try (Connection con = dbConnection.getConnection()) {
//            String sql = "SELECT * FROM user as u WHERE u.email = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, email);
//            ResultSet set = ps.executeQuery();
//            Alert alert;
//            if (!set.isBeforeFirst()) {
//                System.out.print("User does not exists");
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("User does not exist");
//                alert.show();
//            } else {
//                while (set.next()) {
//                    User user = new User(set.getInt("id"), set.getString("email"), set.getString("password"), set.getString("first_name"), set.getString("last_name"));
//                    if (user.getEmail().equals(email) && encoder.matches(password, user.getEncryptedPassword())) {
//                        changeScene(event, "/UserAction.fxml", user);
//                    } else {
//                        alert = new Alert(Alert.AlertType.ERROR);
//                        alert.setContentText("Password do not match");
//                        alert.show();
//                    }
//                }
//            }
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
//    }
//
//    public User getUserById(String id) {
//        try (Connection con = dbConnection.getConnection()) {
//            String sql = "SELECT * FROM user as u WHERE u.id = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, id);
//            ResultSet set = ps.executeQuery();
//            if (set.next()) {
//                return new User(set.getInt("id"), set.getString("email"), set.getString("password"), set.getString("first_name"), set.getString("last_name"));
//            }
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
//        return null;
//    }
//
//    private  User getUserByEmail(String email) {
//        try (Connection con = dbConnection.getConnection()) {
//            String sql = "SELECT * FROM user as u WHERE u.email = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, email);
//            ResultSet set = ps.executeQuery();
//            if (set.next()) {
//                return new User(set.getInt("id"), set.getString("email"), set.getString("password"), set.getString("first_name"), set.getString("last_name"));
//            }
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
//        return null;
//    }
//
//    public void deleteUser(ActionEvent event, String id) {
//        try (Connection con = dbConnection.getConnection()) {
//            String sql = "DELETE FROM user WHERE id = ?";
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, id);
//            ResultSet set = ps.executeQuery();
//            Alert alert;
//            if (!set.isBeforeFirst()) {
//                changeScene(event, "/MainWindow.fxml", null);
//            } else {
//                alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("Could not delete user!");
//                alert.show();
//            }
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
//    }
//}

// TODO: sort event and sort refreshing of GUI
//    public static void updateUser(ActionEvent event, String id, String email, String firstName, String lastName) {
//        DbConnection cnn = new DbConnection();
//        Connection connection = null;
//        PreparedStatement uExist = null;
//        int set;
//        try{
//            connection = cnn.getConnection();
//            uExist = connection.prepareStatement("UPDATE user SET email = ?,first_name = ?,last_name = ? WHERE id = ?");
//            uExist.setString(1,email);
//            uExist.setString(2,firstName);
//            uExist.setString(3,lastName);
//            uExist.setString(4,id);
//
//            set = uExist.executeUpdate();
//
//            if(set > 0) {
//                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                alert.setContentText("Successfully updated user");
//                alert.show();
//            } else {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setContentText("Could not update user !");
//                alert.show();
//            }
//        }catch(SQLException e){
//            e.printStackTrace();
//        } finally {
//            if(uExist != null){
//                try {
//                    uExist.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            if(connection != null){
//                try {
//                    connection.close();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//    }


//    public static void updatePsw(ActionEvent event,String id, String oldPsw, String newPsw) {
//         Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32,64,1,15*1024,2);
//         DbConnection cnn = new DbConnection();
//         Connection connection = null;
//         PreparedStatement uExist = null;
//         int set;
//         User user = getUserByEmail(id);
//
//         if(oldPsw.isEmpty() || newPsw.isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setContentText("Neither old password or new password cannot be empty !");
//            alert.show();
//        }else if (encoder.matches(newPsw, user.getEncryptedPassword())){
//             Alert alert = new Alert(Alert.AlertType.WARNING);
//             alert.setContentText("New password cannot be same as the old one ");
//             alert.show();
//         }
//         else {
//             var hashedPassword = encoder.encode(newPsw);
//            try{
//                connection = cnn.getConnection();
//                uExist = connection.prepareStatement("UPDATE user SET password = ? WHERE id = ?");
//                uExist.setString(1,hashedPassword);
//                uExist.setString(2,id);
//                set = uExist.executeUpdate();
//
//                if(set > 0) {
//                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//                    alert.setContentText("Successfully updated password");
//                    alert.show();
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setContentText("Could not update password !");
//                    alert.show();
//                }
//            }catch(SQLException e){
//                e.printStackTrace();
//            } finally {
//                if(uExist != null){
//                    try {
//                        uExist.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if(connection != null){
//                    try {
//                        connection.close();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }



