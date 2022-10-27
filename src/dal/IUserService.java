package dal;

import be.User;
import bll.exceptions.UserDAOException;

import java.io.File;
import java.util.List;

public interface IUserService {
    List<User> getUsers() throws UserDAOException;
    User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException;
    User updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException;
    void deleteUser(User currentUser) throws UserDAOException;

    }
