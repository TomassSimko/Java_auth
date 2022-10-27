package dal;

import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import dal.repositories.UserDAO;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public class UserService implements IUserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) throws UserManagerException {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> getUsers() throws UserDAOException{
        try {
            return userDAO.getUsers();
        } catch (SQLException ex) {
            throw new UserDAOException("Could not retrieve users",ex);
        }
    }

    @Override
    public User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException  {
        try{
            return userDAO.createUser(email,password,firstName,lastName,isActive,pictureURL);
        }catch (SQLException ex){
            throw new UserDAOException("Could not create user with email " + email ,ex);
        }
    }

    @Override
    public User updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException {
        try{
            return userDAO.updateUser(user,email,password,firstName,lastName,isActive,pictureURL);
        }catch (SQLException ex){
            throw new UserDAOException("Could not update user with id " + user.getId() ,ex);
        }
    }

    @Override
    public void deleteUser(User currentUser) throws UserDAOException{
        try{
             userDAO.deleteUser(currentUser);
        }catch (SQLException ex){
            throw new UserDAOException("Could not delete user with id " + currentUser.getId() ,ex);
        }
    }
}
