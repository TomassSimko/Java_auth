package dal;

import be.User;
import dal.repositories.UserDAO;
import dal.repositories.interfaces.IUserDAO;

import java.io.File;
import java.util.List;

public class UserService implements IUserService {
    private final IUserDAO userDAO;

    public UserService() throws Exception {
        this.userDAO = new UserDAO();
    }

    @Override
    public List<User> getUsers() throws Exception {
        return userDAO.getUsers();
    }

    @Override
    public User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws Exception {
        return userDAO.createUser(email, password, firstName, lastName, isActive, pictureURL);
    }

    @Override
    public void updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws Exception {
        userDAO.updateUser(user, email, password, firstName, lastName, isActive, pictureURL);
    }

    @Override
    public void deleteUser(User currentUser) throws Exception {
        userDAO.deleteUser(currentUser);
    }
}
