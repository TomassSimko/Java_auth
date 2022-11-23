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
    }

    public ObservableList<User> getUserList() throws UserDAOException {
        List<User> fetchedUsers = userManager.getUsers();
        return userList = FXCollections.observableArrayList(fetchedUsers);
    }

    public void createUser(String email, String password, String userName, boolean isActive) throws UserDAOException {
        User fetchedUser = userManager.createUser(email,password,userName,isActive);
        userList.add(fetchedUser);
    }

    public void updateUser(User user, String email, String password, String userName, boolean isActive) throws UserDAOException {
        userManager.updateUser(user,email,password,userName,isActive);
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
