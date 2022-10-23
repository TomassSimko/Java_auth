package bll;

import be.User;

import java.io.File;
import java.util.List;

public interface IUserManager {
    List<User> getUsers();
    User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL);
    User updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL);
    void deleteUser(User currentUser);
}
