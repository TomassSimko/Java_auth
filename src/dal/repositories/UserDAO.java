package dal.repositories;

import be.User;
import dal.db.DbConnection;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private final DbConnection connection;

    private static final String TABLE_USER = "user";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USERS_EMAIL = "email";
    private static final String COLUMN_USERS_PASSWORD = "password";
    private static final String COLUMN_USER_FIRSTNAME = "first_name";
    private static final String COLUMN_USER_LASTNAME = "last_name";
    private static final String COLUMN_USER_IS_ACTIVE = "is_active";
    private static final String COLUMN_USER_PROFILE_PICTURE = "profile_picture";

    private static final String COLUMN_USER_PICTURE_PATH = "picture_url";


    public UserDAO() {
        connection = new DbConnection();
    }

    public List<User> getUsers() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM " + TABLE_USER;
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String passwordHash = rs.getString(3);
                String firstName = rs.getString(4);
                String lastName = rs.getString(5);
                boolean isActive = rs.getBoolean(6);
                String picturePath = rs.getString(8);
                userList.add(new User(id, email, passwordHash, firstName, lastName, isActive, picturePath));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

    public User createUser(String email, String passwordHash, String firstName, String lastName, boolean isActive, File pictureURL) throws SQLException {
        try (Connection con = connection.getConnection()) {
            FileInputStream stream = new FileInputStream(pictureURL);
            String sql = "INSERT INTO " + TABLE_USER + "("
                    + COLUMN_USERS_EMAIL + ","
                    + COLUMN_USERS_PASSWORD + ","
                    + COLUMN_USER_FIRSTNAME + ","
                    + COLUMN_USER_LASTNAME + ","
                    + COLUMN_USER_IS_ACTIVE + ","
                    + COLUMN_USER_PROFILE_PICTURE + ","
                    + COLUMN_USER_PICTURE_PATH +
                    ")"
                    + "VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setBoolean(5, isActive);
            preparedStatement.setBinaryStream(6, stream, pictureURL.length());
            preparedStatement.setString(7, pictureURL.getAbsolutePath());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return new User(id, email, passwordHash, firstName, lastName, isActive, pictureURL.getAbsolutePath());
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User updateUser(User user, String email, String passwordHash, String firstName, String lastName, boolean isActive, File pictureURL) throws SQLException {
        try (Connection con = connection.getConnection()) {
            FileInputStream stream = new FileInputStream(pictureURL);
            String sql = "UPDATE " + TABLE_USER + " SET "
                    + COLUMN_USERS_EMAIL + "= ?" + ","
                    + COLUMN_USERS_PASSWORD + "= ?" + ","
                    + COLUMN_USER_FIRSTNAME + "= ?" + ","
                    + COLUMN_USER_LASTNAME + "= ?" + ","
                    + COLUMN_USER_IS_ACTIVE + "= ? " + ","
                    + COLUMN_USER_PROFILE_PICTURE + "= ? " + ","
                    + COLUMN_USER_PICTURE_PATH + "= ?"
                    + "WHERE "
                    + COLUMN_USER_ID + "= ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setBoolean(5, isActive);
            preparedStatement.setBinaryStream(6, stream, pictureURL.length());
            preparedStatement.setString(7, pictureURL.getAbsolutePath());
            preparedStatement.setInt(8, user.getId());
            preparedStatement.executeUpdate();

            user.setEmail(email);
            user.setPassword(passwordHash);
            user.setFirstName(firstName);
            user.setFirstName(lastName);
            return user;
        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteUser(User currentUser) throws SQLException {
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM " + TABLE_USER + "WHERE" + COLUMN_USER_ID + "= ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, currentUser.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
