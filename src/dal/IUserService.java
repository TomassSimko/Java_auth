package dal;

import be.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();
    User createUser(String email, String password, String firstName, String lastName);
    User updateUser(User user,String email, String password,String firstName,String lastName);
    void deleteUser(User currentUser);

    }
