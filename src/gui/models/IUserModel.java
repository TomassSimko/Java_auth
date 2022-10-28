package gui.models;

import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserServiceException;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public interface IUserModel {
    List<User> getUserList() throws UserDAOException, UserServiceException;
    void createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException,UserServiceException;
    void updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException,UserServiceException;
    void deleteUser(User currentUser) throws UserDAOException,UserServiceException;
    void filteredTableOfUsers(String query) throws UserDAOException,UserServiceException;
}
