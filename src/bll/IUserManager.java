package bll;

import be.User;

import java.util.List;

public interface IUserManager {
    List<User> getUsers();

    User registerUser(String email, String password);
}
