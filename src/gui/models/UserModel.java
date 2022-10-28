package gui.models;

import be.User;
import bll.IUserManager;
import bll.UserManager;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;


public class UserModel {
    private final IUserManager userManager;
    private ObservableList<User> userList;

    public UserModel() throws UserManagerException {
        this.userManager = new UserManager();
       // getUserList();
    }

    public ObservableList<User> getUserList() throws UserDAOException {
        List<User> fetchedUsers = userManager.getUsers();
        return userList = FXCollections.observableArrayList(fetchedUsers);
    }

    public void createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException {
        User fetchedUser = userManager.createUser(email,password,firstName,lastName,isActive,pictureURL);
        userList.add(fetchedUser);
    }

    public void updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException {
        userManager.updateUser(user,email,password,firstName,lastName,isActive,pictureURL);
    }
    public void deleteUser(User currentUser) throws UserDAOException{
        userManager.deleteUser(currentUser);
        userList.remove(currentUser);
    }

    public void filteredTableOfUsers(String query) throws UserDAOException {
        List<User> filteredResult = userManager.search(userManager.getUsers(), query);
        userList.clear();
        userList.addAll(filteredResult);
    }

}
