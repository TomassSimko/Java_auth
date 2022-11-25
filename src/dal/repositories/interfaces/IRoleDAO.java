package dal.repositories.interfaces;

import be.Role;
import be.User;

import java.sql.SQLException;
import java.util.List;

public interface IRoleDAO {
    List<Role> getAllRoles() throws Exception;
    Role getRoleByName(String name) throws Exception;
}
