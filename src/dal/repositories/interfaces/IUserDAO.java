package dal.repositories.interfaces;

import be.User;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    List<User> getUsers() throws Exception;
    User createUser(String email, String passwordHash, String firstName, String lastName, boolean isActive, File pictureURL) throws Exception;
    void updateUser(User user, String email, String passwordHash, String firstName, String lastName, boolean isActive, File pictureURL) throws Exception;
    void deleteUser(User currentUser) throws Exception;
}
