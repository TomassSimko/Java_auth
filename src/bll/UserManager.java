package bll;

import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import bll.utitls.cryptography.CryptoEngine;
import bll.utitls.cryptography.ICryptoEngine;
import bll.utitls.validations.SearchHelper;
import dal.IUserService;
import dal.UserService;

import java.io.File;
import java.util.List;

public class UserManager implements IUserManager {

    private final IUserService userService;
    private final SearchHelper searchHelper;

    private final ICryptoEngine cryptoEngine;

    public UserManager() throws UserManagerException {
        try {
            this.userService = new UserService();
        } catch (Exception e) {
            throw new UserManagerException("Could not retrieve services",e);
        }
        this.searchHelper = new SearchHelper();
        this.cryptoEngine = new CryptoEngine();
    }
    @Override
    public List<User> getUsers() throws UserDAOException {
        try {
            return userService.getUsers();
        }catch(Exception ex){
            throw new UserDAOException("Could not retrieve users",ex);
        }
    }
    @Override
    public User createUser(String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException {
        String hashedPassword = cryptoEngine.Hash(password);
        try{
            return userService.createUser(email,hashedPassword,firstName,lastName,isActive,pictureURL);
        }catch (Exception ex){
            throw new UserDAOException("Could not create users with email " + email,ex);
        }
    }

    @Override
    public void updateUser(User user, String email, String password, String firstName, String lastName, boolean isActive, File pictureURL) throws UserDAOException {
        try{
            userService.updateUser(user, email, password, firstName, lastName, isActive, pictureURL);
        }catch (Exception ex){
            throw new UserDAOException("Could not update users with id " + user.getId(),ex);
        }
    }

    @Override
    public void deleteUser(User currentUser) throws UserDAOException {
        try{
            userService.deleteUser(currentUser);
        }catch (Exception ex){
            throw new UserDAOException("Could not update users with id " + currentUser.getId(),ex);
        }
    }

    @Override
    public List<User> search(List<User> users, String query) {
        return searchHelper.search(users,query);
    }
}
