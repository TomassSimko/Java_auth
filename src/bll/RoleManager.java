package bll;

import be.Role;
import be.User;
import bll.exceptions.UserDAOException;
import bll.exceptions.UserManagerException;
import dal.IRoleService;
import dal.IUserService;
import dal.RoleService;
import dal.UserService;

import java.util.List;

public class RoleManager implements IRoleManager{

    private final IRoleService roleService;
    public RoleManager()  throws UserManagerException{
        try {
            this.roleService = new RoleService();
        } catch (Exception e) {
            throw new UserManagerException("Could not retrieve services",e);
        }
    }

    @Override
    public List<Role> getRoles() throws UserDAOException {
        try {
            return roleService.getRoles();
        }catch(Exception ex){
            throw new UserDAOException("Could not retrieve users",ex);
        }
    }
}
