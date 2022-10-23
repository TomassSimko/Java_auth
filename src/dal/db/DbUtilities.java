package dal.db;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

public class DbUtilities {

    public DbUtilities() {
    }

    // Move to helper class
    private final static Argon2PasswordEncoder encoder =
            new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);
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

//



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



