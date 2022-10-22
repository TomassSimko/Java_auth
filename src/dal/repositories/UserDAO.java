package dal.repositories;

import be.User;
import dal.db.DbConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private final DbConnection connection;

    public UserDAO(){
        connection = new DbConnection();
    }

    public List<User> getUsers() throws SQLException {
        List<User> userList = new ArrayList<>();

        try (Connection con = connection.getConnection()) {
            String sql = "SELECT * FROM user";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String passwordHash = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                userList.add(new User(id, email, passwordHash, firstName, lastName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

    public User createUser(String email, String passwordHash,String firstName,String lastName) throws SQLException{
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO user(email,password,first_name,last_name) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return new User(id, email, passwordHash,firstName,lastName);
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public User updateUser(User user,String email, String passwordHash,String firstName,String lastName) throws SQLException{
        try (Connection con = connection.getConnection()) {
            String sql = "UPDATE user SET email = ?, password = ? , first_name = ? ,last_name = ? WHERE id = ? ";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, passwordHash);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4, lastName);
            preparedStatement.setInt(5, user.getId());
            preparedStatement.executeUpdate();

            user.setEmail(email);
            user.setPassword(passwordHash);
            user.setFirstName(firstName);
            user.setFirstName(lastName);
            return user;
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void deleteUser(User currentUser) throws SQLException{
        try (Connection con = connection.getConnection()) {
            String sql = "DELETE FROM user WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, currentUser.getId());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
