package dal.repositories;

import be.Role;
import be.User;
import dal.db.DbConnection;
import dal.repositories.interfaces.IRoleDAO;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RoleDAO implements IRoleDAO {

    private final Connection cnn;

    private static final String TABLE_ROLE = "role";
    private static final String COLUMN_ROLE_ID = "id";
    private static final String COLUMN_NAME = "name";

    public RoleDAO(Connection connection) {
       this.cnn = connection;
    }

    public List<Role> getAllRoles() throws Exception{
        List<Role> roleList = new ArrayList<>();
        try (cnn) {
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

}
