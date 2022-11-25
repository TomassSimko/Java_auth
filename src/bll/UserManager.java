package bll;

import be.Role;
import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import bll.utitls.cryptography.CryptoEngine;
import bll.utitls.cryptography.ICryptoEngine;
import bll.utitls.SearchHelper;
import dal.IRoleService;
import dal.IUserService;
import dal.RoleService;
import dal.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserManager implements IUserManager {

    private final IUserService userService;
    private final IRoleService roleService;
    private final SearchHelper searchHelper;
    private final ICryptoEngine cryptoEngine;

    public UserManager() throws UserManagerException {
        try {
            this.userService = new UserService();
            this.roleService = new RoleService();

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
    public User createUser(String email, String password, String userName, boolean isActive,List<String> roles) throws Exception {
        // fetch all roles if the one exist and then construct new role
      //  List<Role> fetchedRoles = roleService.getRoles(); // change to get get Role by name

        List<Role> createdRoles = new ArrayList<>();
        for (String role : roles
             ) {
            Role fetchedRole = roleService.getRoleByName(role);
            if(fetchedRole != null){
               return null;
            }
            createdRoles.add(fetchedRole);
        }

        String hashedPassword = cryptoEngine.Hash(password);
        try{
            return userService.createUser(email,hashedPassword,userName,isActive,createdRoles);
        }catch (Exception ex){
            throw new UserDAOException("Could not create users with email " + email,ex);
        }
    }

    @Override
    public void updateUser(User user, String email, String password, String userName, boolean isActive) throws UserDAOException {
        try{
            userService.updateUser(user, email, password,userName, isActive);
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
