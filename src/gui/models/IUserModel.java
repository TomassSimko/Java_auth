package gui.models;

import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserServiceException;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

public interface IUserModel {
    List<User> getUserList() throws UserDAOException, UserServiceException;
    void createUser(String email, String password, String userName, boolean isActive) throws UserDAOException;
    void updateUser(User user, String email, String password, String userName, boolean isActive) throws UserDAOException;
    void deleteUser(User currentUser) throws UserDAOException,UserServiceException;
    void filteredTableOfUsers(String query) throws UserDAOException,UserServiceException;
}
