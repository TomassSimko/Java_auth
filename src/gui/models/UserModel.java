package gui.models;

import be.User;
import bll.IUserManager;
import bll.UserManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.List;

// model used for front end
public class UserModel {

    private final IUserManager userManager;
    private ObservableList<User> userList;

  //  private User sessionUser;
    // Idea is when logged in setting session user maybe should be in different class idk yet

    public UserModel(){
        userManager = new UserManager();
        getUserList();
    }

    public ObservableList<User> getUserList() {
        List<User> fetchedUsers = userManager.getUsers();
        return userList = FXCollections.observableArrayList(fetchedUsers);
    }

    public void createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL){
        User fetchedUser = userManager.createUser(email,password,firstName,lastName,isActive,pictureURL);
        if(fetchedUser != null){
            System.out.println("Result set success");
        }
        userList.add(fetchedUser);
    }

    public void updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL){
        User updateUser = userManager.updateUser(user,email,password,firstName,lastName,isActive,pictureURL);
        if(updateUser != null){
            System.out.println("Result set updated");
        }
    }

    public void deleteUser(User currentUser) {
        userManager.deleteUser(currentUser);
        userList.remove(currentUser);
    }

    public void filteredTableOfUsers(String query) {
        List<User> filteredResult = userManager.search(userManager.getUsers(), query);
        userList.clear();
        userList.addAll(filteredResult);
    }

}
