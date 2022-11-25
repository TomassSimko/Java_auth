package gui.models;

import be.Role;
import be.User;
import bll.IUserManager;
import bll.UserManager;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
public class UserModel implements IUserModel{
    private final IUserManager userManager;
    private ObservableList<User> userList;

    public UserModel() throws UserManagerException, UserDAOException {
        this.userManager = new UserManager();
        getUserList();
    }

    public ObservableList<User> getUserList() throws UserDAOException {
        List<User> fetchedUsers = userManager.getUsers();
        return userList = FXCollections.observableArrayList(fetchedUsers);
    }

    public void createUser(String email, String password, String userName, boolean isActive,List<String> roles) throws Exception {
        User fetchedUser = userManager.createUser(email,password,userName,isActive,roles);
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
