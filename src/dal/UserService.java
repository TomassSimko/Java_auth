package dal;

import be.User;
import dal.repositories.UserDAO;

import java.sql.SQLException;
import java.util.List;

public class UserService implements IUserService {
    private final UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    @Override
    public List<User> getUsers() {
        try {
            return userDAO.getUsers();
        } catch (SQLException ex) {
           // Logger.getLogger(LogicManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
