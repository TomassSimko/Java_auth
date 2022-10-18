package dal.repositories;

import be.User;
import dal.db.DbConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                String id = rs.getString("id");
                String email = rs.getString("email");
                String passwordHash = rs.getString("passwordHash");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                userList.add(new User(id, email, passwordHash, firstName, lastName));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

}
