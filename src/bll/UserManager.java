package bll;

import be.User;
import dal.IUserService;
import dal.UserService;
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
    public User createUser(String email, String password){
        return userService.registerUser(email,password);
    }
}
