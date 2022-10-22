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
    public User createUser(String email, String password,String firstName,String lastName) {
        try{
            return userDAO.createUser(email,password,firstName,lastName);
        }catch (SQLException ex){
            Logger.getLogger(UserManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
