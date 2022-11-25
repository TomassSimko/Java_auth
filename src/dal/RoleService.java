package dal;

import be.Role;
import dal.repositories.DAOFactory;
import dal.repositories.interfaces.IRoleDAO;

import java.util.List;

public class RoleService implements IRoleService{
    private final IRoleDAO roleDao = DAOFactory.createRoleDao();

    @Override
    public List<Role> getRoles() throws Exception {
        return roleDao.getAllRoles();
    }

    @Override
    public Role getRoleByName(String role) throws Exception {
        return roleDao.getRoleByName(role);
    }
}
