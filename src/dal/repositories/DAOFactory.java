package dal.repositories;

import dal.db.DbConnection;
import dal.repositories.interfaces.IRoleDAO;
import dal.repositories.interfaces.IUserDAO;

public class DAOFactory {
    public static IUserDAO createUserDao() {
        return new UserDAO(DbConnection.getConnection());
    }

    public static IRoleDAO createRoleDao() {
        return new RoleDAO(DbConnection.getConnection());
    }
}
