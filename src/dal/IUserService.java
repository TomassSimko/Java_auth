package dal;

import be.User;
import bll.exceptions.UserDAOException;

import java.io.File;
import java.util.List;

public interface IUserService {
    List<User> getUsers() throws Exception;
    User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws Exception;
    void updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws Exception;
    void deleteUser(User currentUser) throws Exception;

    }
