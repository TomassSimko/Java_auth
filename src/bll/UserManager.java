package bll;

import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import bll.utitls.validations.SearchHelper;
import dal.IUserService;
import dal.UserService;

import java.io.File;
import java.util.List;

public class UserManager implements IUserManager {

    private final IUserService userService;
    private final SearchHelper searchHelper;

    public UserManager(IUserService userService) throws UserManagerException{
        this.userService = userService;
      //  this.userService = new UserService();
       this.searchHelper = new SearchHelper();
    }
    @Override
    public List<User> getUsers() throws UserDAOException {
        return userService.getUsers();
    }
    @Override
    public User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException {
        return userService.createUser(email,password,firstName,lastName,isActive,pictureURL);
    }

    @Override
    public User updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException {
        return userService.updateUser(user,email,password,firstName,lastName,isActive,pictureURL);
    }

    @Override
    public void deleteUser(User currentUser) throws UserDAOException {
        userService.deleteUser(currentUser);
    }

    @Override
    public List<User> search(List<User> users, String query) {
        return searchHelper.search(users,query);
    }
}
