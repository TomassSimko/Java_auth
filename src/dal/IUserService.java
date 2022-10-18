package dal;

import be.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    User registerUser(String email, String password);
}
