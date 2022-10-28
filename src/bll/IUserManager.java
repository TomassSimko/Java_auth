package bll;

import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserServiceException;

import java.io.File;
import java.util.List;

public interface IUserManager {
    List<User> getUsers() throws UserDAOException;
    User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException;
    void updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException;
    void deleteUser(User currentUser) throws UserDAOException;

    List<User> search(List<User> users, String query);
}
