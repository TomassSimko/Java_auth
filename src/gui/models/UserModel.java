package gui.models;

import be.User;
import bll.IUserManager;
import bll.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

// model used for front end
public class UserModel {

    private final IUserManager userManager;
    private ObservableList<User> userList;

    public UserModel(){
        userManager = new UserManager();
    }

    public ObservableList<User> getUserList() {
        List<User> fetchedUsers = userManager.getUsers();
        userList = FXCollections.observableArrayList(fetchedUsers);
        return userList;
    }
}
