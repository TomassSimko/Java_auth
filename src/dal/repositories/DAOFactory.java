package dal.repositories;

import dal.repositories.interfaces.IRoleDAO;
import dal.repositories.interfaces.IUserDAO;

public class DAOFactory {
    public static IUserDAO createUserDao() {
        return new UserDAO();
    }

    public static IRoleDAO createRoleDao() {
        return new RoleDAO();
    }
}
