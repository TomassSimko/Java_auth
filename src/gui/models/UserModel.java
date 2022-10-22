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
        getUserList();
    }

    public ObservableList<User> getUserList() {
        List<User> fetchedUsers = userManager.getUsers();
        userList = FXCollections.observableArrayList(fetchedUsers);
        return userList;
    }

    public void createUser(String email, String password, String firstName, String lastName){
        User fetchedUser = userManager.createUser(email,password,firstName,lastName);
        if(fetchedUser != null){
            System.out.println("Result set success");
        }
        userList.add(fetchedUser);
    }

    public void updateUser(User user,String email,String password,String firstName,String lastName){
        User updateUser = userManager.updateUser(user,email,password,firstName,lastName);
        if(updateUser != null){
            System.out.println("Result set updated");
        }
    }

    public void deleteUser(User currentUser) {
        userManager.deleteUser(currentUser);
        userList.remove(currentUser);
    }
}
