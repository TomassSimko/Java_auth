package bll;

import be.User;

import java.util.List;

public interface IUserManager {
    List<User> getUsers();
    User createUser(String email, String password,String firstName,String lastName);
}
