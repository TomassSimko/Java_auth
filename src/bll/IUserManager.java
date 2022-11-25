package bll;

import be.Role;
import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserServiceException;

import java.io.File;
import java.util.List;

public interface IUserManager {
    List<User> getUsers() throws UserDAOException;
    User createUser(String email, String password, String userName, boolean isActive,List<String> roles) throws Exception;
    void updateUser(User user, String email, String password, String userName,boolean isActive) throws UserDAOException;
    void deleteUser(User currentUser) throws UserDAOException;

    List<User> search(List<User> users, String query);
}
