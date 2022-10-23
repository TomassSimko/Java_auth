package bll;

import be.User;
import dal.IUserService;
import dal.UserService;

import java.io.File;
import java.util.List;

// Connects GUI with Dal out the db service
public class UserManager implements IUserManager {

    private final IUserService userService;

    public UserManager(){
       this.userService = new UserService();
    }
    @Override
    public List<User> getUsers() {
        return userService.getUsers();
    }
    @Override
    public User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL){
        return userService.createUser(email,password,firstName,lastName,isActive,pictureURL);
    }

    @Override
    public User updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL){
        return userService.updateUser(user,email,password,firstName,lastName,isActive,pictureURL);
    }

    @Override
    public void deleteUser(User currentUser) {
        userService.deleteUser(currentUser);
    }
}
