package dal;

import be.User;
import bll.UserManager;
import dal.repositories.UserDAO;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public User registerUser(String email, String password) {
        try{
            return userDAO.registerUser(email,password);
        }catch (SQLException ex){
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
