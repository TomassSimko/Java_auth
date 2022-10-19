package dal.repositories;

import be.User;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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

    public User registerUser(String email,String passwordHash) throws SQLException{
        try (Connection con = connection.getConnection()) {
            String sql = "INSERT INTO user(email, password) VALUES (?,?)";
            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, email);
            pstmt.setString(2, passwordHash);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            return new User(id, email, passwordHash,"","");
        } catch (SQLServerException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
