package dal;

import be.Role;
import be.User;
import dal.repositories.DAOFactory;
import dal.repositories.UserDAO;
import dal.repositories.interfaces.IUserDAO;

import java.io.File;
import java.util.List;

public class UserService implements IUserService {
    private final IUserDAO userDAO = DAOFactory.createUserDao();

    @Override
    public List<User> getUsers() throws Exception {
        return userDAO.getUsers();
    }

    @Override
    public User createUser(String email, String password, String userName, boolean isActive,List<Role> roles) throws Exception {
        return userDAO.createUser(email, password, userName, isActive,roles);
    }

    @Override
    public void updateUser(User user, String email, String password, String userName, boolean isActive) throws Exception {
        userDAO.updateUser(user, email, password, userName, isActive);
    }

    @Override
    public void deleteUser(User currentUser) throws Exception {
        userDAO.deleteUser(currentUser);
    }
}
