package dal.repositories;

import be.Role;
import dal.db.DbConnection;
import dal.repositories.interfaces.IRoleDAO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class RoleDAO implements IRoleDAO {

    private static final String TABLE_ROLE = "role";
    private static final String COLUMN_ROLE_ID = "id";
    private static final String COLUMN_NAME = "name";

    public List<Role> getAllRoles() throws Exception{
        List<Role> roleList = new ArrayList<>();
       try (Connection cnn = DbConnection.getConnection()) {
            String sql = "SELECT * FROM " + TABLE_ROLE;
            Statement stmt = cnn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                roleList.add(new Role(
                        rs.getInt(1),
                        rs.getString(2)
                ));
            }
        }
        return roleList;
    }

    public Role getRoleByName(String name) throws Exception{
        Role role = null;
        try (Connection cnn = DbConnection.getConnection()) {
            String sql = "SELECT * FROM role AS r WHERE r.name= ?";
            PreparedStatement preparedStatement = cnn.prepareStatement(sql);
            preparedStatement.setString(1,name);
            ResultSet rs = preparedStatement.executeQuery(sql);
            while (rs.next()) {
                role = new Role(rs.getInt("id"),rs.getString("name"));
            }
        }
        return role;
    }

}
